package eu.novobit.controller;

import eu.novobit.annotation.CtrlDef;
import eu.novobit.com.rest.controller.MappedFileController;
import eu.novobit.dto.data.ContractDto;
import eu.novobit.dto.data.MinContractDto;
import eu.novobit.exception.handler.HrmExceptionHandler;
import eu.novobit.mapper.ContractMapper;
import eu.novobit.model.Contract;
import eu.novobit.service.impl.ContractService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * The type Contract file controller.
 */
@Slf4j
@Validated
@RestController
@CtrlDef(handler = HrmExceptionHandler.class, mapper = ContractMapper.class, service = ContractService.class)
@RequestMapping(value = "/api/private/contract")
public class ContractFileController extends MappedFileController<Contract, MinContractDto, ContractDto, ContractService> {

}
