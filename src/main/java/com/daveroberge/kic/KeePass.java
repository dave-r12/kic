package com.daveroberge.kic;

public interface KeePass {
  void init(String databaseName);
  void open(String databaseName, String password);
  void addRootEntry(String title, String username, String password) throws DuplicateEntryException;
  void save(String databasePassword);
  String getPasswordForEntry(String title) throws NoEntryFoundException;
}
