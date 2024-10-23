package eu.novobit.async.camel.processor;

import eu.novobit.com.camel.processor.AbstractCamelProcessor;
import eu.novobit.com.camel.processor.AbstractStringProcessor;
import eu.novobit.dto.data.AssoAccountDto;
import eu.novobit.dto.request.RegisterNewAccountDto;
import eu.novobit.helper.JsonHelper;
import eu.novobit.model.Account;
import eu.novobit.model.AccountDetails;
import eu.novobit.model.RoleInfo;
import eu.novobit.service.IAccountService;
import eu.novobit.service.IAppParameterService;
import eu.novobit.service.IRoleInfoService;
import lombok.extern.slf4j.Slf4j;
import org.apache.camel.Exchange;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.Arrays;

/**
 * The type Register new account processor.
 */
@Slf4j
@Component
@Qualifier("registerNewAccountProcessor")
public class RegisterNewAccountProcessor extends AbstractStringProcessor {

    @Autowired
    private IAccountService accountService;
    @Autowired
    private IAppParameterService parameterService;
    @Autowired
    private IRoleInfoService roleInfoService;

    @Override
    public void performProcessor(Exchange exchange, String RegisterNewAccountMsg) throws Exception {
        RegisterNewAccountDto newAccount = JsonHelper.fromJson(RegisterNewAccountMsg, RegisterNewAccountDto.class);

        exchange.getIn().setHeader("email", newAccount.getEmail());
        exchange.getIn().setHeader("firstName", newAccount.getFirstName());
        exchange.getIn().setHeader("lastName", newAccount.getLastName());

        RoleInfo roleInfo = null;
        String[] splitOrigin = newAccount.getOrigin().split("-");
        String roleName = parameterService.getValueByDomainAndName(newAccount.getDomain(), splitOrigin[0] + "_ROLE", true, "");
        if (StringUtils.hasText(roleName)) {
            roleInfo = roleInfoService.findByName(roleName);
        } else {
            log.error("<Error>: No role parametrized for account origin : {} ", newAccount.getOrigin());
        }

        Account account = accountService.create(Account.builder()
                .origin(newAccount.getOrigin())
                .domain(newAccount.getDomain())
                .email(newAccount.getEmail())
                .phoneNumber(newAccount.getPhoneNumber())
                .roleInfo(roleInfo != null ? Arrays.asList(roleInfo) : null)
                .accountDetails(AccountDetails.builder()
                        .firstName(newAccount.getFirstName())
                        .lastName(newAccount.getLastName())
                        .build())
                .build());

        exchange.getIn().setBody(JsonHelper.toJson(AssoAccountDto.builder()
                .code(account.getCode())
                .origin(account.getOrigin())
                .build()));
        exchange.getIn().setHeader(AbstractCamelProcessor.ORIGIN, splitOrigin[0]);
        exchange.getIn().setHeader(AbstractCamelProcessor.RETURN_HEADER, true);
    }
}
