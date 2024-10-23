package eu.novobit.repository;

import eu.novobit.model.Contract;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


/**
 * The interface Contract repository.
 */
@Repository
public interface ContractRepository extends JpaPagingAndSortingSASCodifiableRepository<Contract, Long> {

    /**
     * Update contract status.
     *
     * @param isLocked the isLocked
     * @param id       the id
     */
    @Modifying
    @Query("update Contract c set c.isLocked = :isLocked  where c.id = :id")
    void updateContractStatus(@Param("isLocked") Boolean isLocked, @Param("id") Long id);
}
