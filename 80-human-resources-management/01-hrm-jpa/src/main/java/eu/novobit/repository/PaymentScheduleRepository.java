package eu.novobit.repository;

import eu.novobit.model.PaymentSchedule;
import org.springframework.stereotype.Repository;


/**
 * The interface PaymentSchedule repository.
 */
@Repository
public interface PaymentScheduleRepository extends JpaPagingAndSortingRepository<PaymentSchedule, Long> {

}
