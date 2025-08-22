package proofpoint.entities;

/**
 * Represents a folder in the in-memory file system.
 * A folder can contain other folders or files (text or zip).
 */
public class Folder extends ContainerEntity {

    /**
     * Constructs a new Folder with the given name and parent container.
     *
     * @param name   the name of the folder
     * @param parent the parent container entity
     */
    public Folder(String name, ContainerEntity parent) {
        super(name, parent);
    }

    /**
     * Returns the type of this entity.
     *
     * @return the string "folder"
     */
    @Override
    public String getType() {
        return "folder";
    }
}
