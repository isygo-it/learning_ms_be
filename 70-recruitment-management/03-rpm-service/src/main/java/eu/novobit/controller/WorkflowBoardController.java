package eu.novobit.controller;

import eu.novobit.annotation.CtrlDef;
import eu.novobit.com.rest.controller.MappedCrudController;
import eu.novobit.com.rest.controller.ResponseFactory;
import eu.novobit.constants.JwtContants;
import eu.novobit.constants.RestApiContants;
import eu.novobit.dto.*;
import eu.novobit.dto.data.JobApplicationInterviewEventRequestDto;
import eu.novobit.dto.data.WorkflowBoardDto;
import eu.novobit.dto.data.WorkflowStateDto;
import eu.novobit.exception.handler.RpmExceptionHandler;
import eu.novobit.mapper.WorkflowBoardMapper;
import eu.novobit.mapper.WorkflowStateMapper;
import eu.novobit.model.IBoardItem;
import eu.novobit.model.IStatable;
import eu.novobit.model.WorkflowBoard;
import eu.novobit.service.IWorkflowBoardService;
import eu.novobit.service.impl.WorkflowBoardService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * The type Workflow board controller.
 */
@Slf4j
@Validated
@RestController
@RequestMapping(path = "/api/private/workflow/board")
@CtrlDef(handler = RpmExceptionHandler.class, mapper = WorkflowBoardMapper.class, service = WorkflowBoardService.class)
public class WorkflowBoardController extends MappedCrudController<WorkflowBoard, WorkflowBoardDto, WorkflowBoardDto, WorkflowBoardService> {

    @Autowired
    private IWorkflowBoardService workflowBoardService;

    @Autowired
    private WorkflowStateMapper workflowStateMapper;

    /**
     * Gets states.
     *
     * @param requestContext the request context
     * @param wbCode         the wb code
     * @return the states
     */
    @Operation(summary = "getStates Api",
            description = "getStates")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Api executed successfully",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = AbstractDto.class))})
    })
    @GetMapping(path = "/states")
    ResponseEntity<List<WorkflowStateDto>> getStates(@RequestAttribute(value = JwtContants.JWT_USER_CONTEXT, required = false) RequestContextDto requestContext,
                                                     @RequestParam(name = RestApiContants.wbCode) String wbCode) {
        try {
            List<WorkflowStateDto> list = workflowStateMapper.listEntityToDto(workflowBoardService.getStates(wbCode))
                    .stream()
                    .peek(workflowStateDto -> workflowStateDto.setWbCode(wbCode))
                    .toList();
            if (CollectionUtils.isEmpty(list)) {
                return ResponseFactory.ResponseNoContent();
            }
            return ResponseFactory.ResponseOk(list);
        } catch (Throwable e) {
            log.error("<Error>: api exception : {} ", e);
            return getBackExceptionResponse(e);
        }
    }

    /**
     * Gets items.
     *
     * @param requestContext the request context
     * @param wbCode         the wb code
     * @return the items
     */
    @Operation(summary = "getItems Api",
            description = "getItems")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Api executed successfully",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = AbstractDto.class))})
    })

    @GetMapping(path = "/items")
    ResponseEntity<List<IBoardItem>> getItems(@RequestAttribute(value = JwtContants.JWT_USER_CONTEXT, required = false) RequestContextDto requestContext,
                                              @RequestParam(name = RestApiContants.wbCode) String wbCode) {
        try {
            List<IBoardItem> list = workflowBoardService.getItems(requestContext.getSenderDomain(), wbCode);
            if (CollectionUtils.isEmpty(list)) {
                return ResponseFactory.ResponseNoContent();
            }
            return ResponseFactory.ResponseOk(list);
        } catch (Throwable e) {
            log.error("<Error>: api exception : {} ", e);
            return getBackExceptionResponse(e);
        }
    }

    /**
     * Gets job application candidate.
     *
     * @param requestContext the request context
     * @param code           the code
     * @return the job application candidate
     */
    @Operation(summary = "getJobApplicationCandidate Api",
            description = "getJobApplicationCandidate")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Api executed successfully",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = AbstractDto.class))})
    })
    @GetMapping(path = "/candidate/{code}")
    ResponseEntity<AccountModelDto> getJobApplicationCandidate(@RequestAttribute(value = JwtContants.JWT_USER_CONTEXT, required = false) RequestContextDto requestContext,
                                                               @PathVariable String code) {
        try {
            return ResponseFactory.ResponseOk(workflowBoardService.getCandidateData(code));
        } catch (Throwable e) {
            log.error("<Error>: api exception : {} ", e);
            return getBackExceptionResponse(e);
        }
    }


    /**
     * Gets item types.
     *
     * @param requestContext the request context
     * @return the item types
     */
    @Operation(summary = "getItemTypes Api",
            description = "getItemTypes")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Api executed successfully",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = AbstractDto.class))})
    })
    @GetMapping(path = "/itemTypes")
    ResponseEntity<List<String>> getItemTypes(@RequestAttribute(value = JwtContants.JWT_USER_CONTEXT, required = false) RequestContextDto requestContext) {
        try {
            List<String> list = this.exceptionHandler().getEntityMap().values().stream()
                    .filter(c -> IStatable.class.isAssignableFrom(c))
                    .map(aClass -> aClass.getName()).toList();
            if (CollectionUtils.isEmpty(list)) {
                return ResponseFactory.ResponseNoContent();
            }
            return ResponseFactory.ResponseOk(list);
        } catch (Throwable e) {
            log.error("<Error>: api exception : {} ", e);
            return getBackExceptionResponse(e);
        }
    }

    /**
     * Create event response entity.
     *
     * @param requestContext  the request context
     * @param bpmEventRequest the bpm event request
     * @return the response entity
     */
    @Operation(summary = "createEvent Api",
            description = "createEvent")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Api executed successfully",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = AbstractDto.class))})
    })
    @PostMapping(path = "/event")
    ResponseEntity<BpmEventResponseDto> createEvent(@RequestAttribute(value = JwtContants.JWT_USER_CONTEXT, required = false) RequestContextDto requestContext,
                                                    @Valid @RequestBody BpmEventRequestDto bpmEventRequest) {
        try {
            return ResponseFactory.ResponseOk(workflowBoardService.performEvent(bpmEventRequest));
        } catch (Throwable e) {
            log.error("<Error>: api exception : {} ", e);
            return getBackExceptionResponse(e);
        }
    }

    /**
     * Gets event.
     *
     * @param requestContext the request context
     * @param code           the code
     * @param id             the id
     * @return the event
     */
    @Operation(summary = "getEvent Api",
            description = "getEvent")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Api executed successfully",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = AbstractDto.class))})
    })
    @GetMapping(path = "/board-event/{code}/{id}")
    ResponseEntity<JobApplicationInterviewEventRequestDto> getEvent(@RequestAttribute(value = JwtContants.JWT_USER_CONTEXT, required = false) RequestContextDto requestContext,
                                                                    @PathVariable String code,
                                                                    @PathVariable Long id) {
        try {
            return ResponseFactory.ResponseOk(workflowBoardService.getInterviewEvent(requestContext.getSenderDomain(), code, id));
        } catch (Throwable e) {
            log.error("<Error>: api exception : {} ", e);
            return getBackExceptionResponse(e);
        }
    }

    /**
     * Create event response entity.
     *
     * @param requestContext the request context
     * @param eventType      the event type
     * @param code           the code
     * @param event          the event
     * @return the response entity
     */
    @Operation(summary = "event Api",
            description = "event")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Api executed successfully",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = AbstractDto.class))})
    })
    @PostMapping(path = "/board-event/{event-type}/{code}")
    ResponseEntity<?> createEvent(@RequestAttribute(value = JwtContants.JWT_USER_CONTEXT, required = false) RequestContextDto requestContext,
                                  @PathVariable(name = RestApiContants.EVENT_TYPE) String eventType,
                                  @PathVariable(name = RestApiContants.CODE) String code,
                                  @Valid @RequestBody JobApplicationInterviewEventRequestDto event) {
        try {
            return ResponseFactory.ResponseOk(workflowBoardService.addInterviewEvent(requestContext.getSenderDomain(), code, eventType, event));
        } catch (Throwable e) {
            log.error("<Error>: api exception : {} ", e);
            return getBackExceptionResponse(e);
        }
    }

    /**
     * Update event response entity.
     *
     * @param requestContext the request context
     * @param eventType      the event type
     * @param code           the code
     * @param event          the event
     * @return the response entity
     */
    @Operation(summary = "updateEvent Api",
            description = "updateEvent")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Api executed successfully",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = AbstractDto.class))})
    })
    @PutMapping(path = "/board-event/{event-type}/{code}")
    ResponseEntity<?> updateEvent(@RequestAttribute(value = JwtContants.JWT_USER_CONTEXT, required = false) RequestContextDto requestContext,
                                  @PathVariable(name = RestApiContants.EVENT_TYPE) String eventType,
                                  @PathVariable(name = RestApiContants.CODE) String code,
                                  @Valid @RequestBody JobApplicationInterviewEventRequestDto event) {
        try {
            return ResponseFactory.ResponseOk(workflowBoardService.editInterviewEvent(code, eventType, event));
        } catch (Throwable e) {
            log.error("<Error>: api exception : {} ", e);
            return getBackExceptionResponse(e);
        }
    }


    /**
     * Gets available workflow emails.
     *
     * @param requestContext the request context
     * @param wfCode         the wf code
     * @return the available workflow emails
     */
    @Operation(summary = "getAvailableWorkflowEmails Api",
            description = "getAvailableWorkflowEmails")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Api executed successfully",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = AbstractDto.class))})
    })
    @GetMapping(path = "/watchers")
    ResponseEntity<List<String>> getAvailableWorkflowEmails(@RequestAttribute(value = JwtContants.JWT_USER_CONTEXT, required = false) RequestContextDto requestContext,
                                                            @RequestParam(name = RestApiContants.wfCode) String wfCode) {
        try {
            return ResponseFactory.ResponseOk(crudService().getBoardWatchersByWorkflow(wfCode, requestContext.getSenderDomain()));
        } catch (Throwable e) {
            log.error("<Error>: api exception : {} ", e);
            return getBackExceptionResponse(e);
        }
    }
}
