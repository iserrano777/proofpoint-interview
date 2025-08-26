package proofpoint.entities;

import java.io.Serializable;

/**
 * Represents a Drive in the file system.
 *
 * A Drive is the root-level entity and cannot have a parent.
 * It can contain folders, zip files, and text files, just like a folder or zip file.
 */
public class Drive extends ContainerEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * Constructs a Drive with the given name.
     * Since a Drive is a root entity, its parent is always null.
     *
     * @param name the unique name of the drive
     */
    public Drive(String name) {
        super(name, null); // drive has no parent
    }

    /**
     * Returns the type of this entity.
     *
     * @return the string "drive"
     */
    @Override
    public String getType() {
        return "drive";
    }

    /**
     * Returns the path of this drive.
     * For drives, the path is simply their name.
     *
     * @return the drive's name as its path
     */
    @Override
    public String getPath() {
        return name;
    }
}
