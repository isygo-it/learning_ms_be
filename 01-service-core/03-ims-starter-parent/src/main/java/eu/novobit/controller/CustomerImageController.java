package eu.novobit.controller;

import eu.novobit.annotation.CtrlDef;
import eu.novobit.com.rest.controller.MappedImageController;
import eu.novobit.dto.data.CustomerDto;
import eu.novobit.exception.handler.ImsExceptionHandler;
import eu.novobit.mapper.CustomerMapper;
import eu.novobit.model.Customer;
import eu.novobit.service.impl.CustomerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * The type Customer image controller.
 */
@Slf4j
@Validated
@RestController
@RequestMapping(path = "/api/private/customer")
@CtrlDef(handler = ImsExceptionHandler.class, mapper = CustomerMapper.class, service = CustomerService.class)
public class CustomerImageController extends MappedImageController<Customer, CustomerDto, CustomerDto, CustomerService> {

}
