package eu.novobit.repository;

import eu.novobit.model.QuizQuestion;
import org.springframework.stereotype.Repository;


/**
 * The interface Quiz repository.
 */
@Repository
public interface QuizQuestionRepository extends JpaPagingAndSortingRepository<QuizQuestion, Long> {

}
