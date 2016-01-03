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
