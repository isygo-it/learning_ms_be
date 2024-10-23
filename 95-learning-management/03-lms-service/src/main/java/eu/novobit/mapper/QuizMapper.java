package eu.novobit.mapper;

import eu.novobit.dto.data.QuizDto;
import eu.novobit.model.Quiz;
import org.mapstruct.Mapper;
import org.mapstruct.NullValueCheckStrategy;

/**
 * The interface Quiz mapper.
 */
@Mapper(nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS, componentModel = "spring")
public interface QuizMapper extends EntityMapper<Quiz, QuizDto> {

}
