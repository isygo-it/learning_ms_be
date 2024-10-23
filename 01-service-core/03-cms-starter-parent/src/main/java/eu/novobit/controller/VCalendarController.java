package eu.novobit.controller;

import eu.novobit.annotation.CtrlDef;
import eu.novobit.com.rest.controller.MappedCrudController;
import eu.novobit.constants.JwtContants;
import eu.novobit.constants.RestApiContants;
import eu.novobit.dto.AbstractDto;
import eu.novobit.dto.RequestContextDto;
import eu.novobit.dto.data.VCalendarDto;
import eu.novobit.exception.handler.CmsExceptionHandler;
import eu.novobit.mapper.VCalendarMapper;
import eu.novobit.model.VCalendar;
import eu.novobit.service.impl.VCalendarService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * The type V calendar controller.
 */
@Slf4j
@Validated
@RestController
@RequestMapping(path = "/api/private/calendar")
@CtrlDef(handler = CmsExceptionHandler.class, mapper = VCalendarMapper.class, service = VCalendarService.class)
public class VCalendarController extends MappedCrudController<VCalendar, VCalendarDto, VCalendarDto, VCalendarService> {

    @Autowired
    private VCalendarService vCalendarService;

    /**
     * Download response entity.
     *
     * @param requestContext the request context
     * @param domain         the domain
     * @param name           the name
     * @return the response entity
     * @throws IOException the io exception
     */
    @Operation(summary = "xxx Api",
            description = "xxx")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Api executed successfully",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = AbstractDto.class))})
    })
    @GetMapping(path = "/download", produces = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Resource> download(@RequestAttribute(value = JwtContants.JWT_USER_CONTEXT, required = false) RequestContextDto requestContext,
                                             @RequestParam(name = RestApiContants.DOMAIN_NAME) String domain,
                                             @RequestParam(name = RestApiContants.name) String name) throws IOException {
        try {
            Resource resource = vCalendarService.download(domain, name);
            Path path = resource.getFile()
                    .toPath();

            log.info(resource.getFilename());

            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_TYPE, Files.probeContentType(path))
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                    .body(resource);
        } catch (Throwable e) {
            log.error("<Error>: api exception : {} ", e);
            return getBackExceptionResponse(e);
        }
    }
}
