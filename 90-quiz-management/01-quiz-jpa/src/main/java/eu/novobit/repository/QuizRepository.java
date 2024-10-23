package eu.novobit.repository;

import eu.novobit.model.Quiz;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * The interface Quiz repository.
 */
@Repository
public interface QuizRepository extends JpaPagingAndSortingSASCodifiableRepository<Quiz, Long> {

    /**
     * Find by tags in list.
     *
     * @param tags the tags
     * @return the list
     */
    List<Quiz> findByTagsIn(List<String> tags);

    /**
     * Find by category in list.
     *
     * @param categories the categories
     * @return the list
     */
    List<Quiz> findByCategoryIn(List<String> categories);
}
