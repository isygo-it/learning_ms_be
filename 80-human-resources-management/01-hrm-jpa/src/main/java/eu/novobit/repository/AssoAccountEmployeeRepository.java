package eu.novobit.repository;

import eu.novobit.model.AssoAccountEmployee;

import java.util.Optional;

/**
 * The interface Asso account employee repository.
 */
public interface AssoAccountEmployeeRepository extends JpaPagingAndSortingRepository<AssoAccountEmployee, Long> {

    /**
     * Find by account code ignore case optional.
     *
     * @param accountCode the account code
     * @return the optional
     */
    Optional<AssoAccountEmployee> findByAccountCodeIgnoreCase(String accountCode);
}
