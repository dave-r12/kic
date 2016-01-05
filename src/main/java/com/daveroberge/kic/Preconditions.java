package com.daveroberge.kic;

public class Preconditions {

  public static KicFile kicFilePresentOrExit() {
    if (!KicFile.hasKicFile()) {
      System.err.println("no .kic file present - use 'kic db' to set a default database file");
      System.exit(0);
    }

    return KicFile.readKicFile();
  }
}
