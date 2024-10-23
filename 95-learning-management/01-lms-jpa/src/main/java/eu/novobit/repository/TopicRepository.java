package eu.novobit.repository;

import eu.novobit.model.Topic;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * The interface Topic repository.
 */
@Repository
public interface TopicRepository extends JpaPagingAndSortingSASCodifiableRepository<Topic, Long> {
        List<Topic> findAllByIdIn(Long[] ids);
}