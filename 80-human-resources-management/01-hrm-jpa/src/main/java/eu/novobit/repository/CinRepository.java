package eu.novobit.repository;

import eu.novobit.model.Cin;
import org.springframework.stereotype.Repository;


/**
 * The interface Cin repository.
 */
@Repository
public interface CinRepository extends JpaPagingAndSortingRepository<Cin, Long> {

}
