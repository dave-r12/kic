# kic

### Still a work in progress

KeePass in the Cloud

This is a simple command line client, with the added capability of uploading and downloading KeePass databases to Google Drive.

### Example usage

```
$ kic init my-database
enter your keepass database password:
created database my-database

$ kic add -t github my-database
enter your keepass database password:
add new entry github
copied password to clipboard, exiting in 10 seconds
..........

$ kic ls my-database
enter your keepass database password:
title: github                   , username:

$ kic cp -t github my-database
enter your keepass database password:
copied password to clipboard, exiting in 10 seconds
..........
```

## Building and running

Apologies, this is a bit cumbersome currently. This is built on openkeepass https://github.com/cternes/openkeepass. They don't currently publish SNAPSHOT builds (that I can see), so I had to clone that repo and install it locally. They just finished adding the feature to write KeePass files in their latest SNAPSHOT version.

Per their instructions, you'll also need a the JCE found here (Java 8) http://www.oracle.com/technetwork/java/javase/downloads/jce8-download-2133166.html

Apparently Oracle cannot bundle this by default because of US laws?

Once those things are in place, you should be able to build:

```
./gradlew distTar
```

That generates a tar file under build/distributions. Extract the archive and make use of the of 'kic' bash script.
