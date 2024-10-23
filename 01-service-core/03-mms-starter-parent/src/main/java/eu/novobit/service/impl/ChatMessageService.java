package eu.novobit.service.impl;

import eu.novobit.annotation.SrvRepo;
import eu.novobit.com.rest.service.AbstractCrudService;
import eu.novobit.config.WsChannelInterceptor;
import eu.novobit.dto.data.ChatAccountDto;
import eu.novobit.dto.wsocket.WsConnectDto;
import eu.novobit.model.ChatMessage;
import eu.novobit.repository.ChatMessageRepository;
import eu.novobit.service.IChatMessageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * The type Chat message service.
 */
@Slf4j
@Service
@Transactional
@SrvRepo(value = ChatMessageRepository.class)
public class ChatMessageService extends AbstractCrudService<ChatMessage, ChatMessageRepository>
        implements IChatMessageService {

    public List<WsConnectDto> getConnectionsByDomain(Long domainId) {
        return WsChannelInterceptor.getConnectionsByDomain(domainId);
    }

    @Override
    public List<ChatMessage> findByReceiverId(Long receiverId, Pageable pageable) {
        return repository().findByReceiverId(receiverId, pageable);
    }

    @Override
    public List<ChatMessage> findByReceiverIdAndSenderId(Long receiverId, Long SenderId, Pageable pageable) {
        return repository().findChatStack(receiverId, SenderId);
    }

    @Override
    public List<ChatAccountDto> getChatAccounts(Long userId, Pageable pageable) {
        List<ChatMessage> list = repository().findByReceiverIdOrSenderIdOrderByDateDesc(userId, userId);
        if (!CollectionUtils.isEmpty(list)) {
            List<ChatAccountDto> chatMessages = list.stream().collect(Collectors.groupingBy(ChatMessage::getGroupKey))
                    .values().stream().map(chats ->
                            ChatAccountDto.builder()
                                    .SenderId(chats.get(0).getSenderId())
                                    .fromFullName(chats.get(0).getSenderName())
                                    .receiverId(chats.get(0).getReceiverId())
                                    .date(chats.get(0).getDate())
                                    .lastMessage(chats.get(0).getMessage())
                                    .read(chats.get(0).getRead())
                                    .status(WsChannelInterceptor.getStatus(userId))
                                    .build()
                    ).collect(Collectors.toList());

            chatMessages.sort((o1, o2) -> o2.getDate().compareTo(o1.getDate()));
            return chatMessages;
        }

        return Collections.EMPTY_LIST;
    }
}
