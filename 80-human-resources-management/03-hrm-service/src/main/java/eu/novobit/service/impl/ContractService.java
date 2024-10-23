package eu.novobit.service.impl;

import eu.novobit.annotation.CodeGenKms;
import eu.novobit.annotation.CodeGenLocal;
import eu.novobit.annotation.LnkFileService;
import eu.novobit.annotation.SrvRepo;
import eu.novobit.com.rest.service.AbstractCrudFileService;
import eu.novobit.config.AppProperties;
import eu.novobit.exception.handler.LeaveSummaryNotFoundException;
import eu.novobit.model.AppNextCode;
import eu.novobit.model.Contract;
import eu.novobit.model.Employee;
import eu.novobit.model.LeaveSummary;
import eu.novobit.model.schema.SchemaColumnConstantName;
import eu.novobit.remote.dms.DmsLinkedFileService;
import eu.novobit.remote.kms.KmsIncrementalKeyService;
import eu.novobit.repository.ContractRepository;
import eu.novobit.repository.EmployeeRepository;
import eu.novobit.repository.LeaveSummaryRepository;
import eu.novobit.service.IContractService;
import eu.novobit.service.ILeaveSummaryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Optional;

/**
 * The type Contract service.
 */
@Slf4j
@Service
@Transactional
@CodeGenLocal(value = NextCodeService.class)
@LnkFileService(DmsLinkedFileService.class)
@CodeGenKms(value = KmsIncrementalKeyService.class)
@SrvRepo(value = ContractRepository.class)
public class ContractService extends AbstractCrudFileService<Contract, ContractRepository> implements IContractService {

    private final AppProperties appProperties;
    @Autowired
    private ILeaveSummaryService leaveSummaryService;
    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private LeaveSummaryRepository leaveSummaryRepository;

    /**
     * Instantiates a new Contract service.
     *
     * @param appProperties the app properties
     */
    public ContractService(AppProperties appProperties) {
        this.appProperties = appProperties;
    }

    @Override
    public Contract afterUpdate(Contract contract) {
        Optional<Employee> employee = employeeRepository.findById(contract.getEmployee());
        Optional<LeaveSummary> leaveSummary = leaveSummaryRepository.findByCodeEmployeeAndYear(employee.get().getCode(), String.valueOf(LocalDate.now().getYear()));
        if (leaveSummary.isPresent()) {
            LeaveSummary leaveSummaryUpdated = leaveSummary.get();
            if (contract.getHolidayInformation() != null) {
                leaveSummaryUpdated.setLeaveCount(contract.getHolidayInformation().getLegalLeaveCount());
                leaveSummaryUpdated.setRecoveryLeaveCount(contract.getHolidayInformation().getRecoveryLeaveCount());
            }

            leaveSummaryService.saveOrUpdate(leaveSummaryUpdated);
        } else {
            throw new LeaveSummaryNotFoundException("with employee Code: " + employee.get().getCode());
        }
        return super.afterCreate(contract);
    }

    @Override
    protected String getUploadDirectory() {
        return this.appProperties.getUploadDirectory();
    }

    @Override
    public AppNextCode initCodeGenerator() {
        return AppNextCode.builder()
                .domain("default")
                .entity(Contract.class.getSimpleName())
                .attribute(SchemaColumnConstantName.C_CODE)
                .prefix("RCTR")
                .valueLength(6L)
                .value(1L)
                .increment(1)
                .build();
    }

    @Override
    public Contract updateContractStatus(Long id, Boolean isLocked) {

        repository().updateContractStatus(isLocked, id);
        return repository().findById(id).orElse(null);

    }
}
