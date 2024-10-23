package eu.novobit.repository;

import eu.novobit.model.Quiz;
import org.springframework.stereotype.Repository;


/**
 * The interface Quiz repository.
 */
@Repository
public interface QuizRepository extends JpaPagingAndSortingCodifiableRepository<Quiz, Long> {

}
