package eu.novobit.controller;

import eu.novobit.annotation.CtrlDef;
import eu.novobit.api.ChatMessageControllerApi;
import eu.novobit.com.rest.controller.AbstractCrudBasicsController;
import eu.novobit.com.rest.controller.ResponseFactory;
import eu.novobit.dto.RequestContextDto;
import eu.novobit.dto.data.ChatAccountDto;
import eu.novobit.dto.data.ChatMessageDto;
import eu.novobit.dto.wsocket.WsConnectDto;
import eu.novobit.exception.handler.MmsExceptionHandler;
import eu.novobit.mapper.ChatMessageMapper;
import eu.novobit.model.ChatMessage;
import eu.novobit.service.impl.ChatMessageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * The type Chat message controller.
 */
@Slf4j
@Validated
@RestController
@RequestMapping(path = "/api/private/chat")
@CtrlDef(handler = MmsExceptionHandler.class, mapper = ChatMessageMapper.class, service = ChatMessageService.class)
public class ChatMessageController extends AbstractCrudBasicsController<ChatMessage, ChatMessageDto, ChatMessageDto, ChatMessageService>
        implements ChatMessageControllerApi {

    @Override
    public ResponseEntity<List<ChatMessageDto>> findByReceiverId(RequestContextDto requestContext,
                                                                 Long userId,
                                                                 Integer page,
                                                                 Integer size) {
        try {
            List<ChatMessage> list = crudService().findByReceiverId(userId, PageRequest.of(page, size));
            if (CollectionUtils.isEmpty(list)) {
                return ResponseFactory.ResponseNoContent();
            }
            return ResponseFactory.ResponseOk(mapper().listEntityToDto(list));
        } catch (Throwable e) {
            log.error("<Error>: api exception : {} ", e);
            return getBackExceptionResponse(e);
        }
    }

    @Override
    public ResponseEntity<List<ChatMessageDto>> findByReceiverIdAndSenderId(RequestContextDto requestContext,
                                                                            Long userId,
                                                                            Long SenderId,
                                                                            Integer page,
                                                                            Integer size) {
        try {
            List<ChatMessage> list = crudService().findByReceiverIdAndSenderId(userId, SenderId, PageRequest.of(page, size));
            if (CollectionUtils.isEmpty(list)) {
                return ResponseFactory.ResponseNoContent();
            }
            return ResponseFactory.ResponseOk(mapper().listEntityToDto(list));
        } catch (Throwable e) {
            log.error("<Error>: api exception : {} ", e);
            return getBackExceptionResponse(e);
        }
    }

    @Override
    public ResponseEntity<List<ChatAccountDto>> getChatAccounts(RequestContextDto requestContext,
                                                                Long userId,
                                                                Integer page,
                                                                Integer size) {
        try {
            List<ChatAccountDto> list = crudService().getChatAccounts(userId, PageRequest.of(page, size));
            if (CollectionUtils.isEmpty(list)) {
                return ResponseFactory.ResponseNoContent();
            }
            return ResponseFactory.ResponseOk(list);
        } catch (Throwable e) {
            log.error("<Error>: api exception : {} ", e);
            return getBackExceptionResponse(e);
        }
    }

    @Override
    public ResponseEntity<List<WsConnectDto>> getChatStatus(RequestContextDto requestContext,
                                                            Long domainId) {
        try {
            List<WsConnectDto> list = crudService().getConnectionsByDomain(domainId);
            if (CollectionUtils.isEmpty(list)) {
                return ResponseFactory.ResponseNoContent();
            }
            return ResponseFactory.ResponseOk(list);
        } catch (Throwable e) {
            log.error("<Error>: api exception : {} ", e);
            return getBackExceptionResponse(e);
        }
    }
}
