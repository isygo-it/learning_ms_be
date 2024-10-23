package eu.novobit.repository;

import eu.novobit.enumerations.IEnumBinaryStatus;
import eu.novobit.model.Customer;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * The interface Customer repository.
 */
public interface CustomerRepository extends JpaPagingAndSortingSASCodifiableRepository<Customer, Long> {

    /**
     * Update admin status by id int.
     *
     * @param id        the id
     * @param newStatus the new status
     * @return the int
     */
    @Modifying
    @Query("UPDATE Customer c SET c.adminStatus = :newStatus WHERE c.id = :id")
    int updateAdminStatusById(@Param("id") Long id,
                              @Param("newStatus") IEnumBinaryStatus.Types newStatus);
}
