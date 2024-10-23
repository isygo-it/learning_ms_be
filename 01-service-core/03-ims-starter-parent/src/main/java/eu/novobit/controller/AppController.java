package eu.novobit.controller;

import eu.novobit.annotation.CtrlDef;
import eu.novobit.com.rest.controller.MappedCrudController;
import eu.novobit.com.rest.controller.ResponseFactory;
import eu.novobit.constants.JwtContants;
import eu.novobit.constants.RestApiContants;
import eu.novobit.dto.AbstractDto;
import eu.novobit.dto.RequestContextDto;
import eu.novobit.dto.data.ApplicationDto;
import eu.novobit.enumerations.IEnumBinaryStatus;
import eu.novobit.exception.handler.ImsExceptionHandler;
import eu.novobit.mapper.ApplicationMapper;
import eu.novobit.model.Application;
import eu.novobit.service.impl.ApplicationService;
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
 * The type App controller.
 */
@Slf4j
@Validated
@RestController
@RequestMapping(path = "/api/private/application")
@CtrlDef(handler = ImsExceptionHandler.class, mapper = ApplicationMapper.class, service = ApplicationService.class)
public class AppController extends MappedCrudController<Application, ApplicationDto, ApplicationDto, ApplicationService> {

    /**
     * Update customer status response entity.
     *
     * @param requestContext the request context
     * @param id             the id
     * @param newStatus      the new status
     * @return the response entity
     */
    @Operation(summary = "Update application status Api",
            description = "Update application status")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Api executed successfully",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = AbstractDto.class))})
    })
    @PutMapping(path = "/update-status")
    public ResponseEntity<ApplicationDto> updateCustomerStatus(@RequestAttribute(value = JwtContants.JWT_USER_CONTEXT, required = false) RequestContextDto requestContext,
                                                               @RequestParam(name = RestApiContants.ID) Long id,
                                                               @RequestParam(name = RestApiContants.NEW_STATUS) IEnumBinaryStatus.Types newStatus) {
        log.info("in update status");
        try {
            return ResponseFactory.ResponseOk(mapper().entityToDto(crudService().updateStatus(id, newStatus)));
        } catch (Throwable e) {
            log.error("<Error>: api exception : {} ", e);
            return getBackExceptionResponse(e);
        }
    }
}
