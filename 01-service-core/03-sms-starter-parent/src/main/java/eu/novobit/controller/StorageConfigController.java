package eu.novobit.controller;

import eu.novobit.annotation.CtrlDef;
import eu.novobit.com.rest.controller.MappedCrudController;
import eu.novobit.com.rest.controller.ResponseFactory;
import eu.novobit.constants.JwtContants;
import eu.novobit.constants.RestApiContants;
import eu.novobit.dto.AbstractDto;
import eu.novobit.dto.RequestContextDto;
import eu.novobit.dto.data.StorageConfigDto;
import eu.novobit.exception.handler.SmsExceptionHandler;
import eu.novobit.mapper.StorageConfigMapper;
import eu.novobit.model.StorageConfig;
import eu.novobit.service.impl.StorageConfigService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * The type Storage config controller.
 */
@Slf4j
@Validated
@RestController
@RequestMapping(path = "/api/private/storage/config")
@CtrlDef(handler = SmsExceptionHandler.class, mapper = StorageConfigMapper.class, service = StorageConfigService.class)
public class StorageConfigController extends MappedCrudController<StorageConfig, StorageConfigDto, StorageConfigDto, StorageConfigService> {


    /**
     * Find by domain ignore case response entity.
     *
     * @param requestContext the request context
     * @param domain         the domain
     * @return the response entity
     */
    @Operation(summary = "findByDomainIgnoreCase Api",
            description = "findByDomainIgnoreCase")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Api executed successfully",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = AbstractDto.class))})
    })
    @GetMapping(path = "/domain/{domain}")
    public ResponseEntity<StorageConfigDto> findByDomainIgnoreCase(@RequestAttribute(value = JwtContants.JWT_USER_CONTEXT, required = false) RequestContextDto requestContext,
                                                                   @PathVariable(name = RestApiContants.DOMAIN_NAME) String domain) {
        try {
            return ResponseFactory.ResponseOk(this.mapper().entityToDto(crudService().findByDomainIgnoreCase(domain)));
        } catch (Throwable e) {
            log.error("<Error>: Error calling api getNotificationsByReceiverId : {}", e);
            return getBackExceptionResponse(e);
        }
    }
}
