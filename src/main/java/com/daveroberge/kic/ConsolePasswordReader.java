package com.daveroberge.kic;

import java.io.Console;
import java.util.Scanner;

/**
 * Reads a password from stdin. Apparently the preferred approach of using Sytem.console() does not
 * work in all environments
 */
public class ConsolePasswordReader implements PasswordReader {

  public String readPassword() {
    Console console = System.console();
    if (console != null) {
      char[] password = console.readPassword("enter password for keepass database:");
      return new String(password);
    }

    String password = System.getenv("KEEPASS_PASSWORD");
    if (password != null) {
      return password;
    }

    System.out.println("enter password for keepass database (WARNING - this is CLEARTEXT):");
    Scanner scanner = new Scanner(System.in);
    return scanner.nextLine();
  }
}
