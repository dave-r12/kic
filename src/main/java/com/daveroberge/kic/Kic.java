package com.daveroberge.kic;

import io.airlift.airline.Cli;
import io.airlift.airline.Help;

public class Kic {

  public static void main(String[] args) {
    Cli.CliBuilder<Runnable> builder = Cli.<Runnable>builder("kic")
        .withDescription("keepass in the cloud")
        .withDefaultCommand(Help.class)
        .withCommands(InitCommand.class, AddCommand.class, ListCommand.class, CopyCommand.class);

    Cli<Runnable> cli = builder.build();
    cli.parse(args).run();
  }

}
