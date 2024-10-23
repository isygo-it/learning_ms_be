package eu.novobit.api;

import eu.novobit.com.rest.api.IMappedCrudApi;
import eu.novobit.constants.JwtContants;
import eu.novobit.constants.RestApiContants;
import eu.novobit.dto.AbstractDto;
import eu.novobit.dto.RequestContextDto;
import eu.novobit.dto.data.EmployeeDto;
import eu.novobit.dto.data.MinEmployeeDto;
import eu.novobit.enumerations.IEnumBinaryStatus;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * The interface Employee controller api.
 */
public interface EmployeeControllerApi extends IMappedCrudApi<MinEmployeeDto, EmployeeDto> {
    /**
     * Update employee status response entity.
     *
     * @param requestContext the request context
     * @param id             the id
     * @param newStatus      the new status
     * @return the response entity
     */
    @Operation(summary = "xxx Api",
            description = "xxx")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Api executed successfully",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = AbstractDto.class))}),
            @ApiResponse(responseCode = "404", description = "Api not found")
    })
    @PutMapping(path = "/updateStatusEmployee")
    ResponseEntity<EmployeeDto> updateEmployeeStatus(@RequestAttribute(value = JwtContants.JWT_USER_CONTEXT, required = false) RequestContextDto requestContext,
                                                     @RequestParam(name = RestApiContants.ID) Long id,
                                                     @RequestParam(name = RestApiContants.NEW_STATUS) IEnumBinaryStatus.Types newStatus);

    /**
     * Gets employee by code.
     *
     * @param requestContext the request context
     * @param code           the code
     * @return the employee by code
     */
    @Operation(summary = "Get Employee by Code",
            description = "Get an employee by their code")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Employee found successfully"),
            @ApiResponse(responseCode = "404", description = "Employee not found")
    })
    @GetMapping("/code/{code}")
    public ResponseEntity<EmployeeDto> getEmployeeByCode(@RequestAttribute(value = JwtContants.JWT_USER_CONTEXT, required = false) RequestContextDto requestContext,
                                                         @PathVariable(name = RestApiContants.code) String code);

    /**
     * Gets employee by domain.
     *
     * @param requestContext the request context
     * @param domain         the domain
     * @return the employee by domain
     */
    @Operation(summary = "Get Employee by Domain",
            description = "Get an employee by their domain")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Employee found successfully"),
            @ApiResponse(responseCode = "404", description = "Employee not found")
    })
    @GetMapping("/domain/{domain}")
    public ResponseEntity<List<EmployeeDto>> getEmployeeByDomain(@RequestAttribute(value = JwtContants.JWT_USER_CONTEXT, required = false) RequestContextDto requestContext,
                                                                 @PathVariable(name = RestApiContants.DOMAIN_NAME) String domain);
}
