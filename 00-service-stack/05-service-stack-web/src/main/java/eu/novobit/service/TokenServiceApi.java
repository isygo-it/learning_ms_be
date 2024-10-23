package eu.novobit.service;

import eu.novobit.constants.JwtContants;
import eu.novobit.constants.RestApiContants;
import eu.novobit.dto.AbstractDto;
import eu.novobit.dto.RequestContextDto;
import eu.novobit.dto.TokenDto;
import eu.novobit.dto.data.TokenRequestDto;
import eu.novobit.enumerations.IEnumAppToken;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * The interface Token service api.
 */
public interface TokenServiceApi {

    /**
     * Create token by domain response entity.
     *
     * @param domain          the domain
     * @param application     the application
     * @param tokenType       the token type
     * @param tokenRequestDto the token request dto
     * @return the response entity
     */
    @Operation(summary = "createTokenByDomain Api",
            description = "createTokenByDomain")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Api executed successfully",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = AbstractDto.class))})
    })
    @PostMapping(path = "/{domain}/{application}/{tokenType}")
    ResponseEntity<TokenDto> createTokenByDomain(//@RequestAttribute(value = JwtContants.JWT_USER_CONTEXT, required = false) RequestContextDto requestContext,
                                                 @PathVariable(name = RestApiContants.DOMAIN_NAME) String domain,
                                                 @PathVariable(name = RestApiContants.application) String application,
                                                 @PathVariable(name = RestApiContants.tokenType) IEnumAppToken.Types tokenType,
                                                 @Valid @RequestBody TokenRequestDto tokenRequestDto);

    /**
     * Is token valid response entity.
     *
     * @param requestContext the request context
     * @param domain         the domain
     * @param application    the application
     * @param tokenType      the token type
     * @param token          the token
     * @param subject        the subject
     * @return the response entity
     */
    @Operation(summary = "isTokenValid Api",
            description = "isTokenValid")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Api executed successfully",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = AbstractDto.class))})
    })
    @GetMapping(path = "/valid/{domain}/{application}/{tokenType}/{token}/{subject}")
    ResponseEntity<Boolean> isTokenValid(@RequestAttribute(value = JwtContants.JWT_USER_CONTEXT, required = false) RequestContextDto requestContext,
                                         @PathVariable(name = RestApiContants.DOMAIN_NAME) String domain,
                                         @PathVariable(name = RestApiContants.application) String application,
                                         @PathVariable(name = RestApiContants.tokenType) IEnumAppToken.Types tokenType,
                                         @PathVariable(name = RestApiContants.token) String token,
                                         @PathVariable(name = RestApiContants.subject) String subject);
}