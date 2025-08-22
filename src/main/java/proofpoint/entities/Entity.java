package proofpoint.entities;

/**
 * Abstract base class representing a file system entity.
 *
 * All file system elements (Drive, Folder, TextFile, ZipFile) extend this class.
 * It defines common properties like name, parent, and path.
 */
public abstract class Entity {
    protected String name;
    protected ContainerEntity parent;

    /**
     * Constructs a new Entity with the given name and parent.
     *
     * @param name   the name of the entity (must be unique within the parent)
     * @param parent the parent container; null only for Drives
     */
    public Entity(String name, ContainerEntity parent) {
        this.name = name;
        this.parent = parent;
    }

    /**
     * Returns the name of this entity.
     *
     * @return the entity name
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the parent of this entity.
     *
     * @return the parent container (null only for Drives)
     */
    public ContainerEntity getParent() {
        return parent;
    }

    /**
     * Updates the parent of this entity.
     *
     * @param newParent the new parent container
     */
    public void setParent(ContainerEntity newParent) {
        this.parent = newParent;
    }

    /**
     * Computes the full path of this entity by traversing up to the drive.
     *
     * @return the full path, e.g., Drive\Folder\SubFolder\File.txt
     */
    public String getPath() {
        if (parent == null) return name;
        return parent.getPath() + "\\" + name;
    }

    /**
     * Returns the type of the entity: drive, folder, textfile, or zipfile.
     *
     * @return the type as a lowercase string
     */
    public abstract String getType();
}