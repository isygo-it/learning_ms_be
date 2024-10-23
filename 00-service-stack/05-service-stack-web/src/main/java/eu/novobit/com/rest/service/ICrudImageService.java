package eu.novobit.com.rest.service;

import eu.novobit.model.ICodifiable;
import eu.novobit.model.IIdEntity;
import eu.novobit.model.IImageEntity;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * The interface Crud image service.
 *
 * @param <T> the type parameter
 */
public interface ICrudImageService<T extends IIdEntity & IImageEntity & ICodifiable> extends ICrudCodifiableService<T> {

    /**
     * Upload image t.
     *
     * @param id    the id
     * @param image the image
     * @return the t
     * @throws IOException the io exception
     */
    T uploadImage(Long id, MultipartFile image) throws IOException;

    /**
     * Download image resource.
     *
     * @param id the id
     * @return the resource
     * @throws IOException the io exception
     */
    Resource downloadImage(Long id) throws IOException;

    /**
     * Create with image t.
     *
     * @param file the file
     * @param dto  the dto
     * @return the t
     * @throws IOException the io exception
     */
    T createWithImage(MultipartFile file, T dto) throws IOException;

    /**
     * Update with image t.
     *
     * @param file the file
     * @param dto  the dto
     * @return the t
     * @throws IOException the io exception
     */
    T updateWithImage(MultipartFile file, T dto) throws IOException;
}
