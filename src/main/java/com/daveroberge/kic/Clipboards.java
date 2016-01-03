package com.daveroberge.kic;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;

public class Clipboards {
  public static void copyAndNotify(String value) {
    Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
    Transferable transferable = new StringSelection(value);
    clipboard.setContents(transferable, null);

    System.out.println("copied password to clipboard, exiting in 10 seconds");
    try {
      for (int i = 0; i < 10; i++) {
        System.out.print(".");
        Thread.sleep(1000);
      }
    } catch (InterruptedException e) {
    }

    clipboard.setContents(new StringSelection(""), null);
  }
}
