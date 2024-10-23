package eu.novobit.controller;

import eu.novobit.annotation.CtrlDef;
import eu.novobit.com.rest.api.IUploadFileApi;
import eu.novobit.com.rest.controller.MappedCrudController;
import eu.novobit.com.rest.controller.ResponseFactory;
import eu.novobit.constants.JwtContants;
import eu.novobit.constants.RestApiContants;
import eu.novobit.dto.AbstractDto;
import eu.novobit.dto.RequestContextDto;
import eu.novobit.dto.data.TemplateDto;
import eu.novobit.exception.handler.MmsExceptionHandler;
import eu.novobit.mapper.TemplateMapper;
import eu.novobit.model.Template;
import eu.novobit.service.impl.TemplateService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.ws.rs.NotSupportedException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * The type Template controller.
 */
@Slf4j
@Validated
@RestController
@RequestMapping(path = "/api/private/mail/template")
@CtrlDef(handler = MmsExceptionHandler.class, mapper = TemplateMapper.class, service = TemplateService.class)
public class TemplateController extends MappedCrudController<Template, TemplateDto, TemplateDto, TemplateService>
        implements IUploadFileApi<TemplateDto> {

    @Autowired
    private TemplateService templateService;

    @Override
    public ResponseEntity<TemplateDto> createWithFile(RequestContextDto requestContext,
                                                      TemplateDto templateDto) {
        try {
            return ResponseFactory.ResponseOk(mapper().entityToDto(templateService.createTemplate(templateDto.getDomain(), mapper().dtoToEntity(templateDto), templateDto.getFile())));
        } catch (Throwable e) {
            log.error("<Error>: api exception : {} ", e);
            return getBackExceptionResponse(e);
        }
    }

    @Override
    public ResponseEntity<TemplateDto> uploadFile(RequestContextDto requestContext,
                                                  Long id,
                                                  MultipartFile file) {
        throw new NotSupportedException();
    }

    @Override
    public ResponseEntity<Resource> downloadFile(RequestContextDto requestContext,
                                                 String domain,
                                                 String filename,
                                                 Long version) {
        try {
            Resource file = templateService.download(domain, filename);
            Path path = file.getFile().toPath();
            log.info(file.getFilename());
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_TYPE, Files.probeContentType(path))
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"").body(file);
        } catch (Throwable e) {
            log.error("<Error>: api exception : {} ", e);
            return getBackExceptionResponse(e);
        }
    }

    /**
     * Delete template response entity.
     *
     * @param requestContext the request context
     * @param domain         the domain
     * @param id             the id
     * @return the response entity
     * @throws IOException the io exception
     */
    @Operation(summary = "deleteTemplate Api",
            description = "deleteTemplate")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Api executed successfully",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = AbstractDto.class))})
    })
    @DeleteMapping(path = "/delete/{domain}/{id}")
    public ResponseEntity<String> deleteTemplate(@RequestAttribute(value = JwtContants.JWT_USER_CONTEXT, required = false) RequestContextDto requestContext,
                                                 @RequestParam(name = RestApiContants.DOMAIN_NAME) String domain,
                                                 @PathVariable Long id) throws IOException {
        try {
            templateService.deleteTemplate(domain, id);
            return ResponseFactory.ResponseOk("Template deleted");
        } catch (Throwable e) {
            log.error("<Error>: api exception : {} ", e);
            return getBackExceptionResponse(e);
        }
    }

    @Override
    public ResponseEntity<TemplateDto> updateWithFile(@RequestAttribute(value = JwtContants.JWT_USER_CONTEXT, required = false) RequestContextDto requestContext,
                                                      @RequestParam(name = RestApiContants.DOMAIN_NAME) String domain,
                                                      TemplateDto templateDto) {
        try {
            return ResponseFactory.ResponseOk(mapper().entityToDto(templateService.updateTemplate(domain, templateDto.getId(), mapper().dtoToEntity(templateDto), templateDto.getFile())));
        } catch (Throwable e) {
            log.error("<Error>: api exception : {} ", e);
            return getBackExceptionResponse(e);
        }
    }

    @Override
    public ResponseEntity<TemplateDto> updateWithFile(RequestContextDto requestContext,
                                                      String domain,
                                                      Long id,
                                                      TemplateDto templateDto) {
        try {
            return ResponseFactory.ResponseOk(mapper().entityToDto(templateService.updateTemplate(domain, id, mapper().dtoToEntity(templateDto), templateDto.getFile())));
        } catch (Throwable e) {
            log.error("<Error>: api exception : {} ", e);
            return getBackExceptionResponse(e);
        }
    }
}
