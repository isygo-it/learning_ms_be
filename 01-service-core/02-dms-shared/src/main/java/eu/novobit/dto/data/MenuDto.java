package eu.novobit.dto.data;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * The type Menu dto.
 */
@Data
public class MenuDto {
    private String path;
    private List<MenuDto> children;

    /**
     * Instantiates a new Menu dto.
     *
     * @param path the path
     */
    public MenuDto(String path) {
        this.path = path;
        children = new ArrayList<>();
    }

    /**
     * Add child.
     *
     * @param child the child
     */
    public void addChild(MenuDto child) {
        children.add(child);
    }

    /**
     * Gets children.
     *
     * @return the children
     */
    public List<MenuDto> getChildren() {
        return children;
    }

    /**
     * Gets path.
     *
     * @return the path
     */
    public String getPath() {
        return path;
    }

    /**
     * Sets path.
     *
     * @param path the path
     */
    public void setPath(String path) {
        this.path = path;
    }

    /**
     * Gets child.
     *
     * @param data the data
     * @return the child
     */
    public MenuDto getChild(String data) {
        for (MenuDto n : children)
            if (n.path.equals(data)) {
                return n;
            }

        return null;
    }
}
