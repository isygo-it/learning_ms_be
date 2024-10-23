package eu.novobit.api;

import eu.novobit.constants.JwtContants;
import eu.novobit.constants.RestApiContants;
import eu.novobit.dto.AbstractDto;
import eu.novobit.dto.RequestContextDto;
import eu.novobit.dto.data.PropertyDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * The interface Property controller api.
 */
public interface PropertyControllerApi {

    /**
     * Gets property by account.
     *
     * @param requestContext the request context
     * @param accountCode    the account code
     * @param guiName        the gui name
     * @param name           the name
     * @return the property by account
     */
    @Operation(summary = "xxx Api",
            description = "xxx")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Api executed successfully",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = AbstractDto.class))})
    })
    @GetMapping(path = "/account")
    ResponseEntity<PropertyDto> getPropertyByAccount(@RequestAttribute(value = JwtContants.JWT_USER_CONTEXT, required = false) RequestContextDto requestContext,
                                                     @RequestParam(name = RestApiContants.accountCode) String accountCode,
                                                     @RequestParam(name = RestApiContants.guiName) String guiName,
                                                     @RequestParam(name = RestApiContants.name) String name);

    /**
     * Gets property by account and gui.
     *
     * @param requestContext the request context
     * @param accountCode    the account code
     * @param guiName        the gui name
     * @return the property by account and gui
     */
    @Operation(summary = "xxx Api",
            description = "xxx")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Api executed successfully",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = AbstractDto.class))})
    })
    @GetMapping(path = "/account/gui")
    ResponseEntity<List<PropertyDto>> getPropertyByAccountAndGui(@RequestAttribute(value = JwtContants.JWT_USER_CONTEXT, required = false) RequestContextDto requestContext,
                                                                 @RequestParam(name = RestApiContants.accountCode) String accountCode,
                                                                 @RequestParam(name = RestApiContants.guiName) String guiName);

    /**
     * Update property account response entity.
     *
     * @param requestContext the request context
     * @param accountCode    the account code
     * @param property       the property
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
    @PutMapping(path = "/account")
    ResponseEntity<PropertyDto> updatePropertyAccount(@RequestAttribute(value = JwtContants.JWT_USER_CONTEXT, required = false) RequestContextDto requestContext,
                                                      @RequestParam(name = RestApiContants.code) String accountCode,
                                                      @Valid @RequestBody PropertyDto property);
}
