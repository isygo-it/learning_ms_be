package eu.novobit.repository;

import eu.novobit.model.AccountProps;
import org.springframework.stereotype.Repository;

import java.util.Optional;


/**
 * The interface Acount props repository.
 */
@Repository
public interface AcountPropsRepository extends JpaPagingAndSortingRepository<AccountProps, Long> {

    /**
     * Find by account code ignore case optional.
     *
     * @param accountCode the account code
     * @return the optional
     */
    Optional<AccountProps> findByAccount_CodeIgnoreCase(String accountCode);
}
