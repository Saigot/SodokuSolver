package UI;

import exception.OutOfGuessesException;
import exception.UnsolveableException;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFileChooser;
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
    JTextField[] inputs = new JTextField[81];
    JPanel mainPanel = new JPanel();
    
    JButton solve = new JButton("Solve");
    JButton clear = new JButton("Clear");
    
    JButton export = new JButton("export");
    JButton importButton = new JButton("import");
    
    IOReader io = new IOReader();
    
    //path is for debugging purposes
    JFileChooser fc = new JFileChooser();
    Sodoku s = new Sodoku();
    
    public static byte debugLevel;
    public static final File debugFile = new File("TestPuzzles");

    public void start() {
        if(debugLevel >= 1 && debugFile.exists()){
            //a quick hack because I hate having to navigate to my source folder, replace debugFile with what you want
            fc.setCurrentDirectory(debugFile);
        }
        form.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        form.setSize(new Dimension(600, 600));
        form.setLayout(new BorderLayout());
        mainPanel.setLayout(gridLayout);

        for (int x = 0; x < 81; x++) {
            inputs[x] = new JTextField();
            int rightEdge = x%3 == 2 ? 4 : 1;
            int leftEdge = x%3 == 0 ? 4 : 1;
            int topEdge = x <= 8 ||(x>=27 && x<=35)||(x>=54 && x<=62) ? 4 : 1;
            int botEdge = (x>=18 && x<=26)||(x>=45 && x<=53)||(x>=72) ? 4 : 1;
            inputs[x].setBorder(BorderFactory.createMatteBorder(topEdge, leftEdge, botEdge, rightEdge, Color.black));
            
            mainPanel.add(inputs[x]);
        }
        form.add(mainPanel, BorderLayout.CENTER);
        solve.addActionListener(this);
        clear.addActionListener(this);
        export.addActionListener(this);
        importButton.addActionListener(this);
        JPanel buttons = new JPanel();
        buttons.add(solve);
        buttons.add(clear);
        buttons.add(export);
        buttons.add(importButton);
        form.add(buttons, BorderLayout.SOUTH);
        form.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand().toLowerCase()) {
            case "solve":
                int n = 0;
                //reset the internal sudoku puzzle
                s.reset();
                for (JTextField c : inputs) {
                    String text = c.getText();
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
                try {
                    s.Solve();
                    n = 0;
                    for (JTextField input : inputs) {
                        ((JTextField) input).setText(s.get(n) == null ? "" : 
                                s.get(n).getValue() == null ? "" : s.get(n).getValue().toString());
                        n++;
                    }
                } catch (OutOfGuessesException ex) {
                    if(s.isDone()){
                       n = 0;
                       for (JTextField input : inputs) {
                            ((JTextField) input).setText(s.get(n) == null ? "" : 
                                    s.get(n).getValue() == null ? "" : s.get(n).getValue().toString());
                            n++;
                       } 
                    }else{
                        JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                        s.reset();
                    }
                }
                break;
            case "clear":
                s.reset();
                for (Component c : mainPanel.getComponents()) {
                    n = 0;
                    if (c instanceof JTextField) {
                        ((JTextField) c).setText("");
                        n++;
                    }
                }
                break;
            case "export":
                int returnValue = fc.showSaveDialog(form);
                if(returnValue == JFileChooser.APPROVE_OPTION){
                    File f = fc.getSelectedFile();
                    String err = io.writeValues(f, inputs, ",");
                    if (!err.isEmpty()) {
                        JOptionPane.showMessageDialog(null, err, "Error", JOptionPane.ERROR_MESSAGE);
                    }

                }
                break;
            case "import":
                returnValue = fc.showOpenDialog(form);
                if(returnValue == JFileChooser.APPROVE_OPTION){
                    try {
                        File f = fc.getSelectedFile();
                        String err = io.readValues(f, inputs, ",");
                        if(!err.isEmpty()){
                            JOptionPane.showMessageDialog(null, err, "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    } catch (IOException ex) {
                        Logger.getLogger(AppGUI.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                break;
                
        }
        
    }
}
