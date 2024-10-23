package eu.novobit.api;

import eu.novobit.com.rest.api.IMappedCrudApi;
import eu.novobit.constants.JwtContants;
import eu.novobit.constants.RestApiContants;
import eu.novobit.dto.AbstractDto;
import eu.novobit.dto.RequestContextDto;
import eu.novobit.dto.data.DomainDto;
import eu.novobit.enumerations.IEnumBinaryStatus;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;


/**
 * The interface Domain controller api.
 */
public interface DomainControllerApi extends IMappedCrudApi<DomainDto, DomainDto> {

    /**
     * Update admin status response entity.
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
                            schema = @Schema(implementation = AbstractDto.class))})
    })
    @PutMapping(path = "/update-status")
    public ResponseEntity<DomainDto> updateAdminStatus(@RequestAttribute(value = JwtContants.JWT_USER_CONTEXT, required = false) RequestContextDto requestContext,
                                                       @RequestParam(name = RestApiContants.ID) Long id,
                                                       @RequestParam(name = RestApiContants.NEW_STATUS) IEnumBinaryStatus.Types newStatus);

    /**
     * Gets all domain names.
     *
     * @param requestContext the request context
     * @return the all domain names
     */
    @Operation(summary = "xxx Api",
            description = "xxx")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Api executed successfully",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = AbstractDto.class))})
    })
    @GetMapping(path = "/names")
    public ResponseEntity<List<String>> getAllDomainNames(@RequestAttribute(value = JwtContants.JWT_USER_CONTEXT, required = false) RequestContextDto requestContext);

    /**
     * Gets by name.
     *
     * @param requestContext the request context
     * @return the by name
     */
    @Operation(summary = "xxx Api",
            description = "xxx")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Api executed successfully",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = AbstractDto.class))})
    })
    @GetMapping(path = "/name")
    public ResponseEntity<DomainDto> getByName(@RequestAttribute(value = JwtContants.JWT_USER_CONTEXT, required = false) RequestContextDto requestContext);
}
