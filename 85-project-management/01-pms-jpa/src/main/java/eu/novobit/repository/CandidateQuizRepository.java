package eu.novobit.repository;

import eu.novobit.model.CandidateQuiz;
import org.springframework.stereotype.Repository;


/**
 * The interface Candidate quiz repository.
 */
@Repository
public interface CandidateQuizRepository extends JpaPagingAndSortingCodifiableRepository<CandidateQuiz, Long> {

}
