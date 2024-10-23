package eu.novobit.com.rest.service;

import eu.novobit.model.ICodifiable;
import eu.novobit.model.IFileEntity;
import eu.novobit.model.IIdEntity;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * The interface Crud file service.
 *
 * @param <T> the type parameter
 */
public interface ICrudFileService<T extends IIdEntity & IFileEntity & ICodifiable> extends ICrudCodifiableService<T> {

    /**
     * Create with file t.
     *
     * @param domain the domain
     * @param entity the entity
     * @param file   the file
     * @return the t
     * @throws IOException the io exception
     */
    T createWithFile(String domain, T entity, MultipartFile file) throws IOException;

    /**
     * Update with file t.
     *
     * @param id   the id
     * @param file the file
     * @return the t
     * @throws IOException the io exception
     */
    T updateWithFile(Long id, MultipartFile file) throws IOException;

    /**
     * Download file resource.
     *
     * @param domain   the domain
     * @param filename the filename
     * @param version  the version
     * @return the resource
     * @throws IOException the io exception
     */
    Resource downloadFile(String domain, String filename, Long version) throws IOException;

    /**
     * Download file resource.
     *
     * @param id the id
     * @return the resource
     * @throws IOException the io exception
     */
    Resource downloadFile(Long id) throws IOException;
}
