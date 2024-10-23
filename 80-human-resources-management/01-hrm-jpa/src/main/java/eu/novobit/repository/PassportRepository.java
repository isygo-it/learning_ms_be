package eu.novobit.repository;

import eu.novobit.model.Passport;
import org.springframework.stereotype.Repository;


/**
 * The interface Passport repository.
 */
@Repository
public interface PassportRepository extends JpaPagingAndSortingRepository<Passport, Long> {

}
