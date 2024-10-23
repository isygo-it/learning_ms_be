package eu.novobit.controller;

import eu.novobit.annotation.CtrlDef;
import eu.novobit.api.EmployeeControllerApi;
import eu.novobit.com.rest.controller.MappedCrudController;
import eu.novobit.com.rest.controller.ResponseFactory;
import eu.novobit.dto.RequestContextDto;
import eu.novobit.dto.data.EmployeeDto;
import eu.novobit.dto.data.MinEmployeeDto;
import eu.novobit.enumerations.IEnumBinaryStatus;
import eu.novobit.exception.handler.HrmExceptionHandler;
import eu.novobit.mapper.EmployeeMapper;
import eu.novobit.model.Employee;
import eu.novobit.service.impl.EmployeeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * The type Employee controller.
 */
@Slf4j
@Validated
@RestController
@CtrlDef(handler = HrmExceptionHandler.class, mapper = EmployeeMapper.class, service = EmployeeService.class)
@RequestMapping(value = "/api/private/employee")
public class EmployeeController extends MappedCrudController<Employee, MinEmployeeDto, EmployeeDto, EmployeeService>
        implements EmployeeControllerApi {

    @Override
    public ResponseEntity<EmployeeDto> getEmployeeByCode(RequestContextDto requestContext,
                                                         String code) {
        try {
            Employee employee = crudService().findEmployeeByCode(code);
            if (employee != null) {
                return ResponseEntity.ok(mapper().entityToDto(employee));
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            log.error(ERROR_API_EXCEPTION, e);
            return getBackExceptionResponse(e);
        }
    }

    @Override
    public ResponseEntity<List<EmployeeDto>> getEmployeeByDomain(RequestContextDto requestContext, String domain) {
        try {
            List<Employee> employees = crudService().findEmployeeByDomain(domain);
            List<EmployeeDto> employeeDtos = mapper().listEntityToDto(employees);
            return ResponseEntity.ok(employeeDtos);
        } catch (Exception e) {
            log.error(ERROR_API_EXCEPTION, e);
            return getBackExceptionResponse(e);
        }
    }


    @Override
    public ResponseEntity<EmployeeDto> updateEmployeeStatus(RequestContextDto requestContext,
                                                            Long id,
                                                            IEnumBinaryStatus.Types newStatus) {
        log.info("update employee status");
        try {
            return ResponseFactory.ResponseOk(mapper().entityToDto(crudService().updateEmployeeStatus(id, newStatus)));
        } catch (Throwable e) {
            log.error(ERROR_API_EXCEPTION, e);
            return getBackExceptionResponse(e);
        }
    }
}
