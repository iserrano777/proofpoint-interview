package proofpoint;

import proofpoint.entities.*;

import java.util.HashMap;
import java.util.Map;

/**
 * Manages an in-memory file system supporting drives, folders, text files, and zip files.
 * Provides operations such as create, delete, move, and write.
 *
 * Entities are structured in a tree-like hierarchy starting from drives,
 * and each entity has a unique path.
 *
 * Supported entity types:
 * - Drive: Top-level container, cannot be nested.
 * - Folder: Can contain other folders or files.
 * - TextFile: Leaf node, supports content writing.
 * - ZipFile: Container similar to folders.
 */

public class FileSystemManager {
    // A map of drive names to Drive objects representing the root of each file system tree.
    private final Map<String, Drive> drives = new HashMap<>();

    /**
     * Creates a new entity in the file system.
     *
     * @param type       The type of entity to create (drive, folder, textfile, zipfile).
     * @param name       The name of the new entity.
     * @param parentPath The path to the parent container (ignored for drives).
     * @throws IllegalArgumentException If the parent path doesn't exist, or the entity already exists,
     *                                  or the type is invalid, or the parent cannot contain children.
     */
    public void create(String type, String name, String parentPath) {
        if (type.equalsIgnoreCase("drive")) {
            if (drives.containsKey(name)) {
                throw new IllegalArgumentException("Drive already exists: " + name);
            }
            drives.put(name, new Drive(name));
        } else {
            Entity parent = resolve(parentPath);

            if (!(parent instanceof ContainerEntity)) {
                throw new IllegalArgumentException("Illegal File System Operation: Parent cannot contain children");
            }

            ContainerEntity container = (ContainerEntity) parent;
            if (container.hasChild(name)) {
                throw new IllegalArgumentException("Path already exists: " + name);
            }

            Entity newEntity;
            switch (type.toLowerCase()) {
                case "folder":
                    newEntity = new Folder(name, container);
                    break;
                case "textfile":
                    newEntity = new TextFile(name, container);
                    break;
                case "zipfile":
                    newEntity = new ZipFile(name, container);
                    break;
                default:
                    throw new IllegalArgumentException("Invalid entity type: " + type);
            }
            container.addChild(newEntity);
        }
    }

    /**
     * Deletes an entity and all its contents recursively.
     *
     * @param path The path to the entity to delete.
     * @throws IllegalArgumentException If the path is invalid or does not exist.
     */
    public void delete(String path) {
        Entity entity = resolve(path);
        if (entity.getParent() == null) {
            // Entity is a Drive
            drives.remove(entity.getName());
        } else {
            ContainerEntity parent = (ContainerEntity) entity.getParent();
            parent.removeChild(entity.getName());
        }
    }

    /**
     * Moves an entity from one location to another in the file system.
     *
     * @param sourcePath      The full path to the entity to move.
     * @param destinationPath The full path to the destination container (must be a folder or zip file).
     * @throws IllegalArgumentException If any of the paths is invalid, the destination is not a container,
     *                                  or an entity with the same name already exists at the destination.
     */
    public void move(String sourcePath, String destinationPath) {
        Entity source = resolve(sourcePath);
        Entity dest = resolve(destinationPath);

        if (!(dest instanceof ContainerEntity)) {
            throw new IllegalArgumentException("Destination is not a folder-like entity");
        }

        ContainerEntity sourceParent = (ContainerEntity) source.getParent();
        ContainerEntity destination = (ContainerEntity) dest;

        if (destination.hasChild(source.getName())) {
            throw new IllegalArgumentException("Path already exists at destination");
        }

        sourceParent.removeChild(source.getName());
        source.setParent(destination);
        destination.addChild(source);
    }

    /**
     * Writes text content to a text file in the file system.
     *
     * @param path    The full path to the text file.
     * @param content The content to write.
     * @throws IllegalArgumentException If the path does not refer to a text file.
     */
    public void writeToFile(String path, String content) {
        Entity entity = resolve(path);
        if (!(entity instanceof TextFile)) {
            throw new IllegalArgumentException("Not a text file");
        }
        ((TextFile) entity).setContent(content);
    }

    /**
     * Resolves a path string to the corresponding entity.
     *
     * @param path A backslash-separated path (e.g., "C\\folder\\file.txt").
     * @return The entity located at the path.
     * @throws IllegalArgumentException If the path is invalid, or any part of the path is not found.
     */
    public Entity resolve(String path) {
        String[] parts = path.split("\\\\");
        if (parts.length == 0 || parts[0].isEmpty()) {
            throw new IllegalArgumentException("Invalid path: " + path);
        }

        Drive drive = drives.get(parts[0]);
        if (drive == null) {
            throw new IllegalArgumentException("Drive not found: " + parts[0]);
        }

        Entity current = drive;
        for (int i = 1; i < parts.length; i++) {
            if (!(current instanceof ContainerEntity)) {
                throw new IllegalArgumentException("Cannot traverse non-container entity: " + current.getPath());
            }
            current = ((ContainerEntity) current).getChild(parts[i]);
            if (current == null) {
                throw new IllegalArgumentException("Path not found: " + parts[i]);
            }
        }
        return current;
    }
}
