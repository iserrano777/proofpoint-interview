package proofpoint.entities;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * Abstract base class for entities that can contain other entities.
 *
 * This includes:
 * - Drives
 * - Folders
 * - Zip files
 *
 * It manages a map of children by name to enforce unique naming under a parent.
 */
public abstract class ContainerEntity extends Entity implements Serializable {
    private static final long serialVersionUID = 1L;

    // Stores child entities keyed by name for fast lookup
    protected Map<String, Entity> children = new HashMap<>();

    /**
     * Constructs a new container entity.
     *
     * @param name   The name of the container.
     * @param parent The parent container entity (null for drives).
     */
    public ContainerEntity(String name, ContainerEntity parent) {
        super(name, parent);
    }

    /**
     * Adds a child entity to this container.
     *
     * @param child The entity to add.
     */
    public void addChild(Entity child) {
        children.put(child.getName(), child);
    }

    /**
     * Removes a child entity from this container by name.
     *
     * @param name The name of the child to remove.
     */
    public void removeChild(String name) {
        children.remove(name);
    }

    /**
     * Retrieves a child entity by name.
     *
     * @param name The name of the child entity.
     * @return The child entity, or null if not found.
     */
    public Entity getChild(String name) {
        return children.get(name);
    }

    /**
     * Checks if this container has a child with the given name.
     *
     * @param name The name to check.
     * @return true if a child with that name exists; false otherwise.
     */
    public boolean hasChild(String name) {
        return children.containsKey(name);
    }

    /**
     * Returns all children of this container.
     *
     * @return A collection of all child entities.
     */
    public Collection<Entity> getChildren() {
        return children.values();
    }
}
