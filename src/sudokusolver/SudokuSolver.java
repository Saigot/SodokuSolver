/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sudokusolver;

import UI.AppGUI;

/**
 *
 * @author Michael17
 */
public class SudokuSolver {
    
    private enum DisplayType{
        GUI, COMMAND_LINE
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        DisplayType displayType = DisplayType.GUI;
        boolean debug = false;
        //we use byte to conserve memory
        byte debugLevel = 0;
        for (String s : args) {
            if(debug){
               switch(s.toLowerCase()){
                   case "none":
                   case "0":
                   case "n":
                       debugLevel = 0;
                       break;
                   case "low":
                   case "1":
                   case "l":
                       debugLevel = 1;
                       break;
                   case "medium":
                   case "med":
                   case "m":
                   case "2":
                       debugLevel = 2;
                       break;
                   case "high":
                   case "hi":
                   case "h":
                   case "3":
                       debugLevel = 3;
                       break;
                   default:
                       printDebugErrMsg();
                       return;
               }
               debug = false;
               continue;
            }
            switch (s.toLowerCase()) {
                case "-g":
                case "gui":
                    displayType = DisplayType.GUI;
                    break;
                case "-h":
                case "help":
                    System.out.println("Help is coming soon, for now try running with no arguements");
                    return;
                case "-d":
                case "debug":
                    debug = true;
                    break;
                    
                    
                //more app displaytypes here
                default:
                    System.out.println("Invalid Command. Run with -h or help for help");
                    return;
            }
        }
        if(debug){
            printDebugErrMsg();
        }
        if(displayType == DisplayType.GUI){
            setDebugLevel(debugLevel);
            AppGUI appGUI = new AppGUI();
            appGUI.start();
        }else if(displayType == DisplayType.COMMAND_LINE){
            //todo: command line support
        }
    }
    
    public static void printDebugErrMsg(){
        System.out.println("Invalid Debug Parameter, valid values are:");
        System.out.println("numbers between 0 and 3");
        System.out.println("none,low,medium,high");
    }
    
    public static void setDebugLevel(byte debugLevel){
        Cell.debugLevel = debugLevel;
        AppGUI.debugLevel = debugLevel;
        Sodoku.debugLevel = debugLevel;
    }
}
