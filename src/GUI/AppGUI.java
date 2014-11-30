package GUI;

import exception.OutOfGuessesException;
import exception.UnsolveableException;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import sudokusolver.Sodoku;

/**
 *
 * @author Michael Scovell
 */
public class AppGUI implements ActionListener {

    JFrame form = new JFrame("Sodoku");
    GridLayout gridLayout = new GridLayout(10, 9);
    JPanel mainPanel = new JPanel();
    JButton solve = new JButton("Solve");
    JButton clear = new JButton("Clear");
    Sodoku s = new Sodoku();

    public void start() {
        form.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        form.setSize(new Dimension(600, 600));
        form.setLayout(new BorderLayout());
        mainPanel.setLayout(gridLayout);

        for (int x = 0; x < 9; x++) {
            for (int y = 0; y < 9; y++) {
                mainPanel.add(new JTextField());
            }
        }
        form.add(mainPanel, BorderLayout.CENTER);
        solve.addActionListener(this);
        clear.addActionListener(this);
        JPanel buttons = new JPanel();
        buttons.add(solve);
        buttons.add(clear);
        form.add(buttons, BorderLayout.SOUTH);
        form.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case "Solve":
                int n = 0;
                s.reset();
                for (Component c : mainPanel.getComponents()) {
                    if (c instanceof JTextField) {
                        String text = ((JTextField) c).getText();
                        if (!text.isEmpty()) {
                            int val = Integer.parseInt(text);
                            if (val < 1 || val > 9) {
                                JOptionPane.showMessageDialog(null, "Please select a number between 1 and 9", "Range Error", JOptionPane.ERROR_MESSAGE);
                                s.reset();
                                return;
                            }
                            try {
                                s.set(s.get(n), val, false);
                            } catch (UnsolveableException ex) {
                                JOptionPane.showMessageDialog(null, "Invalid Layout", "Layout Error", JOptionPane.ERROR_MESSAGE);
                                s.reset();
                                return;
                            }
                        }
                        n++;
                    }
                }
                try {
                    s.Solve();
                    for (Component c : mainPanel.getComponents()) {
                        n = 0;
                        if (c instanceof JTextField) {
                            ((JTextField) c).setText(s.get(n) == null ? "" : 
                                    s.get(n).getValue() == null ? "" : s.get(n).getValue().toString());
                            n++;
                        }
                    }
                } catch (OutOfGuessesException ex) {
                    JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                    s.reset();
                }
                System.out.println("DONE");
                break;
            case "Clear":
                s.reset();
                for (Component c : mainPanel.getComponents()) {
                    n = 0;
                    if (c instanceof JTextField) {
                        ((JTextField) c).setText("");
                        n++;
                    }
                }
                break;
        }
        
    }
}
