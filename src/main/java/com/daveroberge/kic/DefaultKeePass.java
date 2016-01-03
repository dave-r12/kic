package com.daveroberge.kic;

import de.slackspace.openkeepass.KeePassDatabase;
import de.slackspace.openkeepass.domain.Entry;
import de.slackspace.openkeepass.domain.EntryBuilder;
import de.slackspace.openkeepass.domain.Group;
import de.slackspace.openkeepass.domain.GroupBuilder;
import de.slackspace.openkeepass.domain.KeePassFile;
import de.slackspace.openkeepass.domain.KeePassFileBuilder;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;

public class DefaultKeePass implements KeePass {
  private KeePassFile keePassFile;
  private String databaseName;

  @Override public void open(String databaseName, String password) {
    try {
      keePassFile = KeePassDatabase.getInstance(databaseName + ".kdbx").openDatabase(password);
    } catch (Exception e) {
      e.printStackTrace();
      System.err.println("unable to open database");
      System.exit(1);
    }

    this.databaseName = databaseName;
  }

  @Override public void addRootEntry(String title, String username, String password) throws DuplicateEntryException {
    Entry existing = keePassFile.getRoot().getEntryByTitle(title);
    if (existing != null) {
      throw new DuplicateEntryException();
    }

    GroupBuilder groupBuilder = new GroupBuilder(keePassFile.getGroupByName("root"));
    Entry entry = new EntryBuilder(title).password(password).username(username).build();
    groupBuilder.addEntry(entry);
    keePassFile = new KeePassFileBuilder(databaseName).addTopGroups(groupBuilder.build()).build();
  }

  @Override public void save(String databasePassword) {
    try {
      KeePassDatabase.write(keePassFile, databasePassword, new FileOutputStream(databaseName +
          ".kdbx"));
    } catch (FileNotFoundException e) {
      e.printStackTrace();
      System.err.println("unable to save database");
      System.exit(1);
    }
  }

  @Override public String getPasswordForEntry(String title) throws NoEntryFoundException {
    Entry entry = keePassFile.getEntryByTitle(title);
    if (entry != null) {
      return entry.getPassword();
    }
    throw new NoEntryFoundException();
  }

  @Override public void init(String databaseName) {
    this.databaseName = databaseName;
    Group root = new GroupBuilder("root").build();
    this.keePassFile = new KeePassFileBuilder(databaseName).addTopGroups(root).build();
  }
}
