package eu.novobit.controller;

import eu.novobit.annotation.CtrlDef;
import eu.novobit.com.rest.controller.MappedImageController;
import eu.novobit.dto.data.EmployeeDto;
import eu.novobit.exception.handler.HrmExceptionHandler;
import eu.novobit.mapper.EmployeeMapper;
import eu.novobit.model.Employee;
import eu.novobit.service.impl.EmployeeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * The type Employee image controller.
 */
@Slf4j
@Validated
@RestController
@CtrlDef(handler = HrmExceptionHandler.class, mapper = EmployeeMapper.class, service = EmployeeService.class)
@RequestMapping(value = "/api/private/employee")
public class EmployeeImageController extends MappedImageController<Employee, EmployeeDto, EmployeeDto, EmployeeService> {

}
