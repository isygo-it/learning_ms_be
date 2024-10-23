package eu.novobit.repository;

import eu.novobit.model.Author;
import org.springframework.stereotype.Repository;


/**
 * The interface Author repository.
 */
@Repository
public interface AuthorRepository extends JpaPagingAndSortingSASCodifiableRepository<Author, Long> {

}