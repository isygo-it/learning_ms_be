package eu.novobit.repository;

import eu.novobit.model.Vacation;
import org.springframework.stereotype.Repository;


/**
 * The interface Vacation repository.
 */
@Repository
public interface VacationRepository extends JpaPagingAndSortingRepository<Vacation, Long> {

}
