package eu.novobit.mapper;

import eu.novobit.dto.data.AuthorDto;
import eu.novobit.model.Author;
import org.mapstruct.Mapper;
import org.mapstruct.NullValueCheckStrategy;

/**
 * The interface Author mapper.
 */
@Mapper(nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS, componentModel = "spring")
public interface AuthorMapper extends EntityMapper<Author, AuthorDto> {

}
