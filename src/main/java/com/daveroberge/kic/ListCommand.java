package com.daveroberge.kic;

import de.slackspace.openkeepass.KeePassDatabase;
import de.slackspace.openkeepass.domain.Entry;
import de.slackspace.openkeepass.domain.KeePassFile;
import io.airlift.airline.Command;

import java.io.IOException;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Command(name = "ls", description = "list keepass entries")
public class ListCommand implements Runnable {
  PasswordReader passwordReader = new ConsolePasswordReader();

  @Override public void run() {
    String password = passwordReader.readPassword();

    KicFile kicFile = Preconditions.kicFilePresentOrExit();

    KeePassFile keePassFile = null;
    try {
      keePassFile = KeePassDatabase.getInstance(kicFile.getDefaultDatabaseFile())
          .openDatabase(password);
    } catch (IOException e) {
      e.printStackTrace();
      System.err.println("unable to open .kic file");
      System.exit(1);
    }

    List<Entry> entries = keePassFile.getEntries();
    if (entries.isEmpty()) {
      System.out.println("No entries in the database");
      System.exit(0);
    }

    Collections.sort(entries, new Comparator<Entry>() {
      @Override public int compare(Entry o1, Entry o2) {
        return o1.getTitle().compareTo(o2.getTitle());
      }
    });

    for (Entry entry : entries) {
      System.out.println(String.format("title: %-25s, username: %25s", entry.getTitle(), entry
          .getUsername()));
    }
  }
}
