package eu.novobit.repository;

import eu.novobit.model.RegistredUser;
import org.springframework.stereotype.Repository;

/**
 * The interface Registred new account repository.
 */
@Repository
public interface RegistredNewAccountRepository extends JpaPagingAndSortingRepository<RegistredUser, Long> {
}
