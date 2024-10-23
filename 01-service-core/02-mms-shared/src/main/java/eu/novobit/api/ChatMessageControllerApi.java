package eu.novobit.api;

import eu.novobit.constants.JwtContants;
import eu.novobit.constants.RestApiContants;
import eu.novobit.dto.AbstractDto;
import eu.novobit.dto.RequestContextDto;
import eu.novobit.dto.data.ChatAccountDto;
import eu.novobit.dto.data.ChatMessageDto;
import eu.novobit.dto.wsocket.WsConnectDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * The interface Chat message controller api.
 */
public interface ChatMessageControllerApi {

    /**
     * Find by receiver id response entity.
     *
     * @param requestContext the request context
     * @param userId         the user id
     * @param page           the page
     * @param size           the size
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
    @GetMapping(path = "/user")
    public ResponseEntity<List<ChatMessageDto>> findByReceiverId(@RequestAttribute(value = JwtContants.JWT_USER_CONTEXT, required = false) RequestContextDto requestContext,
                                                                 @RequestParam(name = RestApiContants.userId) Long userId,
                                                                 @RequestParam(name = RestApiContants.page) Integer page,
                                                                 @RequestParam(name = RestApiContants.size) Integer size);

    /**
     * Find by receiver id and sender id response entity.
     *
     * @param requestContext the request context
     * @param userId         the user id
     * @param SenderId       the sender id
     * @param page           the page
     * @param size           the size
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
    @GetMapping(path = "/from")
    public ResponseEntity<List<ChatMessageDto>> findByReceiverIdAndSenderId(@RequestAttribute(value = JwtContants.JWT_USER_CONTEXT, required = false) RequestContextDto requestContext,
                                                                            @RequestParam(name = RestApiContants.userId) Long userId,
                                                                            @RequestParam(name = RestApiContants.SenderId) Long SenderId,
                                                                            @RequestParam(name = RestApiContants.page) Integer page,
                                                                            @RequestParam(name = RestApiContants.size) Integer size);

    /**
     * Gets chat accounts.
     *
     * @param requestContext the request context
     * @param userId         the user id
     * @param page           the page
     * @param size           the size
     * @return the chat accounts
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
    public ResponseEntity<List<ChatAccountDto>> getChatAccounts(@RequestAttribute(value = JwtContants.JWT_USER_CONTEXT, required = false) RequestContextDto requestContext,
                                                                @RequestParam(name = RestApiContants.userId) Long userId,
                                                                @RequestParam(name = RestApiContants.page) Integer page,
                                                                @RequestParam(name = RestApiContants.size) Integer size);

    /**
     * Gets chat status.
     *
     * @param requestContext the request context
     * @param domainId       the domain id
     * @return the chat status
     */
    @Operation(summary = "xxx Api",
            description = "xxx")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Api executed successfully",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = AbstractDto.class))})
    })
    @GetMapping(path = "/status/domain")
    public ResponseEntity<List<WsConnectDto>> getChatStatus(@RequestAttribute(value = JwtContants.JWT_USER_CONTEXT, required = false) RequestContextDto requestContext,
                                                            @RequestParam(name = RestApiContants.domainId) Long domainId);
}
