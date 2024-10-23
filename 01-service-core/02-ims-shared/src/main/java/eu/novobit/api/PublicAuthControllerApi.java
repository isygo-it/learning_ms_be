package eu.novobit.api;

import eu.novobit.constants.RestApiContants;
import eu.novobit.dto.AbstractDto;
import eu.novobit.dto.UserContextDto;
import eu.novobit.dto.data.DomainDto;
import eu.novobit.dto.request.AccountAuthTypeRequest;
import eu.novobit.dto.request.AuthRequestDto;
import eu.novobit.dto.request.RegisterNewAccountDto;
import eu.novobit.dto.response.AuthResponseDto;
import eu.novobit.dto.response.UserContext;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * The interface Public auth controller api.
 */
public interface PublicAuthControllerApi {

    /**
     * Authenticate response entity.
     *
     * @param request        the request
     * @param response       the response
     * @param authRequestDto the auth request dto
     * @return the response entity
     */
    @Operation(summary = "authenticate Api",
            description = "authenticate")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Api executed successfully",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = AbstractDto.class))})
    })
    @PostMapping(path = "/authenticate")
    ResponseEntity<AuthResponseDto> authenticate(HttpServletRequest request,
                                                 HttpServletResponse response,
                                                 @Valid @RequestBody AuthRequestDto authRequestDto);

    /**
     * Generate forgot pwd token response entity.
     *
     * @param userContextDto the user context dto
     * @return the response entity
     */
    @Operation(summary = "generateForgotPWDToken Api",
            description = "generateForgotPWDToken")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Api executed successfully",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = AbstractDto.class))})
    })
    @PostMapping(path = "/password/forgotten")
    ResponseEntity<Boolean> generateForgotPWDToken(@Valid @RequestBody UserContextDto userContextDto);

    /**
     * Gets authentication type.
     *
     * @param accountAuthTypeRequest the switch auth type request
     * @return the authentication type
     */
    @Operation(summary = "getAuthenticationType Api",
            description = "getAuthenticationType")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Api executed successfully",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = AbstractDto.class))})
    })
    @PostMapping(path = "/checkAuthType")
    ResponseEntity<UserContext> getAuthenticationType(@Valid @RequestBody AccountAuthTypeRequest accountAuthTypeRequest);

    /**
     * Switch auth type response entity.
     *
     * @param accountAuthTypeRequest the switch auth type request
     * @return the response entity
     */
    @Operation(summary = "switchAuthType Api",
            description = "switchAuthType")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Api executed successfully",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = AbstractDto.class))})
    })
    @PostMapping(path = "/updateAuthType")
    ResponseEntity<Boolean> switchAuthType(@Valid @RequestBody AccountAuthTypeRequest accountAuthTypeRequest);

    /**
     * Register new account response entity.
     *
     * @param registerNewAccountDto the register new account dto
     * @return the response entity
     */
    @Operation(summary = "registerNewAccount Api",
            description = "registerNewAccount")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Api executed successfully",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = AbstractDto.class))})
    })

    @PostMapping(path = "/register")
    ResponseEntity<Boolean> registerNewAccount(@Valid @RequestBody RegisterNewAccountDto registerNewAccountDto);

    /**
     * Gets domain by name.
     *
     * @param domain the domain
     * @return the domain by name
     */
    @Operation(summary = "getDomainByName Api",
            description = "getDomainByName")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Api executed successfully",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = AbstractDto.class))})
    })
    @GetMapping(path = "/domain")
    public ResponseEntity<DomainDto> getDomainByName(@RequestParam(name = RestApiContants.DOMAIN_NAME) String domain);
}
