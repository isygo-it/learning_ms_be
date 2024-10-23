package eu.novobit.dto;

import org.springframework.web.multipart.MultipartFile;

/**
 * The interface File upload dto.
 */
public interface IFileUploadDto {

    /**
     * Gets file.
     *
     * @return the file
     */
    public MultipartFile getFile();

    /**
     * Sets file.
     *
     * @param file the file
     */
    public void setFile(MultipartFile file);
}
