package eu.novobit.repository;

import eu.novobit.enumerations.IEnumBinaryStatus;
import eu.novobit.model.Employee;
import org.springframework.stereotype.Repository;


/**
 * The interface Employee repository.
 */
@Repository
public interface EmployeeRepository extends JpaPagingAndSortingSASCodifiableRepository<Employee, Long> {

    Long countByDomainAndCancelDateNull(String domain);
    Long countByDomainAndCancelDateNullAndEmployeeStatus(String senderDomain, IEnumBinaryStatus.Types employeeStatus);
}
