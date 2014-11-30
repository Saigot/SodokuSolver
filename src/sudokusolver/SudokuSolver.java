/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sudokusolver;

import GUI.AppGUI;

/**
 *
 * @author Michael17
 */
public class SudokuSolver {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        if(args.length == 0){
            AppGUI appGUI = new AppGUI();
            appGUI.start();
        }else{
            for(String s : args){
                switch(s){
                    //more app displaytypes here
                }
            }
        }
    }
}
