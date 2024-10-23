package eu.novobit.repository;

import eu.novobit.model.Post;
import org.springframework.stereotype.Repository;


/**
 * The interface Post repository.
 */
@Repository
public interface PostRepository extends JpaPagingAndSortingSASRepository<Post, Long> {

}
