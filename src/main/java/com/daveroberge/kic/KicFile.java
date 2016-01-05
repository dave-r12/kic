package com.daveroberge.kic;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class KicFile {

  private static final String KIC_FILE_NAME = ".kic";

  private final File kicFile;

  public KicFile(File kicFile) {
    this.kicFile = kicFile;
  }

  public static boolean hasKicFile() {
    File defaultDatabaseFile = getKicFile();
    return defaultDatabaseFile.exists();
  }

  private static File getKicFile() {
    String userHome = System.getProperty("user.home");
    return new File(userHome, KIC_FILE_NAME);
  }

  public static KicFile readKicFile() {
    File kicFile = getKicFile();
    return new KicFile(kicFile);
  }

  public File getDefaultDatabaseFile() throws IOException {
    try (BufferedReader bufferedReader = new BufferedReader(new FileReader(kicFile))) {
      String databaseName = bufferedReader.readLine();
      return new File(databaseName);
    }
  }

  public void writeDefaultDatabaseFile(File defaultDatabase) throws IOException {
    try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(kicFile))) {
      bufferedWriter.write(defaultDatabase.getAbsolutePath());
    }
  }
}
