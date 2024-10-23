package eu.novobit.controller;

import eu.novobit.annotation.CtrlDef;
import eu.novobit.api.ContractControllerApi;
import eu.novobit.com.rest.controller.MappedCrudController;
import eu.novobit.com.rest.controller.ResponseFactory;
import eu.novobit.dto.RequestContextDto;
import eu.novobit.dto.data.ContractDto;
import eu.novobit.dto.data.MinContractDto;
import eu.novobit.exception.handler.HrmExceptionHandler;
import eu.novobit.mapper.ContractMapper;
import eu.novobit.model.Contract;
import eu.novobit.service.IContractService;
import eu.novobit.service.impl.ContractService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * The type Contract controller.
 */
@Slf4j
@Validated
@RestController
@CtrlDef(handler = HrmExceptionHandler.class, mapper = ContractMapper.class, service = ContractService.class)
@RequestMapping(value = "/api/private/contract")
public class ContractController extends MappedCrudController<Contract, MinContractDto, ContractDto, ContractService>
        implements ContractControllerApi {
    @Autowired
    private IContractService contractService;

    @Override
    public ResponseEntity<ContractDto> updateContractStatus(RequestContextDto requestContext,
                                                            Long id, Boolean isLocked) {
        log.info("update contrat status");
        try {
            return ResponseFactory.ResponseOk(mapper().entityToDto(contractService.updateContractStatus(id, isLocked)));
        } catch (Throwable e) {
            log.error(ERROR_API_EXCEPTION, e);
            return getBackExceptionResponse(e);
        }
    }
}
