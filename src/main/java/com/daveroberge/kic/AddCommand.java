package com.daveroberge.kic;

import io.airlift.airline.Command;
import io.airlift.airline.Option;

import static com.daveroberge.kic.Clipboards.copyAndNotify;
import static com.daveroberge.kic.Preconditions.kicFilePresentOrExit;

@Command(name = "add", description = "create new entry in the keepass database")
public class AddCommand extends EntryCommand {

  @Option(name = "-u", description = "username of entry")
  String username;

  @Option(name = "-l", description = "max length of generated password")
  Integer passwordLength;

  KeePass keePass = new DefaultKeePass();
  PasswordReader passwordReader = new ConsolePasswordReader();

  @Override public void run() {
    String password = passwordReader.readPassword();
    KicFile kicFile = Preconditions.kicFilePresentOrExit();

    keePass.open(kicFile, password);

    int length = (passwordLength == null ? PasswordGenerator.DEFAULT_LENGTH : passwordLength);
    PasswordGenerator passwordGenerator = new PasswordGenerator();
    String generatedPassword = passwordGenerator.generate(length);

    try {
      keePass.addRootEntry(title, username, generatedPassword);
    } catch (DuplicateEntryException e) {
      System.err.println("an entry already exists with title: " + title);
      System.exit(1);
    }

    keePass.save(password);

    System.out.println("add new entry " + title);

    copyAndNotify(generatedPassword);
  }
}