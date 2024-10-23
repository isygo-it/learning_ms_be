package eu.novobit.mapper;

import eu.novobit.dto.data.CandidateQuizAnswerDto;
import eu.novobit.model.CandidateQuizAnswer;
import org.mapstruct.Mapper;
import org.mapstruct.NullValueCheckStrategy;

/**
 * The interface Candidate quiz answer mapper.
 */
@Mapper(nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS, componentModel = "spring")
public interface CandidateQuizAnswerMapper extends EntityMapper<CandidateQuizAnswer, CandidateQuizAnswerDto> {

}
