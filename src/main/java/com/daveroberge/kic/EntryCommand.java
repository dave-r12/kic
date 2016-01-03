package com.daveroberge.kic;

import io.airlift.airline.Option;

public abstract class EntryCommand extends KeePassCommand {
  @Option(name = "-t", description = "title of entry", required = true)
  String title;
}
