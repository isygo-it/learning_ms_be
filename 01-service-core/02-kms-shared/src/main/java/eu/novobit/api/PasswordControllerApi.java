package eu.novobit.api;

import eu.novobit.constants.JwtContants;
import eu.novobit.constants.RestApiContants;
import eu.novobit.dto.AbstractDto;
import eu.novobit.dto.RequestContextDto;
import eu.novobit.dto.ResetPwdViaTokenRequestDto;
import eu.novobit.dto.request.*;
import eu.novobit.dto.response.AccessResponseDto;
import eu.novobit.enumerations.IEnumAuth;
import eu.novobit.enumerations.IEnumPasswordStatus;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * The interface Password controller api.
 */
public interface PasswordControllerApi {

    /**
     * Generate response entity.
     *
     * @param type               the type
     * @param generatePwdRequest the generate pwd request
     * @return the response entity
     */
    @Operation(summary = "generate Api",
            description = "generate")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Api executed successfully",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = AbstractDto.class))})
    })
    @PostMapping(path = "/generate/{type}")
    ResponseEntity<Boolean> generate(//@RequestAttribute(value = JwtContants.JWT_USER_CONTEXT, required = false) RequestContextDto requestContext,
                                     @Valid @PathVariable(name = RestApiContants.type) IEnumAuth.Types type,
                                     @Valid @RequestBody GeneratePwdRequestDto generatePwdRequest);

    /**
     * Reset password via token response entity.
     *
     * @param resetPwdViaTokenRequestDto the reset pwd via token request dto
     * @return the response entity
     */
    @Operation(summary = "resetPasswordViaToken Api",
            description = "resetPasswordViaToken")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Api executed successfully",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = AbstractDto.class))})
    })
    @PostMapping(path = "/reset-password")
    ResponseEntity<String> resetPasswordViaToken(//@RequestAttribute(value = JwtContants.JWT_USER_CONTEXT, required = false) RequestContextDto requestContext,
                                                 @Valid @RequestBody ResetPwdViaTokenRequestDto resetPwdViaTokenRequestDto);

    /**
     * Change password response entity.
     *
     * @param requestContext the request context
     * @param oldPassword    the old password
     * @param newPassword    the new password
     * @return the response entity
     */
    @Operation(summary = "changePassword Api",
            description = "changePassword")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Api executed successfully",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = AbstractDto.class))})
    })
    @PostMapping(path = "/change-password")
    ResponseEntity<String> changePassword(@RequestAttribute(value = JwtContants.JWT_USER_CONTEXT, required = false) RequestContextDto requestContext,
                                          @RequestParam(name = RestApiContants.OLD_PASSWORD) String oldPassword,
                                          @RequestParam(name = RestApiContants.NEW_PASSWORD) String newPassword);

    /**
     * Pattern check response entity.
     *
     * @param checkPwdRequest the check pwd request
     * @return the response entity
     */
    @Operation(summary = "patternCheck Api",
            description = "patternCheck")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Api executed successfully",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = AbstractDto.class))})
    })
    @PostMapping(path = "/pattern/check")
    ResponseEntity<Boolean> patternCheck(//@RequestAttribute(value = JwtContants.JWT_USER_CONTEXT, required = false) RequestContextDto requestContext,
                                         @Valid @RequestBody CheckPwdRequestDto checkPwdRequest);

    /**
     * Gets access.
     *
     * @param matchPwdRequest the match pwd request
     * @return the access
     */
    @Operation(summary = "getAccess Api",
            description = "getAccess")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Api executed successfully",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = AbstractDto.class))})
    })
    @PostMapping(path = "/access")
    ResponseEntity<AccessResponseDto> getAccess(//@RequestAttribute(value = JwtContants.JWT_USER_CONTEXT, required = false) RequestContextDto requestContext,
                                                @Valid @RequestBody AccessRequestDto matchPwdRequest);

    /**
     * Matches response entity.
     *
     * @param matchesRequest the matches request
     * @return the response entity
     */
    @Operation(summary = "matches Api",
            description = "matches")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Api executed successfully",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = AbstractDto.class))})
    })
    @PostMapping(path = "/matches")
    ResponseEntity<IEnumPasswordStatus.Types> matches(//@RequestAttribute(value = JwtContants.JWT_USER_CONTEXT, required = false) RequestContextDto requestContext,
                                                      @Valid @RequestBody MatchesRequestDto matchesRequest);

    /**
     * Is password expired response entity.
     *
     * @param isPwdExpiredRequestDto the is pwd expired request dto
     * @return the response entity
     */
    @Operation(summary = "isPasswordExpired Api",
            description = "isPasswordExpired")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Api executed successfully",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = AbstractDto.class))})
    })
    @PostMapping(path = "/isExpired")
    ResponseEntity<Boolean> isPasswordExpired(//@RequestAttribute(value = JwtContants.JWT_USER_CONTEXT, required = false) RequestContextDto requestContext,
                                              @Valid @RequestBody IsPwdExpiredRequestDto isPwdExpiredRequestDto);

    /**
     * Update account response entity.
     *
     * @param account the account
     * @return the response entity
     */
    @Operation(summary = "Update account info Api",
            description = "xxx")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Api executed successfully",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = AbstractDto.class))})
    })
    @PostMapping(path = "/account")
    ResponseEntity<Boolean> updateAccount(//@RequestAttribute(value = JwtContants.JWT_USER_CONTEXT, required = false) RequestContextDto requestContext,
                                          @Valid @RequestBody UpdateAccountRequestDto account);
}
