package com.daveroberge.kic;

import io.airlift.airline.Arguments;

public abstract class KeePassCommand implements Runnable {
  @Arguments(description = "Name of the keepass database", required = true)
  String databaseName;
}
