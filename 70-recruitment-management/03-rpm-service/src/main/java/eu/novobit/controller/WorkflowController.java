package eu.novobit.controller;

import eu.novobit.annotation.CtrlDef;
import eu.novobit.com.rest.controller.MappedCrudController;
import eu.novobit.com.rest.controller.ResponseFactory;
import eu.novobit.constants.JwtContants;
import eu.novobit.dto.AbstractDto;
import eu.novobit.dto.RequestContextDto;
import eu.novobit.dto.data.WorkflowDto;
import eu.novobit.exception.handler.RpmExceptionHandler;
import eu.novobit.mapper.WorkflowMapper;
import eu.novobit.model.Workflow;
import eu.novobit.service.impl.WorkflowService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * The type Workflow controller.
 */
@Slf4j
@Validated
@RestController
@RequestMapping(path = "/api/private/workflow")
@CtrlDef(handler = RpmExceptionHandler.class, mapper = WorkflowMapper.class, service = WorkflowService.class)
public class WorkflowController extends MappedCrudController<Workflow, WorkflowDto, WorkflowDto, WorkflowService> {

    /**
     * Gets unassociated workflows.
     *
     * @param requestContext the request context
     * @return the unassociated workflows
     */
    @Operation(summary = "xxx Api",
            description = "xxx")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Api executed successfully",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = AbstractDto.class))})
    })
    @GetMapping(path = "/unassociated")
    ResponseEntity<List<String>> getUnassociatedWorkflows(@RequestAttribute(value = JwtContants.JWT_USER_CONTEXT, required = false) RequestContextDto requestContext) {
        try {
            return ResponseFactory.ResponseOk(crudService().getWorkflowNotAssociated());
        } catch (Throwable e) {
            log.error("<Error>: api exception : {} ", e);
            return getBackExceptionResponse(e);
        }
    }
}
