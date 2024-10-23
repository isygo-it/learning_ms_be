package eu.novobit.controller;

import eu.novobit.annotation.CtrlDef;
import eu.novobit.com.rest.controller.MappedCrudController;
import eu.novobit.dto.data.AccountDetailsDto;
import eu.novobit.exception.handler.ImsExceptionHandler;
import eu.novobit.mapper.AccountDetailsMapper;
import eu.novobit.model.AccountDetails;
import eu.novobit.service.impl.AccountDetailsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * The type Account details controller.
 */
@Slf4j
@Validated
@RestController
@RequestMapping(path = "/api/private/account/details")
@CtrlDef(handler = ImsExceptionHandler.class, mapper = AccountDetailsMapper.class, service = AccountDetailsService.class)
public class AccountDetailsController extends MappedCrudController<AccountDetails, AccountDetailsDto, AccountDetailsDto, AccountDetailsService> {
}
