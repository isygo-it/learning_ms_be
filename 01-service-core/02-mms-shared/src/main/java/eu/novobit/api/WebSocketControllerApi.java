package eu.novobit.api;

import eu.novobit.constants.JwtContants;
import eu.novobit.constants.RestApiContants;
import eu.novobit.dto.AbstractDto;
import eu.novobit.dto.RequestContextDto;
import eu.novobit.dto.wsocket.WsMessageWrapperDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * The interface Web socket controller api.
 */
public interface WebSocketControllerApi {

    /**
     * Send message to user response entity.
     *
     * @param requestContext the request context
     * @param recieverId     the reciever id
     * @param message        the message
     * @return the response entity
     */
    @Operation(summary = "sendMessageToUser Api",
            description = "sendMessageToUser")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Api executed successfully",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = AbstractDto.class))})
    })
    @PostMapping("/user/send")
    ResponseEntity<?> sendMessageToUser(@RequestAttribute(value = JwtContants.JWT_USER_CONTEXT, required = false) RequestContextDto requestContext,
                                        @RequestParam(name = RestApiContants.recieverId) Long recieverId,
                                        @Valid @RequestBody WsMessageWrapperDto message);

    /**
     * Send message to group response entity.
     *
     * @param requestContext the request context
     * @param groupId        the group id
     * @param message        the message
     * @return the response entity
     */
    @Operation(summary = "sendMessageToGroup Api",
            description = "sendMessageToGroup")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Api executed successfully",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = AbstractDto.class))})
    })
    @PostMapping("/group/send")
    ResponseEntity<?> sendMessageToGroup(@RequestAttribute(value = JwtContants.JWT_USER_CONTEXT, required = false) RequestContextDto requestContext,
                                         @RequestParam(name = RestApiContants.groupId) Long groupId,
                                         @Valid @RequestBody WsMessageWrapperDto message);

    /**
     * Send message to all response entity.
     *
     * @param requestContext the request context
     * @param message        the message
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
    @PostMapping("/all/send")
    ResponseEntity<?> sendMessageToAll(@RequestAttribute(value = JwtContants.JWT_USER_CONTEXT, required = false) RequestContextDto requestContext,
                                       @Valid @RequestBody WsMessageWrapperDto message);
}
