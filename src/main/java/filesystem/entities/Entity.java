package proofpoint.entities;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Abstract base class representing a file system entity.
 *
 * All file system elements (Drive, Folder, TextFile, ZipFile) extend this class.
 * It defines common properties like name, parent, and path.
 */
public abstract class Entity implements Serializable {
    private static final long serialVersionUID = 1L;

    protected String name;
    protected ContainerEntity parent;

    protected long size;
    protected final LocalDateTime createdAt;
    protected LocalDateTime updatedAt;

    /**
     * Constructs a new Entity with the given name and parent.
     *
     * @param name   the name of the entity (must be unique within the parent)
     * @param parent the parent container; null only for Drives
     */
    public Entity(String name, ContainerEntity parent) {
        this.name = name;
        this.parent = parent;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
        this.size = 0;
    }

    public void setName(String name) {
        this.name = name;
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
        this.updatedAt = LocalDateTime.now();
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

    public long getSize() {
        return size;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }


    /**
     * Returns the type of the entity: drive, folder, textfile, or zipfile.
     *
     * @return the type as a lowercase string
     */
    public abstract String getType();
}