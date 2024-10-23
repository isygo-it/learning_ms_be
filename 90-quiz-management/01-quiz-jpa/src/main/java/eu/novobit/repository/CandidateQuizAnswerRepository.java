package eu.novobit.repository;

import eu.novobit.model.CandidateQuizAnswer;
import org.springframework.stereotype.Repository;


/**
 * The interface Candidate quiz answer repository.
 */
@Repository
public interface CandidateQuizAnswerRepository extends JpaPagingAndSortingRepository<CandidateQuizAnswer, Long> {

}
