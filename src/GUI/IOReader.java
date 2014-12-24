
package GUI;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JTextArea;
import javax.swing.JTextField;

/**
 *
 * @author Michael Scovell
 */
public class IOReader {
    public String readValues(File f, JTextField[] inputs, String delimiter) throws IOException{
        ArrayList<Integer> numbers = new ArrayList<>();
        int lineNum = 0;
        
        for (String line : Files.readAllLines(f.toPath(), Charset.defaultCharset())) {

            if (lineNum > 9) {
                break;
            }
            if(line.isEmpty()){
                continue;
            }
            lineNum++;
            int cols = 0;
            String[] split = line.split(delimiter);
            for (String part : split) {
                part = part.trim();
                cols++;
                if(part.equals("\n")){
                    continue;
                }
                try {
                    if(part.isEmpty()){
                        numbers.add(0);
                        continue;
                    }
                    Integer i = Integer.valueOf(part);
                    if(i > 9 || i < 1){
                        throw new NumberFormatException();
                    }
                    numbers.add(i);
                } catch (NumberFormatException e) {
                    return "Line " + lineNum + " column " + cols + ": \""  + part + "\" Is not a valid value";
                }
                
            }
            if(cols > 9){
                return "Line " + lineNum + " has too many columns";
            }
        }
        for(int i = 0; i < numbers.size(); i++){
            inputs[i].setText(numbers.get(i).equals(0) ? "" : numbers.get(i).toString());
        }
        
        return "";
    }
    
    public String writeValues(File f, JTextField[] inputs, String delimiter){
        if(!f.exists()){
            try {
                f.createNewFile();
            } catch (IOException ex) {
                return "Failed to create File";
            }
        }
        if(!f.canWrite()){
            return "No permission to write this file";
        }
        String toWrite = "";
        for(int i = 0; i < inputs.length; i++){
            
            if(i%9 == 0){
                toWrite += "\n" + (inputs[i].getText().isEmpty() ? " " : inputs[i].getText());
            }else{
                toWrite += delimiter + (inputs[i].getText().isEmpty() ? " " : inputs[i].getText());
            }
        }
        
        
        
        try (BufferedWriter writer = Files.newBufferedWriter(f.toPath(), Charset.defaultCharset())) {
            writer.write(toWrite, 0, toWrite.length());
        } catch (IOException x) {
            System.err.format("IOException: %s%n", x);
        }
        return "";
    }
}
