package com.daveroberge.kic;

import io.airlift.airline.Arguments;
import io.airlift.airline.Command;

import java.io.File;
import java.io.IOException;

@Command(name = "db", description = "retrieve or set the default database")
public class DatabaseCommand implements Runnable {

  @Arguments(title = "database file", description = "the database file to set as the default " +
      "database")
  String databaseFile;

  @Override public void run() {
    if (databaseFile == null) {
      readKicFile();
    } else {
      writeKicFile();
    }
  }

  private void writeKicFile() {
    File defaultDatabaseFile = new File(databaseFile);
    if (!defaultDatabaseFile.exists()) {
      System.err.println("cannot set default database to " + databaseFile + ", it doesn't exists");
      System.exit(1);
    }

    KicFile kicFile = KicFile.readKicFile();
    try {
      kicFile.writeDefaultDatabaseFile(defaultDatabaseFile);
      System.out.println("set default database to " + defaultDatabaseFile.getAbsolutePath());
    } catch (IOException e) {
      e.printStackTrace();
      System.err.println("unable to write .kic file");
      System.exit(1);
    }
  }

  private void readKicFile() {
    if (!KicFile.hasKicFile()) {
      System.out.println("no default database is set");
      System.exit(0);
    }

    KicFile kicFile = KicFile.readKicFile();
    try {
      System.out.println("default database file is " + kicFile.getDefaultDatabaseFile().getAbsolutePath());
    } catch (IOException e1) {
      e1.printStackTrace();
      System.err.println("problem reading .kic file");
      System.exit(1);
    }
  }
}
