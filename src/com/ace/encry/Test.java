package com.ace.encry;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;

public class Test {
	  /**
     * Places text on the clipboard
     */
    public void placeTextOnClipboard() {

        //Get the toolkit
        Toolkit toolkit = Toolkit.getDefaultToolkit();

        //Get the clipboard
        Clipboard clipboard = toolkit.getSystemClipboard();

        //The setContents method of the Clipboard instance takes a Transferable
        //as first parameter. The StringSelection class implements the Transferable
        //interface.
        StringSelection stringSel = new StringSelection("text to be placed on the clipboard");

        //We specify null as the clipboard owner
        clipboard.setContents(stringSel, null);
        
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        new Test().placeTextOnClipboard();
    }
}
