package eu.novobit.service;

import eu.novobit.com.rest.service.ICrudServiceMethods;
import eu.novobit.dto.LinkedFileDto;
import eu.novobit.model.LinkedFile;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

/**
 * The interface Linked file service.
 */
public interface ILinkedFileService extends ICrudServiceMethods<LinkedFile> {
    /**
     * Upload string.
     *
     * @param linkedFile the linked file
     * @param file       the file
     * @return the string
     * @throws IOException the io exception
     */
    String upload(LinkedFileDto linkedFile, MultipartFile file) throws IOException;

    /**
     * Search by tags list.
     *
     * @param domain the domain
     * @param tags   the tags
     * @return the list
     * @throws IOException the io exception
     */
    List<LinkedFile> searchByTags(String domain, String tags) throws IOException;

    /**
     * Delete file.
     *
     * @param domain the domain
     * @param code   the code
     * @throws IOException the io exception
     */
    void deleteFile(String domain, String code) throws IOException;

    /**
     * Search by original name linked file.
     *
     * @param domain       the domain
     * @param originalName the original name
     * @return the linked file
     * @throws IOException the io exception
     */
    LinkedFile searchByOriginalName(String domain, String originalName) throws IOException;

    /**
     * Rename file linked file.
     *
     * @param domain   the domain
     * @param old_name the old name
     * @param new_name the new name
     * @return the linked file
     * @throws IOException the io exception
     */
    LinkedFile renameFile(String domain, String old_name, String new_name) throws IOException;


    /**
     * Search by categories list.
     *
     * @param domain     the domain
     * @param categories the categories
     * @return the list
     * @throws IOException the io exception
     */
    List<LinkedFile> searchByCategories(String domain, List<String> categories) throws IOException;

    /**
     * Download resource.
     *
     * @param originalFileName the original file name
     * @param domain           the domain
     * @param version          the version
     * @return the resource
     * @throws IOException the io exception
     */
    Resource download(String originalFileName, String domain, Long version) throws IOException;


    /**
     * Gets all.
     *
     * @return the all
     */
    List<LinkedFile> getAll();


    /**
     * Directory tree string.
     *
     * @return the string
     */
    String directoryTree();

    /**
     * Gets linked file details.
     *
     * @param path   the path
     * @param domain the domain
     * @return the linked file details
     */
    LinkedFile getLinkedFileDetails(String path, String domain);
}
