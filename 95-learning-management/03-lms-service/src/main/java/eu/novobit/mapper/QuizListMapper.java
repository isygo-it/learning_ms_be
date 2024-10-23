package eu.novobit.mapper;

import eu.novobit.dto.data.QuizListDto;
import eu.novobit.model.Quiz;
import org.mapstruct.Mapper;
import org.mapstruct.NullValueCheckStrategy;

/**
 * The interface Quiz list mapper.
 */
@Mapper(nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS, componentModel = "spring")
public interface QuizListMapper extends EntityMapper<Quiz, QuizListDto> {

}
