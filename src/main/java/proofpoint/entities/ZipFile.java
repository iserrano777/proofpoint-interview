package proofpoint.entities;

/**
 * Represents a zip file in the in-memory file system.
 * A zip file is a container that can hold other folders or files,
 * similar to a folder, but is semantically treated as a compressed archive.
 */
public class ZipFile extends ContainerEntity {

    /**
     * Constructs a new ZipFile with the given name and parent container.
     *
     * @param name   the name of the zip file
     * @param parent the parent container entity (folder, zip file, or drive)
     */
    public ZipFile(String name, ContainerEntity parent) {
        super(name, parent);
    }

    /**
     * Returns the type of this entity.
     *
     * @return the string "zipfile"
     */
    @Override
    public String getType() {
        return "zipfile";
    }
}