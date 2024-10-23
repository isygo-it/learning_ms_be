package eu.novobit.service;

import eu.novobit.com.rest.service.ICrudImageService;
import eu.novobit.com.rest.service.ICrudServiceMethods;
import eu.novobit.dto.RequestContextDto;
import eu.novobit.dto.data.EmployeeGlobalStatDto;
import eu.novobit.dto.data.EmployeeStatDto;
import eu.novobit.enumerations.IEnumBinaryStatus;
import eu.novobit.enumerations.IEnumSharedStatType;
import eu.novobit.model.Employee;
import eu.novobit.model.EmployeeLinkedFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

/**
 * The interface Employee service.
 */
public interface IEmployeeService extends ICrudServiceMethods<Employee>, ICrudImageService<Employee> {
    /**
     * Find employee by code employee.
     *
     * @param code the code
     * @return the employee
     */
    Employee findEmployeeByCode(String code);

    /**
     * Find employee by domain.
     *
     * @param domain the domain
     * @return the employee
     */
    List<Employee> findEmployeeByDomain(String domain);

    /**
     * Update employee status employee.
     *
     * @param id        the id
     * @param newStatus the new status
     * @return the employee
     */
    Employee updateEmployeeStatus(Long id, IEnumBinaryStatus.Types newStatus);

    /**
     * Upload additional file list.
     *
     * @param id    the id
     * @param files the files
     * @return the list
     * @throws IOException the io exception
     */
    List<EmployeeLinkedFile> uploadAdditionalFile(Long id, MultipartFile[] files) throws IOException;

    /**
     * Delete additional file boolean.
     *
     * @param id               the id
     * @param originalFileName the original file name
     * @return the boolean
     * @throws IOException the io exception
     */
    boolean deleteAdditionalFile(Long id, String originalFileName) throws IOException;

    EmployeeStatDto getObjectStatistics(String code, RequestContextDto requestContext);

    EmployeeGlobalStatDto getGlobalStatistics(IEnumSharedStatType.Types enumStatType, RequestContextDto requestContext);


}
