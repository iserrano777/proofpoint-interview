# Proofpoint File System Challenge

This is a Java-based in-memory file system implementation developed for the Proofpoint interview technical assessment.

## Features

- Supports creation of:
  - Drives
  - Folders
  - Text files
  - Zip files (folders that can contain other entities)
- Basic file system operations:
  - `create`: Add new drives, folders, or files
  - `delete`: Remove any entity from the file system
  - `move`: Move files/folders between directories
  - `writeToFile`: Write content to a text file
  - `resolve`: Navigate the file system using a string path

## Structure

- `FileSystemManager`: Main controller that handles operations
- `Entity` and subclasses (`Folder`, `TextFile`, `ZipFile`, `Drive`): Represent filesystem components
- `ContainerEntity`: Base class for components that can contain children
- `JUnit Test`: Unit tests validating functionality

## Example Usage

```java
fsm.create("drive", "C", "");
fsm.create("folder", "Projects", "C");
fsm.create("textfile", "README.txt", "C\\Projects");
fsm.writeToFile("C\\Projects\\README.txt", "Hello, Proofpoint!");
