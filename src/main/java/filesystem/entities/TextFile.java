package proofpoint.entities;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Represents a text file in the in-memory file system.
 * A text file cannot contain other entities and holds string content.
 */
public class TextFile extends Entity implements Serializable {
    private static final long serialVersionUID = 1L;
    private String content = "";

    /**
     * Constructs a new TextFile with the given name and parent container.
     *
     * @param name   the name of the text file
     * @param parent the parent container entity
     */
    public TextFile(String name, ContainerEntity parent) {
        super(name, parent);
    }

    /**
     * Sets the content of the text file.
     *
     * @param content the new content to be written to the file
     */
    public void setContent(String content) {
        this.content = content;
        this.size = content.length(); // update size
        this.updatedAt = LocalDateTime.now(); // update timestamp
    }

    /**
     * Returns the current content of the text file.
     *
     * @return the content of the file
     */
    public String getContent() {
        return content;
    }

    /**
     * Returns the type of this entity.
     *
     * @return the string "textfile"
     */
    @Override
    public String getType() {
        return "textfile";
    }
}
