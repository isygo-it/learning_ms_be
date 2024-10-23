package eu.novobit.service;

import eu.novobit.com.rest.service.ICrudServiceMethods;
import eu.novobit.dto.data.ChatAccountDto;
import eu.novobit.dto.wsocket.WsConnectDto;
import eu.novobit.model.ChatMessage;
import org.springframework.data.domain.Pageable;

import java.util.List;


/**
 * The interface Chat message service.
 */
public interface IChatMessageService extends ICrudServiceMethods<ChatMessage> {

    /**
     * Find by receiver id list.
     *
     * @param receiverId the receiver id
     * @param pageable   the pageable
     * @return the list
     */
    List<ChatMessage> findByReceiverId(Long receiverId, Pageable pageable);

    /**
     * Find by receiver id and sender id list.
     *
     * @param receiverId the receiver id
     * @param SenderId   the sender id
     * @param pageable   the pageable
     * @return the list
     */
    List<ChatMessage> findByReceiverIdAndSenderId(Long receiverId, Long SenderId, Pageable pageable);

    /**
     * Gets chat accounts.
     *
     * @param userId   the user id
     * @param pageable the pageable
     * @return the chat accounts
     */
    List<ChatAccountDto> getChatAccounts(Long userId, Pageable pageable);

    /**
     * Gets connections by domain.
     *
     * @param domainId the domain id
     * @return the connections by domain
     */
    public List<WsConnectDto> getConnectionsByDomain(Long domainId);
}
