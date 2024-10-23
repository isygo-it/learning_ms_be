package eu.novobit.com.rest.controller;

import eu.novobit.com.rest.api.IUploadFileApi;
import eu.novobit.com.rest.service.ICrudCodifiableService;
import eu.novobit.com.rest.service.ICrudFileService;
import eu.novobit.dto.IDto;
import eu.novobit.dto.IFileUploadDto;
import eu.novobit.dto.RequestContextDto;
import eu.novobit.model.ICodifiable;
import eu.novobit.model.IFileEntity;
import eu.novobit.model.IIdEntity;
import eu.novobit.model.ISASEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;


/**
 * The type Mapped file controller.
 *
 * @param <T>     the type parameter
 * @param <MinD>  the type parameter
 * @param <FullD> the type parameter
 * @param <S>     the type parameter
 */
@Slf4j
public abstract class MappedFileController<T extends IIdEntity & ICodifiable & ISASEntity & IFileEntity,
        MinD extends IDto & IFileUploadDto,
        FullD extends MinD,
        S extends ICrudCodifiableService<T> & ICrudFileService<T>>
        extends AbstractCrudBasicsController<T, MinD, FullD, S>
        implements IUploadFileApi<FullD> {

    @Override
    public ResponseEntity<FullD> createWithFile(RequestContextDto requestContext,
                                                //String domain,
                                                FullD dto) {
        log.info("Create with file request received");
        try {
            dto = this.beforeCreate(dto);
            FullD savedResume = mapper().entityToDto(this.afterCreate(
                    crudService().createWithFile("novobit.eu", mapper().dtoToEntity(dto), dto.getFile())));
            return ResponseFactory.ResponseOk(savedResume);
        } catch (Exception ex) {
            return getBackExceptionResponse(ex);
        }
    }

    @Override
    public ResponseEntity<FullD> uploadFile(RequestContextDto requestContext,
                                            Long id, MultipartFile file) {
        log.info("Upload file request received");
        try {
            return ResponseFactory.ResponseOk(mapper().entityToDto(crudService().updateWithFile(id, file)));
        } catch (Throwable e) {
            log.error("<Error>: api exception : {} ", e);
            return getBackExceptionResponse(e);
        }
    }

    @Override
    public ResponseEntity<Resource> downloadFile(RequestContextDto requestContext,
                                                 String domain,
                                                 String filename,
                                                 Long version) {
        log.info("Download file request received");
        try {
            Resource resource = crudService().downloadFile(domain, filename, version);
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_TYPE, "multipart/form-data")
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                    .body(resource);
        } catch (Throwable e) {
            log.error("<Error>: api exception : {} ", e);
            return getBackExceptionResponse(e);
        }
    }

    @Override
    public ResponseEntity<FullD> updateWithFile(RequestContextDto requestContext,
                                                String domain,
                                                FullD dto) {
        log.info("Update with file request received");
        try {
            dto = this.beforeUpdate(dto);
            FullD savedResume = mapper().entityToDto(
                    this.afterUpdate(crudService().createWithFile(domain, mapper().dtoToEntity(dto), dto.getFile())));
            return ResponseFactory.ResponseOk(savedResume);
        } catch (Exception ex) {
            return getBackExceptionResponse(ex);
        }
    }

    @Override
    public ResponseEntity<FullD> updateWithFile(RequestContextDto requestContext,
                                                String domain,
                                                Long id,
                                                FullD dto) {
        log.info("Update with file request received");
        try {
            dto = this.beforeUpdate(dto);
            FullD savedResume = mapper().entityToDto(
                    this.afterUpdate(crudService().createWithFile(domain, mapper().dtoToEntity(dto), dto.getFile())));
            return ResponseFactory.ResponseOk(savedResume);
        } catch (Exception ex) {
            return getBackExceptionResponse(ex);
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
