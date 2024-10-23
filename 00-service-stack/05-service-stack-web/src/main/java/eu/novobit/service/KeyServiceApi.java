package eu.novobit.service;

import eu.novobit.constants.JwtContants;
import eu.novobit.constants.RestApiContants;
import eu.novobit.dto.AbstractDto;
import eu.novobit.dto.RequestContextDto;
import eu.novobit.enumerations.IEnumCharSet;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;


/**
 * The interface Key service api.
 */
public interface KeyServiceApi {

    /**
     * Generate random key response entity.
     *
     * @param requestContext the request context
     * @param length         the length
     * @param charSetType    the char set type
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
    @GetMapping(path = "/random/{length}/{charSetType}")
    ResponseEntity<String> generateRandomKey(@RequestAttribute(value = JwtContants.JWT_USER_CONTEXT, required = false) RequestContextDto requestContext,
                                             @PathVariable(name = RestApiContants.length) Integer length,
                                             @PathVariable(name = RestApiContants.charSetType) IEnumCharSet.Types charSetType);

    /**
     * Renew key by name response entity.
     *
     * @param requestContext the request context
     * @param domain         the domain
     * @param keyName        the key name
     * @param length         the length
     * @param charSetType    the char set type
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
    @PostMapping(path = "/random/renew/{domain}/{keyName}/{length}/{charSetType}")
    ResponseEntity<String> renewKeyByName(@RequestAttribute(value = JwtContants.JWT_USER_CONTEXT, required = false) RequestContextDto requestContext,
                                          @PathVariable(name = RestApiContants.DOMAIN_NAME) String domain,
                                          @PathVariable(name = RestApiContants.keyName) String keyName,
                                          @PathVariable(name = RestApiContants.length) Integer length,
                                          @PathVariable(name = RestApiContants.charSetType) IEnumCharSet.Types charSetType);

    /**
     * Gets key by name.
     *
     * @param requestContext the request context
     * @param domain         the domain
     * @param keyName        the key name
     * @return the key by name
     */
    @Operation(summary = "xxx Api",
            description = "xxx")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Api executed successfully",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = AbstractDto.class))})
    })
    @GetMapping(path = "/random/get/{domain}/{keyName}")
    ResponseEntity<String> getKeyByName(@RequestAttribute(value = JwtContants.JWT_USER_CONTEXT, required = false) RequestContextDto requestContext,
                                        @PathVariable(name = RestApiContants.DOMAIN_NAME) String domain,
                                        @PathVariable(name = RestApiContants.keyName) String keyName);
}