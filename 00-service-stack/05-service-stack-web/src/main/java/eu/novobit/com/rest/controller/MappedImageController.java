package eu.novobit.com.rest.controller;

import eu.novobit.com.rest.api.IUploadImageApi;
import eu.novobit.com.rest.service.ICrudCodifiableService;
import eu.novobit.com.rest.service.ICrudImageService;
import eu.novobit.dto.IDto;
import eu.novobit.dto.IImageUploadDto;
import eu.novobit.dto.RequestContextDto;
import eu.novobit.model.ICodifiable;
import eu.novobit.model.IIdEntity;
import eu.novobit.model.IImageEntity;
import eu.novobit.model.ISASEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;


/**
 * The type Mapped image controller.
 *
 * @param <T>     the type parameter
 * @param <MinD>  the type parameter
 * @param <FullD> the type parameter
 * @param <S>     the type parameter
 */
@Slf4j
public abstract class MappedImageController<T extends IIdEntity & ICodifiable & ISASEntity & IImageEntity,
        MinD extends IDto & IImageUploadDto,
        FullD extends MinD,
        S extends ICrudCodifiableService<T> & ICrudImageService<T>>
        extends AbstractCrudBasicsController<T, MinD, FullD, S>
        implements IUploadImageApi<FullD> {

    @Override
    public ResponseEntity<FullD> uploadImage(RequestContextDto requestContext,
                                             Long id, MultipartFile file) {
        log.info("Upload image request received");
        try {
            return ResponseFactory.ResponseOk(mapper().entityToDto(crudService().uploadImage(id, file)));
        } catch (Throwable e) {
            log.error("<Error>: api exception : {} ", e);
            return getBackExceptionResponse(e);
        }
    }

    @Override
    public ResponseEntity<Resource> downloadImage(RequestContextDto requestContext,
                                                  Long id) throws IOException {
        log.info("Download image request received");
        try {
            Resource imageResource = crudService().downloadImage(id);
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_TYPE, Files.probeContentType(imageResource.getFile().toPath()))
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + imageResource.getFilename() + "\"")
                    .body(imageResource);
        } catch (Throwable e) {
            log.error("<Error>: api exception : {} ", e);
            return getBackExceptionResponse(e);
        }
    }

    @Override
    public ResponseEntity<FullD> createWithImage(RequestContextDto requestContext,
                                                 MultipartFile file, FullD dto) {
        log.info("Create with image request received");
        try {
            dto = this.beforeCreate(dto);
            return ResponseFactory.ResponseOk(mapper().entityToDto(
                    this.afterCreate(crudService().createWithImage(file, mapper().dtoToEntity(dto)))));
        } catch (Throwable e) {
            log.error("<Error>: create Domain : {} ", e);
            return getBackExceptionResponse(e);
        }
    }


    @Override
    public ResponseEntity<FullD> updateWithImage(RequestContextDto requestContext,
                                                 MultipartFile file, FullD dto) {
        log.info("Update with image request received");
        try {
            dto = this.beforeUpdate(dto);
            return ResponseFactory.ResponseOk(mapper().entityToDto(
                    this.afterUpdate(crudService().updateWithImage(file, mapper().dtoToEntity(dto)))));
        } catch (Throwable e) {
            log.error("<Error>: update Domain : {} ", e);
            return getBackExceptionResponse(e);
        }
    }

    /**
     * Before create full d.
     *
     * @param object the object
     * @return the full d
     * @throws Exception the exception
     */
    public FullD beforeCreate(FullD object) throws Exception {
        return object;
    }

    /**
     * After create t.
     *
     * @param object the object
     * @return the t
     * @throws Exception the exception
     */
    public T afterCreate(T object) throws Exception {
        return object;
    }

    /**
     * Before update full d.
     *
     * @param object the object
     * @return the full d
     * @throws Exception the exception
     */
    public FullD beforeUpdate(FullD object) throws Exception {
        return object;
    }

    /**
     * After update t.
     *
     * @param object the object
     * @return the t
     * @throws Exception the exception
     */
    public T afterUpdate(T object) throws Exception {
        return object;
    }
}
