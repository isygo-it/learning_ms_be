package eu.novobit.service.impl;

import eu.novobit.annotation.CodeGenKms;
import eu.novobit.annotation.CodeGenLocal;
import eu.novobit.annotation.LnkFileService;
import eu.novobit.annotation.SrvRepo;
import eu.novobit.async.kafka.KafkaRegisterAccountProducer;
import eu.novobit.com.rest.service.AbstractCrudFileImageService;
import eu.novobit.com.rest.service.AbstractCrudImageService;
import eu.novobit.config.AppProperties;
import eu.novobit.constants.DomainConstants;
import eu.novobit.dto.LinkedFileDto;
import eu.novobit.dto.LinkedFileResponseDto;
import eu.novobit.dto.RequestContextDto;
import eu.novobit.dto.data.EmployeeGlobalStatDto;
import eu.novobit.dto.data.EmployeeStatDto;
import eu.novobit.dto.request.RegisterNewAccountDto;
import eu.novobit.enumerations.IEnumAccountOrigin;
import eu.novobit.enumerations.IEnumBinaryStatus;
import eu.novobit.enumerations.IEnumSharedStatType;
import eu.novobit.exception.handler.EmployeeNotFoundException;
import eu.novobit.model.*;
import eu.novobit.model.schema.SchemaColumnConstantName;
import eu.novobit.remote.dms.DmsLinkedFileService;
import eu.novobit.remote.ims.ImAccountService;
import eu.novobit.remote.kms.KmsIncrementalKeyService;
import eu.novobit.repository.ContractRepository;
import eu.novobit.repository.EmployeeLinkedFileRepository;
import eu.novobit.repository.EmployeeRepository;
import eu.novobit.service.IEmployeeService;
import eu.novobit.service.ILeaveSummaryService;
import jakarta.ws.rs.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDate;
import java.time.Period;
import java.util.*;

/**
 * The type Employee service.
 */
@Slf4j
@Service
@Transactional
@CodeGenLocal(value = NextCodeService.class)
@CodeGenKms(value = KmsIncrementalKeyService.class)
@SrvRepo(value = EmployeeRepository.class)
@LnkFileService(DmsLinkedFileService.class)
public class EmployeeService extends AbstractCrudImageService<Employee, EmployeeRepository> implements IEmployeeService {

    private final AppProperties appProperties;

    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private ContractRepository contractRepository;
    @Autowired
    private ILeaveSummaryService leaveSummaryService;
    @Autowired
    private KafkaRegisterAccountProducer kafkaRegisterAccountProducer;
    @Autowired
    private DmsLinkedFileService dmsLinkedFileService;
    @Autowired
    private EmployeeLinkedFileRepository employeeLinkedFileRepository;
    @Autowired
    private ImAccountService imAccountService;

    /**
     * Instantiates a new Employee service.
     *
     * @param appProperties the app properties
     */
    public EmployeeService(AppProperties appProperties) {
        this.appProperties = appProperties;
    }

    @Override
    public Employee beforeUpdate(Employee employee) {
        employee.setContracts(repository().findById(employee.getId()).get().getContracts());
        return super.beforeUpdate(employee);
    }

    @Override
    public Employee afterCreate(Employee employee) {
        leaveSummaryService.create(LeaveSummary.builder().codeEmployee(employee.getCode())
                .year(String.valueOf(LocalDate.now().getYear()))
                .nameEmployee(employee.getFirstName() + ' ' + employee.getLastName())
                .build());

        try {
            if (employee.getIsLinkedToUser()) {
                kafkaRegisterAccountProducer.sendMessage(RegisterNewAccountDto.builder()
                        .origin(new StringBuilder(IEnumAccountOrigin.Types.EMPLOYEE.name()).append("-").append(employee.getCode()).toString())
                        .domain(employee.getDomain())
                        .email(employee.getEmail())
                        .firstName(employee.getFirstName())
                        .lastName(employee.getLastName())
                        .phoneNumber(employee.getPhone())
                        .build());
            }
        } catch (IOException e) {
            log.error("<Error>: api exception : {} ", e);
        }
        return super.afterCreate(employee);
    }

    @Override
    public void afterDelete(Long id) {
        Optional<Employee> optionalEmployee = employeeRepository.findById(id);
        if (optionalEmployee.isPresent()) {
            Employee employee = optionalEmployee.get();
            List<Contract> contracts = employee.getContracts();
            for (Contract contract : contracts) {
                contractRepository.delete(contract);
            }
        }
        super.afterDelete(id);
    }

    @Override
    public AppNextCode initCodeGenerator() {
        return AppNextCode.builder()
                .domain("default")
                .entity(Employee.class.getSimpleName())
                .attribute(SchemaColumnConstantName.C_CODE)
                .prefix("REMP")
                .valueLength(6L)
                .value(1L)
                .increment(1)
                .build();
    }

    @Override
    protected String getUploadDirectory() {
        return appProperties.getUploadDirectory();
    }

    @Override
    public Employee findEmployeeByCode(String code) {
        Optional<Employee> optionalEmployee = employeeRepository.findByCodeIgnoreCase(code);
        if (optionalEmployee.isPresent()) {
            return optionalEmployee.get();
        } else {
            throw new NotFoundException("Employee not found with CODE: " + code);
        }
    }

    @Override
    public List<Employee> findEmployeeByDomain(String domain) {
        List<Employee> employees = employeeRepository.findByDomainIgnoreCaseIn(Arrays.asList(DomainConstants.DEFAULT_DOMAIN_NAME, domain));
        if (!employees.isEmpty()) {
            return employees;
        } else {
            throw new NotFoundException("Employee not found with domain: " + domain);
        }
    }

    @Override
    public Employee updateEmployeeStatus(Long id, IEnumBinaryStatus.Types newStatus) {
        Optional<Employee> optionalEmployee = employeeRepository.findById(id);
        //TODO pour assurer notifications
        if (optionalEmployee.isPresent()) {
            optionalEmployee.get().setEmployeeStatus(newStatus);
        } else {
            throw new NotFoundException("Employee not found with CODE: " + id);
        }
        return null;
    }

    @Override
    public List<EmployeeLinkedFile> uploadAdditionalFile(Long id, MultipartFile[] files) throws IOException {
        Employee employee = findById(id);

        // int crc16 = CRC16.calculate(buffer);
        //int crc32 = CRC32.calculate(buffer);
        if (employee != null) {
            for (MultipartFile file : files) {
                ResponseEntity<LinkedFileResponseDto> result = dmsLinkedFileService.upload(//RequestContextDto.builder().build(),
                        LinkedFileDto.builder()
                                .domain(employee.getDomain())
                                .path(File.separator + "employee" + File.separator + "additional")
                                .categoryNames(Arrays.asList("Employee"))
                                .file(file)
                                .build());
                EmployeeLinkedFile employeeLinkedFile = EmployeeLinkedFile.builder()
                        .code(result.getBody().getCode())
                        .originalFileName(file.getOriginalFilename())
                        .extension(FilenameUtils.getExtension(file.getOriginalFilename()))
                        .crc16(254147)
                        .crc32(365214)
                        .size(file.getSize())
                        .path("/employee/additional")
                        .mimetype(file.getContentType())
                        .version(1L)
                        .build();
                if (CollectionUtils.isEmpty(employee.getAdditionalFiles())) {
                    employee.setAdditionalFiles(new ArrayList<>());
                }
                employee.getAdditionalFiles().add(employeeLinkedFile);
                employee = this.update(employee);
            }
            return employee.getAdditionalFiles();
        } else {
            throw new EmployeeNotFoundException("with id: " + id);
        }
    }

    @Override
    public boolean deleteAdditionalFile(Long id, String originalFileName) throws IOException {
        Employee employee = findById(id);
        if (employee != null) {

            EmployeeLinkedFile file = employee.getAdditionalFiles().stream()
                    .filter((EmployeeLinkedFile item) -> item.getOriginalFileName().equals(originalFileName)).findAny()
                    .orElse(null);
            if (file != null) {
                employee.getAdditionalFiles().removeIf(elm -> elm.getOriginalFileName().equals(originalFileName));
                dmsLinkedFileService.deleteFile(RequestContextDto.builder().build(), employee.getDomain(), file.getCode());
                this.update(employee);
                employeeLinkedFileRepository.deleteById(file.getId());
                return true;
            } else {
                throw new FileNotFoundException("with original File name: " + originalFileName);
            }
        } else {
            throw new EmployeeNotFoundException("with id: " + id);
        }
    }

    @Override
    public EmployeeStatDto getObjectStatistics(String code, RequestContextDto requestContext) {
        return EmployeeStatDto.builder().contractCount(getTotalContractByEmployee(code))
                .activeContractEndDate(getLastContractEndDate(code))
                .activeContractAnniversaryDate(getActiveContractAnniversaryDate(code))
                .nextBonnusDate(getLastDueDate(code))
                .build();
    }

    private Integer getTotalContractByEmployee(String code) {
        Optional<Employee> optionalEmployee = employeeRepository.findByCodeIgnoreCase(code);
        if (optionalEmployee.isPresent()) {
            Employee employee = optionalEmployee.get();
            List<Contract> contracts = employee.getContracts();
            return contracts.size();
        } else {
            return 0;
        }
    }

    private LocalDate getLastContractEndDate(String code) {
        Optional<Employee> optionalEmployee = employeeRepository.findByCodeIgnoreCase(code);
        if (optionalEmployee.isPresent()) {
            Employee employee = optionalEmployee.get();
            if (!CollectionUtils.isEmpty(employee.getContracts())) {
                return employee.getContracts().stream()
                        .filter(contract -> contract.getEndDate() != null)
                        .map(Contract::getEndDate)
                        .max(LocalDate::compareTo)
                        .orElse(null);
            }
        }
        return null;
    }

    private LocalDate getActiveContractAnniversaryDate(String code) {
        Optional<Employee> optionalEmployee = employeeRepository.findByCodeIgnoreCase(code);
        if (optionalEmployee.isPresent()) {
            Employee employee = optionalEmployee.get();
            if (!CollectionUtils.isEmpty(employee.getContracts())) {
                Optional<Contract> activeContract = employee.getContracts().stream()
                        .filter(contract -> contract.getEndDate() == null || contract.getEndDate().isAfter(LocalDate.now()))
                        .findFirst();

                if (activeContract.isPresent() && activeContract.get().getStartDate() != null) {
                    LocalDate startLocalDate = activeContract.get().getStartDate();
                    LocalDate currentLocalDate = LocalDate.now();

                    int yearsBetween = Period.between(startLocalDate, currentLocalDate).getYears();
                    LocalDate nextAnniversaryDate = startLocalDate.plusYears(yearsBetween + 1);

                    return nextAnniversaryDate;
                }
                else{
                    return null;
                }
            }
        }
        return null;
    }

    public LocalDate getLastDueDate(String code) {
        Optional<Employee> employeeOpt = employeeRepository.findByCodeIgnoreCase(code);
        if (!employeeOpt.isPresent()) {
            return null;
        }
        Employee employee = employeeOpt.get();

        // Fetch contracts for the employee
        List<Contract> contracts = employee.getContracts();

        // Get the last active contract
        Optional<Contract> lastActiveContractOpt = contracts.stream()
                .filter(contract -> !contract.getCheckCancel()) // Filter out canceled contracts
                .max(Comparator.comparing(Contract::getEndDate)); // Get the contract with the latest end date

        if (!lastActiveContractOpt.isPresent()) {
            return null;
        }
        Contract lastActiveContract = lastActiveContractOpt.get();

        SalaryInformation salaryInformation = lastActiveContract.getSalaryInformation();
        if (salaryInformation == null) {
            return null;
        }
        List<Prime> primes = salaryInformation.getPrimes();
        return primes.stream()
                .flatMap(prime -> prime.getDueDates().stream())
                .map(DueDate::getPaymentDate)
                .max(Comparator.naturalOrder())
                .orElse(null);
    }

    @Override
    public EmployeeGlobalStatDto getGlobalStatistics(IEnumSharedStatType.Types enumStatType, RequestContextDto requestContext) {
        EmployeeGlobalStatDto.EmployeeGlobalStatDtoBuilder builder = EmployeeGlobalStatDto.builder();
        switch (enumStatType) {
            case TOTAL_COUNT:
                builder.totalCount(getTotalEmployeeeCount(requestContext));
                break;
            case ACTIVE_COUNT:
                builder.activeCount(getActiveEmployee(requestContext));
                break;
            case CONFIRMED_COUNT:
                builder.confirmedCount(getConfirmedEmployee(requestContext));
                break;

            default:
                throw new IllegalArgumentException("Unknown stat type: " + enumStatType);
        }

        return builder.build();
    }

    private Integer getTotalEmployeeeCount(RequestContextDto requestContextDto) {
        Long totalEmployeeCount = repository().countByDomainAndCancelDateNull(requestContextDto.getSenderDomain());
        return totalEmployeeCount.intValue();
    }

    private Integer getConfirmedEmployee(RequestContextDto requestContextDto) {
        ResponseEntity<Integer> responseEntity = imAccountService.getConfirmedAccountNumberByEmployee(requestContextDto);
        return responseEntity.getBody();
    }

    private Integer getActiveEmployee(RequestContextDto requestContextDto) {
        Long totalActiveCount = repository().countByDomainAndCancelDateNullAndEmployeeStatus(requestContextDto.getSenderDomain(), IEnumBinaryStatus.Types.ENABLED);
        return totalActiveCount.intValue();
    }

}
