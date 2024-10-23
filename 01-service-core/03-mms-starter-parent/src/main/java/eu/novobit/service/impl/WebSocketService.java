package eu.novobit.service.impl;

import eu.novobit.annotation.SrvRepo;
import eu.novobit.com.camel.repository.ICamelRepository;
import eu.novobit.com.rest.service.AbstractCrudBasicsService;
import eu.novobit.dto.wsocket.WsMessageWrapperDto;
import eu.novobit.enumerations.IEnumWsEndpoint;
import eu.novobit.model.ChatMessage;
import eu.novobit.repository.ChatMessageRepository;
import eu.novobit.service.IChatMessageService;
import eu.novobit.service.IWebSocketService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.Date;

/**
 * The type Web socket service.
 */
@Slf4j
@Service
@Transactional
@SrvRepo(value = ChatMessageRepository.class)
public class WebSocketService extends AbstractCrudBasicsService<ChatMessage, ChatMessageRepository>
        implements IWebSocketService {

    @Autowired
    private IChatMessageService chatMessageService;

    @Autowired
    private ICamelRepository camelRepository;

    @Override
    public void saveAndSendToUser(Long recieverId, WsMessageWrapperDto message) throws IOException {
        if (IEnumWsEndpoint.Types.CHAT == message.getEndPoint()) {
            chatMessageService.create(ChatMessage.builder()
                    .receiverId(recieverId)
                    .senderId(message.getSenderId())

                    .message(message.getMessage().getContent())
                    .date(new Date())
                    .build());
        }

        camelRepository.asyncRequestBodyAndHeader(ICamelRepository.send_socket_queue, message, "receiverId", recieverId);
    }

    @Override
    public void saveAndSendToGroup(Long groupId, WsMessageWrapperDto message) {
        //TODO -- Split the message by user and save before
        camelRepository.asyncRequestBodyAndHeader(ICamelRepository.send_socket_queue, message, "receiverId", groupId);
    }

    @Override
    public void saveAndSendToAll(WsMessageWrapperDto message) {
        //TODO -- Split the message by user and save before
        camelRepository.asyncSendBody(ICamelRepository.send_socket_queue, message);
    }
}
