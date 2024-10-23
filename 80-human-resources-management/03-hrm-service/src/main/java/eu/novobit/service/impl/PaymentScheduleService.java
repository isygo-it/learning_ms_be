package eu.novobit.service.impl;

import eu.novobit.annotation.CodeGenKms;
import eu.novobit.annotation.CodeGenLocal;
import eu.novobit.annotation.SrvRepo;
import eu.novobit.com.rest.service.AbstractCrudService;
import eu.novobit.enumerations.IEnumContractType;
import eu.novobit.model.Contract;
import eu.novobit.model.PaymentSchedule;
import eu.novobit.model.SalaryInformation;
import eu.novobit.remote.kms.KmsIncrementalKeyService;
import eu.novobit.repository.ContractRepository;
import eu.novobit.repository.PaymentScheduleRepository;
import eu.novobit.service.IPaymentScheduleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


/**
 * The type PaymentSchedule service.
 */
@Slf4j
@Service
@Transactional
@CodeGenLocal(value = NextCodeService.class)
@CodeGenKms(value = KmsIncrementalKeyService.class)
@SrvRepo(value = PaymentScheduleRepository.class)
public class PaymentScheduleService extends AbstractCrudService<PaymentSchedule, PaymentScheduleRepository> implements IPaymentScheduleService {
    @Autowired
    private ContractRepository contractRepository;

    @Override
    @Transactional
    public List<PaymentSchedule> calculatePaymentSchedule(Long contractId) {
        Optional<Contract> optionalContract = contractRepository.findById(contractId);
        List<PaymentSchedule> paymentSchedules = new ArrayList<>();

        if (optionalContract.isPresent()) {
            Contract contract = optionalContract.get();
            SalaryInformation salaryInformation = contract.getSalaryInformation();

            if (salaryInformation != null && salaryInformation.getPaymentSchedules().isEmpty()) {
                LocalDate startDate = contract.getStartDate();
                LocalDate endDate = contract.getEndDate();
                int frequency = salaryInformation.getFrequency();

                if (startDate != null && frequency > 0) {
                    if (endDate == null && contract.getContract() == IEnumContractType.Types.CDI) {

                        LocalDate currentDate = startDate;
                        for (int i = 0; i < frequency; i++) {
                            LocalDate dueDate = currentDate.with(TemporalAdjusters.lastDayOfMonth());
                            PaymentSchedule paymentSchedule = new PaymentSchedule();
                            paymentSchedule.setDueDate(dueDate);
                            paymentSchedule.setPaymentGrossAmount(salaryInformation.getGrossSalary() / frequency);
                            paymentSchedules.add(paymentSchedule);
                            currentDate = currentDate.plusMonths(1);
                        }
                    } else {
                        long durationInDays = endDate.toEpochDay() - startDate.toEpochDay();
                        double paymentAmount = salaryInformation.getGrossSalary() / frequency;
                        long durationBetweenPayments = durationInDays / frequency;
                        for (int i = 0; i < frequency; i++) {
                            LocalDate dueDate = startDate.plusDays(durationBetweenPayments * i);
                            PaymentSchedule paymentSchedule = new PaymentSchedule();  /*builder*/
                            paymentSchedule.setDueDate(dueDate);
                            paymentSchedule.setPaymentGrossAmount(paymentAmount);
                            paymentSchedules.add(paymentSchedule);
                        }
                    }
                }
                salaryInformation.setPaymentSchedules(paymentSchedules);
                repository().saveAll(paymentSchedules);
            }

            if (salaryInformation != null && !salaryInformation.getPaymentSchedules().isEmpty()) {
                return salaryInformation.getPaymentSchedules();
            }
        }
        return paymentSchedules;
    }
}