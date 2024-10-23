package eu.novobit.api;

import eu.novobit.com.rest.api.IMappedCrudApi;
import eu.novobit.constants.JwtContants;
import eu.novobit.constants.RestApiContants;
import eu.novobit.dto.AbstractDto;
import eu.novobit.dto.RequestContextDto;
import eu.novobit.dto.data.KmsDomainDto;
import eu.novobit.enumerations.IEnumBinaryStatus;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


/**
 * The interface Kms domain controller api.
 */
public interface KmsDomainControllerApi extends IMappedCrudApi<KmsDomainDto, KmsDomainDto> {

    /**
     * Update admin status response entity.
     *
     * @param requestContext the request context
     * @param domain         the domain
     * @param newStatus      the new status
     * @return the response entity
     */
    @Operation(summary = "updateAdminStatus Api",
            description = "updateAdminStatus")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Api executed successfully",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = AbstractDto.class))})
    })
    @PutMapping(path = "/update-status")
    public ResponseEntity<KmsDomainDto> updateAdminStatus(@RequestAttribute(value = JwtContants.JWT_USER_CONTEXT, required = false) RequestContextDto requestContext,
                                                          @RequestParam(name = RestApiContants.DOMAIN_NAME) String domain,
                                                          @RequestParam(name = RestApiContants.NEW_STATUS) IEnumBinaryStatus.Types newStatus);

    /**
     * Update domain response entity.
     *
     * @param domain the domain
     * @return the response entity
     */
    @Operation(summary = "Update domain info Api",
            description = "xxx")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Api executed successfully",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = AbstractDto.class))})
    })
    @PostMapping(path = "/update")
    public ResponseEntity<Boolean> updateDomain(//@RequestAttribute(value = JwtContants.JWT_USER_CONTEXT, required = false) RequestContextDto requestContext,
                                                @Valid @RequestBody KmsDomainDto domain);
}
