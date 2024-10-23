package eu.novobit.com.rest.service;

import eu.novobit.exception.EmptyPathException;
import eu.novobit.exception.ObjectNotFoundException;
import eu.novobit.exception.ResourceNotFoundException;
import eu.novobit.helper.FileHelper;
import eu.novobit.model.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;

/**
 * The type Abstract crud file image service.
 *
 * @param <T> the type parameter
 * @param <R> the type parameter
 */
@Slf4j
public abstract class AbstractCrudFileImageService<T extends IImageEntity & IFileEntity & IIdEntity & ISASEntity & ICodifiable, R extends JpaRepository>
        extends AbstractCrudFileService<T, R>
        implements ICrudFileService<T>, ICrudImageService<T> {

    @Override
    @Transactional
    public T uploadImage(Long id, MultipartFile file) throws IOException {
        T entity = this.findById(id);
        if (entity != null) {
            if (file != null) {
                entity.setImagePath(FileHelper.storeMultipartFile(this.getUploadDirectory() + File.separator + entity.getDomain() + File.separator + entity.getClass().getSimpleName().toLowerCase() + File.separator + "image",
                        file.getOriginalFilename() + "_" + entity.getCode(), file, "png").toString());
            }
            return this.saveOrUpdate(entity);
        } else {
            throw new ObjectNotFoundException("with id " + id);
        }
    }

    @Override
    public Resource downloadImage(Long id) throws IOException {
        T entity = this.findById(id);
        if (entity != null) {
            if (StringUtils.hasText(entity.getImagePath())) {
                Resource resource = new UrlResource(Path.of(entity.getImagePath()).toUri());
                if (!resource.exists()) {
                    throw new ResourceNotFoundException("for path " + entity.getImagePath());
                }
                return resource;
            } else {
                throw new EmptyPathException("for id " + id);
            }
        } else {
            throw new ResourceNotFoundException("with id " + id);
        }
    }

    @Override
    @Transactional
    public T createWithImage(MultipartFile file, T entity) throws IOException {
        T saved = this.create(entity);
        if (file != null) {
            saved.setImagePath(FileHelper.storeMultipartFile(this.getUploadDirectory() + File.separator + saved.getDomain() + File.separator + entity.getClass().getSimpleName().toLowerCase() + File.separator + "image",
                    file.getOriginalFilename() + "_" + saved.getCode(), file, "png").toString());
            saved = this.update(saved);
        }
        return saved;
    }

    @Override
    @Transactional
    public T updateWithImage(MultipartFile file, T entity) throws IOException {
        T updated = this.update(entity);
        if (file != null) {
            updated.setImagePath(FileHelper.storeMultipartFile(this.getUploadDirectory() + File.separator + updated.getDomain() + File.separator + entity.getClass().getSimpleName().toLowerCase() + File.separator + "image",
                    file.getOriginalFilename() + "_" + updated.getCode(), file, "png").toString());
            updated = this.update(updated);
        }
        return updated;
    }

    protected abstract String getUploadDirectory();
}
