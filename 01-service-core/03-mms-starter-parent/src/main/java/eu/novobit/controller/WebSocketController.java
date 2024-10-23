package eu.novobit.controller;

import eu.novobit.annotation.CtrlHandler;
import eu.novobit.api.WebSocketControllerApi;
import eu.novobit.com.rest.controller.AbstractController;
import eu.novobit.com.rest.controller.ResponseFactory;
import eu.novobit.dto.RequestContextDto;
import eu.novobit.dto.wsocket.WsMessageWrapperDto;
import eu.novobit.exception.handler.MmsExceptionHandler;
import eu.novobit.service.IWebSocketService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * The type Web socket controller.
 */
@Slf4j
@Validated
@RestController
@CtrlHandler(MmsExceptionHandler.class)
@RequestMapping(path = "/api/private/ws")
public class WebSocketController extends AbstractController implements WebSocketControllerApi {

    @Autowired
    private IWebSocketService webSocketService;

    @Override
    public ResponseEntity<?> sendMessageToUser(RequestContextDto requestContextDto,
                                               Long recieverId, WsMessageWrapperDto message) {
        try {
            webSocketService.saveAndSendToUser(recieverId, message);
            return ResponseFactory.ResponseOk();
        } catch (Exception ex) {
            return getBackExceptionResponse(ex);
        }
    }

    @Override
    public ResponseEntity<?> sendMessageToGroup(RequestContextDto requestContextDto,
                                                Long groupId, WsMessageWrapperDto message) {
        try {
            webSocketService.saveAndSendToGroup(groupId, message);
            return ResponseFactory.ResponseOk();
        } catch (Exception ex) {
            return getBackExceptionResponse(ex);
        }
    }

    @Override
    public ResponseEntity<?> sendMessageToAll(RequestContextDto requestContextDto,
                                              WsMessageWrapperDto message) {
        try {
            webSocketService.saveAndSendToAll(message);
            return ResponseFactory.ResponseOk();
        } catch (Exception ex) {
            return getBackExceptionResponse(ex);
        }
    }
}
