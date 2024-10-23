package eu.novobit.repository;

import eu.novobit.model.InsuranceSecurity;
import org.springframework.stereotype.Repository;


/**
 * The interface Insurance security repository.
 */
@Repository
public interface InsuranceSecurityRepository extends JpaPagingAndSortingRepository<InsuranceSecurity, Long> {

}
