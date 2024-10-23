package eu.novobit.controller;

import eu.novobit.annotation.CtrlHandler;
import eu.novobit.com.rest.controller.AbstractController;
import eu.novobit.com.rest.controller.ResponseFactory;
import eu.novobit.constants.JwtContants;
import eu.novobit.constants.RestApiContants;
import eu.novobit.dto.AbstractDto;
import eu.novobit.dto.RequestContextDto;
import eu.novobit.dto.data.EmployeeGlobalStatDto;
import eu.novobit.dto.data.EmployeeStatDto;
import eu.novobit.enumerations.IEnumSharedStatType;
import eu.novobit.exception.handler.HrmExceptionHandler;
import eu.novobit.service.IEmployeeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


/**
 * The type Resume statistics controller.
 */
@Slf4j
@Validated
@RestController
@CtrlHandler(HrmExceptionHandler.class)
@RequestMapping(path = "/api/private/employee/stat")
public class EmployeeStatisticsController extends AbstractController {

    @Autowired
    private IEmployeeService employeeService;

    /**
     * Gets global statistics.
     *
     * @param requestContext the request context
     * @param statType       the stat type
     * @return the global statistics
     */
    @Operation(summary = "Get Global Statistics Api",
            description = "Get Global Statistics")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Api executed successfully",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = AbstractDto.class))})
    })
    @GetMapping(path = "/global")
    ResponseEntity<EmployeeGlobalStatDto> getGlobalStatistics(@RequestAttribute(value = JwtContants.JWT_USER_CONTEXT, required = false) RequestContextDto requestContext
            , @RequestParam(name = RestApiContants.STAT_TYPE) IEnumSharedStatType.Types statType) {
        log.info("Get global statistics");
        try {
            return ResponseFactory.ResponseOk(employeeService.getGlobalStatistics(statType, requestContext));
        } catch (Throwable e) {
            log.error("<Error>: api exception : {} ", e);
            return getBackExceptionResponse(e);
        }
    }

    /**
     * Gets object statistics.
     *
     * @param requestContext the request context
     * @param code           the code
     * @return the object statistics
     */
    @Operation(summary = "Get Object Statistics Api",
            description = "Get Object Statistics")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Api executed successfully",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = AbstractDto.class))})
    })
    @GetMapping(path = "/object")
    ResponseEntity<EmployeeStatDto> getObjectStatistics(@RequestAttribute(value = JwtContants.JWT_USER_CONTEXT, required = false) RequestContextDto requestContext,
                                                        @RequestParam(name = RestApiContants.code) String code) {
        log.info("Get object statistics with code: ", code);
        try {
            return ResponseFactory.ResponseOk(employeeService.getObjectStatistics(code, requestContext));
        } catch (Throwable e) {
            log.error("<Error>: api exception : {} ", e);
            return getBackExceptionResponse(e);
        }
    }
}
