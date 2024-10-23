package eu.novobit.mapper;

import eu.novobit.dto.data.ChatMessageDto;
import eu.novobit.model.ChatMessage;
import org.mapstruct.Mapper;
import org.mapstruct.NullValueCheckStrategy;

/**
 * The interface Chat message mapper.
 */
@Mapper(nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS, componentModel = "spring")
public interface ChatMessageMapper extends EntityMapper<ChatMessage, ChatMessageDto> {
}
