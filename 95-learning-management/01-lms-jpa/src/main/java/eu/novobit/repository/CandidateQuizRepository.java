package eu.novobit.repository;

import eu.novobit.model.CandidateQuiz;
import org.springframework.stereotype.Repository;

import java.util.Optional;


/**
 * The interface Candidate quiz repository.
 */
@Repository
public interface CandidateQuizRepository extends JpaPagingAndSortingRepository<CandidateQuiz, Long> {

    /**
     * Find by quiz code ignore case and account code ignore case optional.
     *
     * @param quizCode    the quiz code
     * @param accountCode the account code
     * @return the optional
     */
    Optional<CandidateQuiz> findByQuizCodeIgnoreCaseAndAccountCodeIgnoreCase(String quizCode, String accountCode);

    long countByAccountCode(String accountCode);
}
