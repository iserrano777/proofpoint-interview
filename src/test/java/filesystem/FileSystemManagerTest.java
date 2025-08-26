package proofpoint;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import proofpoint.entities.Entity;
import proofpoint.entities.TextFile;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the FileSystemManager class.
 * This suite verifies the correctness of operations like create, resolve,
 * write to file, move, and delete in the in-memory file system.
 */
public class FileSystemManagerTest {
    private FileSystemManager fsm;

    /**
     * Sets up a fresh file system before each test,
     * with a drive C, a folder 'Projects', and a text file 'README.txt'.
     */
    @BeforeEach
    void setUp() {
        fsm = new FileSystemManager();
        fsm.create("drive", "C", "");
        fsm.create("folder", "Projects", "C");
        fsm.create("textfile", "README.txt", "C\\Projects");
    }

    /**
     * Tests that a created text file can be resolved correctly by path.
     */
    @Test
    void testCreateAndResolveTextFile() {
        Entity readme = fsm.resolve("C\\Projects\\README.txt");
        assertNotNull(readme);
        assertEquals("README.txt", readme.getName());
        assertEquals("textfile", readme.getType());
    }

    /**
     * Tests that content can be written to a text file and read back correctly.
     */
    @Test
    void testWriteToFileAndReadBack() {
        fsm.writeToFile("C\\Projects\\README.txt", "Hello world!");
        TextFile file = (TextFile) fsm.resolve("C\\Projects\\README.txt");
        assertEquals("Hello world!", file.getContent());
    }

    /**
     * Tests moving a file from one folder to a nested zip/folder path.
     * Ensures the file is removed from the source and added to the target.
     */
    @Test
    void testMoveFile() {
        fsm.create("zipfile", "Archive.zip", "C\\Projects");
        fsm.create("folder", "Docs", "C\\Projects\\Archive.zip");

        fsm.move("C\\Projects\\README.txt", "C\\Projects\\Archive.zip\\Docs");

        assertNotNull(fsm.resolve("C\\Projects\\Archive.zip\\Docs\\README.txt"));
        assertThrows(IllegalArgumentException.class, () -> fsm.resolve("C\\Projects\\README.txt"));
    }

    /**
     * Tests deleting a file and ensuring it can no longer be resolved.
     */
    @Test
    void testDeleteFile() {
        fsm.delete("C\\Projects\\README.txt");
        assertThrows(IllegalArgumentException.class, () -> fsm.resolve("C\\Projects\\README.txt"));
    }

    /**
     * Tests deleting a folder along with its children (recursive deletion).
     */
    @Test
    void testDeleteFolderWithChildren() {
        fsm.writeToFile("C\\Projects\\README.txt", "Some content");
        fsm.delete("C\\Projects");

        assertThrows(IllegalArgumentException.class, () -> fsm.resolve("C\\Projects"));
        assertThrows(IllegalArgumentException.class, () -> fsm.resolve("C\\Projects\\README.txt"));
    }
}
