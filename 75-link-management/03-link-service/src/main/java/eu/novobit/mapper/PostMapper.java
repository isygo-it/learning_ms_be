package eu.novobit.mapper;

import eu.novobit.dto.data.PostDto;
import eu.novobit.model.Post;
import org.mapstruct.Mapper;
import org.mapstruct.NullValueCheckStrategy;

/**
 * The interface Post mapper.
 */
@Mapper(nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS, componentModel = "spring")
public interface PostMapper extends EntityMapper<Post, PostDto> {

}
