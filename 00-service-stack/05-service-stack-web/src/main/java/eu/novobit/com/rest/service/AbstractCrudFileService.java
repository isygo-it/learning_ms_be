package eu.novobit.com.rest.service;

import eu.novobit.annotation.LnkFileService;
import eu.novobit.app.ApplicationContextService;
import eu.novobit.com.rest.api.ILinkedFileApi;
import eu.novobit.dto.LinkedFileDto;
import eu.novobit.dto.LinkedFileResponseDto;
import eu.novobit.dto.RequestContextDto;
import eu.novobit.exception.LinkedFileServiceNotDefinedException;
import eu.novobit.exception.NextCodeServiceNotDefinedException;
import eu.novobit.exception.ObjectNotFoundException;
import eu.novobit.exception.ResourceNotFoundException;
import eu.novobit.model.ICodifiable;
import eu.novobit.model.IFileEntity;
import eu.novobit.model.IIdEntity;
import eu.novobit.model.ISASEntity;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Optional;

/**
 * The type Abstract crud file service.
 *
 * @param <T> the type parameter
 * @param <R> the type parameter
 */
@Slf4j
public abstract class AbstractCrudFileService<T extends IFileEntity & IIdEntity & ISASEntity & ICodifiable, R extends JpaRepository>
        extends AbstractCrudCodifiableService<T, R>
        implements ICrudFileService<T> {

    @Autowired
    private ApplicationContextService applicationContextServie;

    private ILinkedFileApi linkedFileApi;

    private ILinkedFileApi linkedFileService() throws NextCodeServiceNotDefinedException {
        if (this.linkedFileApi == null) {
            LnkFileService annotation = this.getClass().getAnnotation(LnkFileService.class);
            if (annotation != null) {
                this.linkedFileApi = applicationContextServie.getBean(annotation.value());
                if (this.linkedFileApi == null) {
                    log.error("<Error>: bean {} not found", annotation.value().getSimpleName());
                    throw new LinkedFileServiceNotDefinedException("Bean not found " + annotation.value().getSimpleName() + " not found");
                }
            } else {
                log.error("<Error>: Linked file service not defined for {}", this.getClass().getSimpleName());
                throw new LinkedFileServiceNotDefinedException(this.getClass().getSimpleName() + ": Missed annotation @LnkFileService");
            }
        }

        return this.linkedFileApi;
    }

    /**
     * Before upload t.
     *
     * @param domain the domain
     * @param entity the entity
     * @param file   the file
     * @return the t
     * @throws IOException the io exception
     */
    public T beforeUpload(String domain, T entity, MultipartFile file) throws IOException {
        return entity;
    }

    /**
     * After upload t.
     *
     * @param domain the domain
     * @param entity the entity
     * @param file   the file
     * @return the t
     * @throws IOException the io exception
     */
    public T afterUpload(String domain, T entity, MultipartFile file) throws IOException {
        return entity;
    }

    @Override
    @Transactional
    public T createWithFile(String domain, T entity, MultipartFile file) throws IOException {
        entity.setOriginalFileName(file.getOriginalFilename());
        entity.setExtension(FilenameUtils.getExtension(file.getOriginalFilename()));
        entity.setDomain(domain);
        //Creating entity
        entity = this.createAndFlush(entity);
        //Uploading file
        entity = this.beforeUpload(domain, entity, file);
        ResponseEntity<LinkedFileResponseDto> result = linkedFileService().upload(//RequestContextDto.builder().build(),
                LinkedFileDto.builder()
                        .domain(entity.getDomain())
                        .path(File.separator + entity.getClass().getSimpleName().toUpperCase())
                        .tags(entity.getTags())
                        .categoryNames(Arrays.asList(entity.getClass().getSimpleName()))
                        .file(file)
                        .build());
        if (result.getStatusCode().is2xxSuccessful()) {
            log.info("File uploaded successfully {}", file.getOriginalFilename());
        }
        entity = this.afterUpload(domain, entity, file);
        return entity;
    }

    @Override
    @Transactional
    public T updateWithFile(Long id, MultipartFile file) throws IOException {
        T entity = findById(id);
        if (entity != null) {
            entity.setOriginalFileName(file.getOriginalFilename());
            entity.setExtension(FilenameUtils.getExtension(file.getOriginalFilename()));
            //updating entity
            entity = this.update(entity);
            //Uploading file
            entity = this.beforeUpload(entity.getDomain(), entity, file);
            ResponseEntity<LinkedFileResponseDto> result = linkedFileService().upload(//RequestContextDto.builder().build(),
                    LinkedFileDto.builder()
                            .domain(entity.getDomain())
                            .path(File.separator + entity.getClass().getSimpleName().toUpperCase())
                            .tags(entity.getTags())
                            .categoryNames(Arrays.asList(entity.getClass().getSimpleName()))
                            .file(file)
                            .build());
            if (result.getStatusCode().is2xxSuccessful()) {
                log.info("File uploaded successfully {}", file.getOriginalFilename());
            }
            entity = this.afterUpload(entity.getDomain(), entity, file);
            return entity;
        } else {
            throw new ObjectNotFoundException("with id " + id);
        }
    }

    @Override
    public Resource downloadFile(String domain, String filename, Long version) throws IOException {
        ResponseEntity<Resource> result = linkedFileService().download(RequestContextDto.builder().build(),
                domain, filename, version);
        if (result.getStatusCode().is2xxSuccessful() && result.hasBody()) {
            return result.getBody();
        } else {
            throw new ResourceNotFoundException("No resource found for " + domain + '/' + filename + "/" + version);
        }
    }

    @Override
    public Resource downloadFile(Long id) throws IOException {
        Optional<T> optional = repository().findById(id);
        if (optional.isPresent()) {
            T entity = optional.get();
            return this.downloadFile(entity.getDomain(), entity.getOriginalFileName(), null);
        } else {
            throw new ObjectNotFoundException("with id " + id);
        }
    }

    /**
     * Gets upload directory.
     *
     * @return the upload directory
     */
    protected abstract String getUploadDirectory();

    public T beforeUpdate(T object) {
        return object;
    }

    public T afterUpdate(T object) {
        return object;
    }

    public T beforeCreate(T object) {
        return object;
    }

    public T afterCreate(T object) {
        return object;
    }
}
