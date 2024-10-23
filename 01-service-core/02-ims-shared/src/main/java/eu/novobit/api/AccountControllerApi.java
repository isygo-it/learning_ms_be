package eu.novobit.api;

import eu.novobit.com.rest.api.IMappedCrudApi;
import eu.novobit.constants.JwtContants;
import eu.novobit.constants.RestApiContants;
import eu.novobit.dto.AbstractDto;
import eu.novobit.dto.RequestContextDto;
import eu.novobit.dto.ResetPwdViaTokenRequestDto;
import eu.novobit.dto.data.AccountDto;
import eu.novobit.dto.data.MinAccountDto;
import eu.novobit.dto.response.UserDataResponseDto;
import eu.novobit.enumerations.IEnumBinaryStatus;
import eu.novobit.enumerations.IEnumLanguage;
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
 * The interface Account controller api.
 */
public interface AccountControllerApi extends IMappedCrudApi<AccountDto, AccountDto> {


    /**
     * Gets emails by domain.
     *
     * @param requestContext the request context
     * @return the emails by domain
     */
    @Operation(summary = "get Emails By Domain Api",
            description = "get Emails By Domain")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Api executed successfully",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = String.class))})
    })
    @GetMapping(path = "/emails")
    ResponseEntity<List<String>> getEmailsByDomain(@RequestAttribute(value = JwtContants.JWT_USER_CONTEXT, required = false) RequestContextDto requestContext);


    /**
     * Gets accounts.
     *
     * @param requestContext the request context
     * @return the accounts
     */
    @Operation(summary = "Get Accounts Api",
            description = "Get Accounts")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Api executed successfully",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = MinAccountDto.class))})
    })
    @GetMapping(path = "/accounts-info")
    ResponseEntity<List<MinAccountDto>> getAccounts(@RequestAttribute(value = JwtContants.JWT_USER_CONTEXT, required = false) RequestContextDto requestContext);


    /**
     * Update account admin status response entity.
     *
     * @param requestContext the request context
     * @param id             the id
     * @param newStatus      the new status
     * @return the response entity
     */
    @Operation(summary = "Update Account Admin Status Api",
            description = "Update Account Admin Status")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Api executed successfully",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = AccountDto.class))})
    })
    @PutMapping(path = "/updateStatusAccount")
    ResponseEntity<AccountDto> updateAccountAdminStatus(@RequestAttribute(value = JwtContants.JWT_USER_CONTEXT, required = false) RequestContextDto requestContext,
                                                        @RequestParam(name = RestApiContants.ID) Long id,
                                                        @RequestParam(name = RestApiContants.NEW_STATUS) IEnumBinaryStatus.Types newStatus);


    /**
     * Update account is admin response entity.
     *
     * @param requestContext the request context
     * @param id             the id
     * @param newStatus      the new status
     * @return the response entity
     */
    @Operation(summary = "Update Account IsAdmin Api",
            description = "Update Account IsAdmin")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Api executed successfully",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = AccountDto.class))})
    })
    @PutMapping(path = "/updateIsAdmin")
    ResponseEntity<AccountDto> updateAccountIsAdmin(@RequestAttribute(value = JwtContants.JWT_USER_CONTEXT, required = false) RequestContextDto requestContext,
                                                    @RequestParam(name = RestApiContants.ID) Long id,
                                                    @RequestParam(name = RestApiContants.NEW_STATUS) boolean newStatus);


    /**
     * Update language response entity.
     *
     * @param requestContext the request context
     * @param id             the id
     * @param language       the language
     * @return the response entity
     */
    @Operation(summary = "Update Account Language Api",
            description = "Update Account Language")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Api executed successfully",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = AccountDto.class))})
    })
    @PutMapping(path = "/updateLanguage")
    ResponseEntity<AccountDto> updateLanguage(@RequestAttribute(value = JwtContants.JWT_USER_CONTEXT, required = false) RequestContextDto requestContext,
                                              @RequestParam(name = RestApiContants.ID) Long id,
                                              @RequestParam(name = RestApiContants.language) IEnumLanguage.Types language);

    /**
     * Connected user response entity.
     *
     * @param requestContext the request context
     * @return the response entity
     */
    @Operation(summary = "Get Connected User/Account data info Api",
            description = "Get Connected User/Account data info")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Api executed successfully",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserDataResponseDto.class))})
    })
    @GetMapping(path = "/me")
    ResponseEntity<UserDataResponseDto> connectedUser(@RequestAttribute(value = JwtContants.JWT_USER_CONTEXT, required = false) RequestContextDto requestContext);

    /**
     * Connected user full data response entity.
     *
     * @param requestContext the request context
     * @return the response entity
     */
    @Operation(summary = "Get Connected User/Account full data info  Api",
            description = "Get Connected User/Account full data info ")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Api executed successfully",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = AccountDto.class))})
    })
    @GetMapping(path = "/profile")
    ResponseEntity<AccountDto> connectedUserFullData(@RequestAttribute(value = JwtContants.JWT_USER_CONTEXT, required = false) RequestContextDto requestContext);

    /**
     * Update connected user profile response entity.
     *
     * @param requestContext the request context
     * @param accountDto     the account dto
     * @return the response entity
     */
    @Operation(summary = "Update connected user data Api",
            description = "Update connected user data")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Api executed successfully",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = AccountDto.class))})
    })
    @PutMapping(path = "/profile")
    ResponseEntity<AccountDto> updateConnectedUserProfile(@RequestAttribute(value = JwtContants.JWT_USER_CONTEXT, required = false) RequestContextDto requestContext,
                                                          @Valid @RequestBody AccountDto accountDto);

    /**
     * Connected user update account response entity.
     *
     * @param requestContext the request context
     * @param id             the id
     * @param accountDto     the account dto
     * @return the response entity
     */
    @Operation(summary = "Update Connected User Account Api",
            description = "Update Connected User Account")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Api executed successfully",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = AccountDto.class))})
    })
    @PutMapping(path = "/update-account/{id}")
    ResponseEntity<AccountDto> connectedUserUpdateAccount(@RequestAttribute(value = JwtContants.JWT_USER_CONTEXT, required = false) RequestContextDto requestContext,
                                                          @PathVariable(name = RestApiContants.ID) Long id,
                                                          @Valid @RequestBody AccountDto accountDto);

    /**
     * Reset password via token response entity.
     *
     * @param requestContext               the request context
     * @param resetPasswordViaTokenRequest the reset password via token request
     * @return the response entity
     */
    @Operation(summary = "Reset Password Via Token Api",
            description = "Reset Password Via Token")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Api executed successfully",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = String.class))})
    })
    @PostMapping(path = "/password/reset/token")
    ResponseEntity<String> resetPasswordViaToken(@RequestAttribute(value = JwtContants.JWT_USER_CONTEXT, required = false) RequestContextDto requestContext,
                                                 @Valid @RequestBody ResetPwdViaTokenRequestDto resetPasswordViaTokenRequest);


    /**
     * Accounts by domain response entity.
     *
     * @param requestContext the request context
     * @return the response entity
     */
    @Operation(summary = "Get Accounts By Domain Api",
            description = "Get Accounts By Domain")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Api executed successfully",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = MinAccountDto.class))})
    })
    @GetMapping(path = "/domain")
    ResponseEntity<List<MinAccountDto>> accountsByDomain(@RequestAttribute(value = JwtContants.JWT_USER_CONTEXT, required = false) RequestContextDto requestContext);

    /**
     * Chat accounts by domain response entity.
     *
     * @param requestContext the request context
     * @return the response entity
     */
    @Operation(summary = "Get Chat Accounts By Domain Api",
            description = "Get Chat Accounts By Domain")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Api executed successfully",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = AbstractDto.class))})
    })
    @GetMapping(path = "/chat/domain")
    ResponseEntity<List<MinAccountDto>> chatAccountsByDomain(@RequestAttribute(value = JwtContants.JWT_USER_CONTEXT, required = false) RequestContextDto requestContext);

    /**
     * Resend email creation response entity.
     *
     * @param requestContext the request context
     * @param id             the id
     * @return the response entity
     */
    @Operation(summary = "Resend Creation Email Api",
            description = "Resend Creation Email")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Api executed successfully",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = AbstractDto.class))})
    })
    @GetMapping(path = "/resend/email/creation/{id}")
    ResponseEntity<?> resendCreationEmail(@RequestAttribute(value = JwtContants.JWT_USER_CONTEXT, required = false) RequestContextDto requestContext,
                                          @PathVariable(name = RestApiContants.ID) Long id);

}
