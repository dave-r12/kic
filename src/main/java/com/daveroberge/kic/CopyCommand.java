package com.daveroberge.kic;

import io.airlift.airline.Command;

import static com.daveroberge.kic.Clipboards.copyAndNotify;

@Command(name = "cp", description = "copy password to clipboard")
public class CopyCommand extends EntryCommand {
  PasswordReader passwordReader = new ConsolePasswordReader();
  KeePass keePass = new DefaultKeePass();

  @Override public void run() {
    String password = passwordReader.readPassword();
    KicFile kicFile = Preconditions.kicFilePresentOrExit();

    keePass.open(kicFile, password);

    try {
      String entryPassword = keePass.getPasswordForEntry(title);
      copyAndNotify(entryPassword);
    } catch (NoEntryFoundException e) {
      System.err.println("no entry found for title: " + title);
      System.exit(1);
    }
  }
}
