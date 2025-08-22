package proofpoint;

import proofpoint.entities.TextFile;
import proofpoint.entities.Entity;

/**
 * Main class to demonstrate basic operations of the in-memory file system.
 * This acts as a sample client for FileSystemManager.
 */
public class Main {
    public static void main(String[] args) {
        // Create a new instance of the file system manager
        FileSystemManager fsm = new FileSystemManager();

        // Create a drive named "C"
        fsm.create("drive", "C", "");

        // Create a folder "Docs" inside drive C
        fsm.create("folder", "Docs", "C");

        // Create a text file "Hello.txt" inside "C\Docs"
        fsm.create("textfile", "Hello.txt", "C\\Docs");

        // Write content to the newly created text file
        fsm.writeToFile("C\\Docs\\Hello.txt", "Hello from the in-memory file system!");

        // Resolve the file to confirm it exists and print its content
        Entity file = fsm.resolve("C\\Docs\\Hello.txt");

        // Check if the resolved entity is a TextFile and print its content
        if (file instanceof TextFile textFile) {
            System.out.println("✅ File content: " + textFile.getContent());
        }
    }
}

