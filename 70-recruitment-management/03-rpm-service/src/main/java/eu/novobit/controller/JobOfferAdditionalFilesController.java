package eu.novobit.controller;

import eu.novobit.annotation.CtrlDef;
import eu.novobit.com.rest.controller.AbstractCrudBasicsController;
import eu.novobit.com.rest.controller.ResponseFactory;
import eu.novobit.constants.JwtContants;
import eu.novobit.constants.RestApiContants;
import eu.novobit.dto.AbstractDto;
import eu.novobit.dto.RequestContextDto;
import eu.novobit.dto.data.JobLinkedFileDto;
import eu.novobit.dto.data.JobOfferDto;
import eu.novobit.exception.handler.RpmExceptionHandler;
import eu.novobit.mapper.JobLinkedFileMapper;
import eu.novobit.mapper.JobOfferMapper;
import eu.novobit.model.JobOffer;
import eu.novobit.service.impl.JobOfferService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * The type Job controller.
 */
@Slf4j
@Validated
@RestController
@RequestMapping(path = "/api/private/Job")
@CtrlDef(handler = RpmExceptionHandler.class, mapper = JobOfferMapper.class, service = JobOfferService.class)
public class JobOfferAdditionalFilesController extends AbstractCrudBasicsController<JobOffer, JobOfferDto, JobOfferDto, JobOfferService> {

    @Autowired
    private JobLinkedFileMapper jobLinkedFileMapper;

    /**
     * Upload additional file response entity.
     *
     * @param requestContext the request context
     * @param id             the id
     * @param files          the files
     * @return the response entity
     */
    @Operation(summary = "xxx Api",
            description = "xxx")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Api executed successfully",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = AbstractDto.class))})
    })
    @PutMapping(path = "/upload/multi-files/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<List<JobLinkedFileDto>> uploadAdditionalFiles(@RequestAttribute(value = JwtContants.JWT_USER_CONTEXT, required = false) RequestContextDto requestContext,
                                                                        @PathVariable(name = RestApiContants.ID) Long id,
                                                                        @Valid @RequestBody MultipartFile[] files) {
        log.info("update additionl file");
        try {
            return ResponseFactory.ResponseOk(jobLinkedFileMapper.listEntityToDto(crudService().uploadAdditionalFile(id, files)));
        } catch (Throwable e) {
            log.error("<Error>: api exception : {} ", e);
            return getBackExceptionResponse(e);
        }
    }

    /**
     * Delete additional response entity.
     *
     * @param id           the id
     * @param originalFile the original file
     * @return the response entity
     */
    @Operation(summary = "deleteAdditionalFile Api",
            description = "deleteAdditionalFile")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Api executed successfully",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = AbstractDto.class))})
    })
    @DeleteMapping(path = "/multi-files/{id}/{originalFile}")
    public ResponseEntity<Boolean> deleteAdditionalFile(@PathVariable(name = RestApiContants.ID) Long id,
                                                        @PathVariable(name = RestApiContants.originalFile) String originalFile) {
        log.info("delete additional file");
        try {
            return ResponseFactory.ResponseOk(crudService().deleteAdditionalFile(id, originalFile));
        } catch (Throwable e) {
            log.error("<Error>: api exception : {} ", e);
            return getBackExceptionResponse(e);
        }
    }

    /**
     * Download response entity.
     *
     * @param requestContext the request context
     * @param domain         the domain
     * @param filename       the filename
     * @param version        the version
     * @return the response entity
     */
    @Operation(summary = "download Api",
            description = "download")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Api executed successfully",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = AbstractDto.class))})
    })
    @GetMapping(path = "/download")
    public ResponseEntity<Resource> download(@RequestAttribute(value = JwtContants.JWT_USER_CONTEXT, required = false) RequestContextDto requestContext,
                                             @RequestParam(name = RestApiContants.DOMAIN_NAME) String domain,
                                             @RequestParam(name = RestApiContants.filename) String filename,
                                             @RequestParam(name = RestApiContants.version) Long version
    ) {
        try {
            log.info("download test file ");
            ResponseEntity<Resource> result = crudService().download(domain, filename, version);
            if (result.getStatusCode().is2xxSuccessful()) {
                log.info("File downloaded successfully {}", filename);
            }
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_TYPE, result.getHeaders().getContentType().toString())
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + filename + "\"")
                    .body(result.getBody());
        } catch (Throwable e) {
            log.error("<Error>: api exception : {} ", e);
            return getBackExceptionResponse(e);
        }
    }
}
