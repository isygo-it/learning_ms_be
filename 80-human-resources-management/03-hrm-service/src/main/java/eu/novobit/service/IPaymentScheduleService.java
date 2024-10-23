package eu.novobit.service;

import eu.novobit.com.rest.service.ICrudServiceMethods;
import eu.novobit.model.PaymentSchedule;

import java.util.List;

/**
 * The interface Payment Schedule service.
 */
public interface IPaymentScheduleService extends ICrudServiceMethods<PaymentSchedule> {
    /**
     * Calculate payment schedule list.
     *
     * @param contractId the contract id
     * @return the list
     */
    List<PaymentSchedule> calculatePaymentSchedule(Long contractId);
}
