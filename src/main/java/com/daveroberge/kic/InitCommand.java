package com.daveroberge.kic;

import io.airlift.airline.Arguments;
import io.airlift.airline.Command;

import java.io.File;
import java.io.IOException;

@Command(name = "init", description = "initialize a new keepass database in the current directory")
public class InitCommand implements Runnable {
  PasswordReader passwordReader = new ConsolePasswordReader();
  KeePass keePass = new DefaultKeePass();

  @Arguments(description = "the name of the keepass database", required = true)
  String databaseName;

  @Override public void run() {
    String password = passwordReader.readPassword();

    if (!databaseName.contains(".")) {
      databaseName = databaseName + ".kdbx";
    }

    try {
      keePass.create(databaseName, password);
    } catch (IOException e) {
      e.printStackTrace();
      System.err.println("unable to create database");
      System.exit(1);
    }
    System.out.println("created database " + databaseName);

    KicFile kicFile = KicFile.readKicFile();
    try {
      kicFile.writeDefaultDatabaseFile(new File(databaseName));
    } catch (IOException e) {
      e.printStackTrace();
      System.err.println("unable to set " + databaseName + " as the default database");
      System.exit(1);
    }

    System.out.println("set default database to " + databaseName);
  }
}
