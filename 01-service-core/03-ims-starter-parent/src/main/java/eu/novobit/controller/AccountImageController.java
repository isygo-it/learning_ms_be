package eu.novobit.controller;

import eu.novobit.annotation.CtrlDef;
import eu.novobit.com.rest.controller.MappedImageController;
import eu.novobit.dto.data.AccountDto;
import eu.novobit.dto.request.UpdateAccountRequestDto;
import eu.novobit.exception.handler.ImsExceptionHandler;
import eu.novobit.mapper.AccountMapper;
import eu.novobit.model.Account;
import eu.novobit.remote.kms.KmsPasswordService;
import eu.novobit.service.impl.AccountService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.security.auth.login.AccountException;

/**
 * The type Account image controller.
 */
@Slf4j
@Validated
@RestController
@CtrlDef(handler = ImsExceptionHandler.class, mapper = AccountMapper.class, service = AccountService.class)
@RequestMapping(path = "/api/private/account")
public class AccountImageController extends MappedImageController<Account, AccountDto, AccountDto, AccountService> {

    @Autowired
    private KmsPasswordService passwordService;

    @Override
    public AccountDto beforeUpdate(AccountDto account) throws Exception {
        ResponseEntity<Boolean> result = passwordService.updateAccount(//RequestContextDto.builder().build(),
                UpdateAccountRequestDto.builder()
                        .code(account.getCode())
                        .domain(account.getDomain())
                        .email(account.getEmail())
                        .fullName(account.getFullName())
                        .build());
        if (result.getStatusCode().is2xxSuccessful() && result.hasBody() && result.getBody()) {
            return super.beforeUpdate(account);
        }
        throw new AccountException("Update account issue in KMS");
    }
}
