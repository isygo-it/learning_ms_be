package eu.novobit.mapper;

import eu.novobit.dto.data.CandidateQuizDto;
import eu.novobit.model.CandidateQuiz;
import org.mapstruct.Mapper;
import org.mapstruct.NullValueCheckStrategy;

/**
 * The interface Candidate quiz mapper.
 */
@Mapper(nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS, componentModel = "spring")
public interface CandidateQuizMapper extends EntityMapper<CandidateQuiz, CandidateQuizDto> {

}
