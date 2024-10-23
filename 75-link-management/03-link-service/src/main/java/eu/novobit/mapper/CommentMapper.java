package eu.novobit.mapper;

import eu.novobit.dto.data.CommentDto;
import eu.novobit.model.Comment;
import org.mapstruct.Mapper;
import org.mapstruct.NullValueCheckStrategy;

@Mapper(nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS, componentModel = "spring")
public interface CommentMapper extends EntityMapper<Comment, CommentDto> {
}
