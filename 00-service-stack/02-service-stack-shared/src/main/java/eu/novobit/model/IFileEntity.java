package eu.novobit.model;

import java.util.List;

/**
 * The interface File entity.
 */
public interface IFileEntity {

    /**
     * Gets original file name.
     *
     * @return the original file name
     */
    String getOriginalFileName();

    /**
     * Sets original file name.
     *
     * @param originalFileName the original file name
     */
    void setOriginalFileName(String originalFileName);

    /**
     * Gets extension.
     *
     * @return the extension
     */
    String getExtension();

    /**
     * Sets extension.
     *
     * @param extension the extension
     */
    void setExtension(String extension);

    /**
     * Gets tags.
     *
     * @return the tags
     */
    List<String> getTags();
}
