package com.daveroberge.kic;

import java.io.IOException;

public interface KeePass {
  void create(String databaseName, String password) throws IOException;
  void open(KicFile kicFile, String password);
  void addRootEntry(String title, String username, String password) throws DuplicateEntryException;
  void save(String databasePassword);
  String getPasswordForEntry(String title) throws NoEntryFoundException;
}
