package com.daveroberge.kic;

import de.slackspace.openkeepass.KeePassDatabase;
import de.slackspace.openkeepass.domain.Entry;
import de.slackspace.openkeepass.domain.EntryBuilder;
import de.slackspace.openkeepass.domain.Group;
import de.slackspace.openkeepass.domain.GroupBuilder;
import de.slackspace.openkeepass.domain.KeePassFile;
import de.slackspace.openkeepass.domain.KeePassFileBuilder;

import java.io.FileOutputStream;
import java.io.IOException;

public class DefaultKeePass implements KeePass {
  private KeePassFile keePassFile;
  private KicFile kicFile;

  @Override public void open(KicFile kicFile, String password) {
    try {
      keePassFile = KeePassDatabase.getInstance(kicFile.getDefaultDatabaseFile()).openDatabase(password);
    } catch (Exception e) {
      e.printStackTrace();
      System.err.println("unable to open database");
      System.exit(1);
    }

    this.kicFile = kicFile;
  }

  @Override public void addRootEntry(String title, String username, String password) throws DuplicateEntryException {
    Entry existing = keePassFile.getRoot().getEntryByTitle(title);
    if (existing != null) {
      throw new DuplicateEntryException();
    }

    GroupBuilder groupBuilder = new GroupBuilder(keePassFile.getGroupByName("root"));
    Entry entry = new EntryBuilder(title).password(password).username(username).build();
    groupBuilder.addEntry(entry);
    keePassFile = new KeePassFileBuilder(keePassFile.getMeta().getDatabaseName()).addTopGroups(groupBuilder.build())
        .build();
  }

  @Override public void save(String databasePassword) {
    try {
      KeePassDatabase.write(keePassFile, databasePassword, new FileOutputStream(kicFile.getDefaultDatabaseFile()));
    } catch (IOException e) {
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

  @Override public void create(String databaseName, String password) throws IOException {
    Group root = new GroupBuilder("root").build();
    KeePassFileBuilder keePassFileBuilder = new KeePassFileBuilder(databaseName).addTopGroups(root);
    KeePassDatabase.write(keePassFileBuilder.build(), password, new FileOutputStream(databaseName));
  }
}
