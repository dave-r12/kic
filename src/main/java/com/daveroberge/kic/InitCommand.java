package com.daveroberge.kic;

import io.airlift.airline.Command;

@Command(name = "init", description = "initialize a new keepass database")
public class InitCommand extends KeePassCommand {
  PasswordReader passwordReader = new ConsolePasswordReader();
  KeePass keePass = new DefaultKeePass();

  @Override public void run() {
    String password = passwordReader.readPassword();

    keePass.init(databaseName);
    keePass.save(password);

    System.out.println("created database " + databaseName);
  }
}
