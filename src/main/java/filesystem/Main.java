package proofpoint;

import proofpoint.entities.TextFile;
import proofpoint.entities.Entity;

import java.util.List;

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


        fsm.create("folder", "desk", "C");
        // Create a text file "Hello.txt" inside "C\Docs"
        fsm.create("textfile", "Hello.txt", "C\\Docs");

        /*  this will throw an error as only txt files can be added to zip files
        fsm.create("zipfile", "Archive", "C");
        fsm.create("textfile", "readme.txt", "C\\Archive");  // ✅ works
        fsm.create("folder", "subfolder", "C\\Archive"); */

        // Write content to the newly created text file
        fsm.writeToFile("C\\Docs\\Hello.txt", "Hello from the in-memory file system!");

        fsm.copy("C\\Docs\\Hello.txt","C\\desk");

        fsm.saveToDisk("filesystem.dat");
        fsm.loadFromDisk("filesystem.dat");

        // Resolve the file to confirm it exists and print its content
        Entity file = fsm.resolve("C\\Docs\\Hello.txt");
        List<Entity> items = fsm.list("C\\Docs");
        for (Entity e : items) {
            System.out.println(e.getName() + " (" + e.getType() + ")");
        }
        // Search for a file
        List<String> matches = fsm.search("Hello.txt");
        System.out.println("Found: " + matches);

        // Rename a file
        fsm.rename("C\\Docs\\Hello.txt", "Hi.txt");
        System.out.println("Renamed to: " + fsm.resolve("C\\Docs\\Hi.txt").getPath());

        // Check if the resolved entity is a TextFile and print its content
        if (file instanceof TextFile textFile) {
            System.out.println("✅ File content: " + textFile.getContent());
        }
    }
}

