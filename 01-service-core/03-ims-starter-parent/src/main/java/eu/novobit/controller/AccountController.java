package eu.novobit.controller;

import eu.novobit.annotation.CtrlDef;
import eu.novobit.api.AccountControllerApi;
import eu.novobit.api.StatisticControllerApi;
import eu.novobit.com.rest.controller.MappedCrudController;
import eu.novobit.com.rest.controller.ResponseFactory;
import eu.novobit.dto.RequestContextDto;
import eu.novobit.dto.ResetPwdViaTokenRequestDto;
import eu.novobit.dto.data.AccountDto;
import eu.novobit.dto.data.MinAccountDto;
import eu.novobit.dto.request.GeneratePwdRequestDto;
import eu.novobit.dto.request.UpdateAccountRequestDto;
import eu.novobit.dto.response.UserDataResponseDto;
import eu.novobit.enumerations.IEnumAuth;
import eu.novobit.enumerations.IEnumBinaryStatus;
import eu.novobit.enumerations.IEnumLanguage;
import eu.novobit.exception.handler.ImsExceptionHandler;
import eu.novobit.jwt.JwtService;
import eu.novobit.mapper.AccountMapper;
import eu.novobit.mapper.MinAccountMapper;
import eu.novobit.mapper.ThemeMapper;
import eu.novobit.model.Account;
import eu.novobit.model.Domain;
import eu.novobit.remote.kms.KmsPasswordService;
import eu.novobit.service.IAccountService;
import eu.novobit.service.IDomainService;
import eu.novobit.service.IThemeService;
import eu.novobit.service.impl.AccountService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * The type Account controller.
 */
@Slf4j
@Validated
@RestController
@CtrlDef(handler = ImsExceptionHandler.class, mapper = AccountMapper.class, service = AccountService.class)
@RequestMapping(path = "/api/private/account")
public class AccountController extends MappedCrudController<Account, AccountDto, AccountDto, AccountService>
        implements AccountControllerApi, StatisticControllerApi {

    @Autowired
    private KmsPasswordService passwordService;
    @Autowired
    private IAccountService accountService;
    @Autowired
    private IDomainService domainService;
    @Autowired
    private JwtService jwtService;
    @Autowired
    private MinAccountMapper minAccountMapper;
    @Autowired
    private IThemeService themeService;
    @Autowired
    private ThemeMapper themeMapper;

    @Override
    public AccountDto beforeUpdate(Long id, AccountDto account) {
        ResponseEntity<Boolean> result = passwordService.updateAccount(//RequestContextDto.builder().build(),
                UpdateAccountRequestDto.builder()
                        .code(account.getCode())
                        .domain(account.getDomain())
                        .email(account.getEmail())
                        .fullName(account.getFullName())
                        .build());
        if (result.getStatusCode().is2xxSuccessful() && result.hasBody() && result.getBody()) {
            return super.beforeUpdate(id, account);
        }
        return super.beforeUpdate(id, account);
    }

    @Override
    public Account afterCreate(Account account) {
        ResponseEntity<Boolean> result = passwordService.generate(//RequestContextDto.builder().build(),
                IEnumAuth.Types.PWD,
                GeneratePwdRequestDto.builder()
                        .domain(account.getDomain())
                        .domainUrl(domainService.findByName(account.getDomain()).getUrl())
                        .email(account.getEmail())
                        .userName(account.getCode())
                        .fullName(account.getFullName())
                        .build());
        if (result.getStatusCode().is2xxSuccessful() && result.hasBody() && result.getBody()) {
            return super.afterCreate(account);
        }
        return super.afterCreate(account);
    }

    @Override
    public ResponseEntity<List<String>> getEmailsByDomain(RequestContextDto requestContext) {
        log.info("get accounts email by domain");
        try {
            return ResponseFactory.ResponseOk(accountService.findEmailsByDomain(requestContext.getSenderDomain()));
        } catch (Throwable e) {
            log.error(ERROR_API_EXCEPTION, e);
            return getBackExceptionResponse(e);
        }
    }

    @Override
    //TODO use new Controller struct wit MinDto/FullDto
    public ResponseEntity<List<MinAccountDto>> getAccounts(RequestContextDto requestContext) {
        log.info("get accounts mini data");
        try {
            return ResponseFactory.ResponseOk(accountService.getMinInfoByDomain(requestContext.getSenderDomain()));
        } catch (Throwable e) {
            log.error(ERROR_API_EXCEPTION, e);
            return getBackExceptionResponse(e);
        }
    }

    @Override
    public ResponseEntity<AccountDto> updateAccountAdminStatus(RequestContextDto requestContext,
                                                               Long id,
                                                               IEnumBinaryStatus.Types newStatus) {
        log.info("update account admin status");
        try {
            return ResponseFactory.ResponseOk(mapper().entityToDto(accountService.updateAccountAdminStatus(id, newStatus)));
        } catch (Throwable e) {
            log.error(ERROR_API_EXCEPTION, e);
            return getBackExceptionResponse(e);
        }
    }

    @Override
    public ResponseEntity<AccountDto> updateAccountIsAdmin(RequestContextDto requestContext,
                                                           Long id,
                                                           boolean newStatus) {
        log.info("update account isAdmin");
        try {
            return ResponseFactory.ResponseOk(mapper().entityToDto(accountService.updateAccountIsAdmin(id, newStatus)));
        } catch (Throwable e) {
            log.error(ERROR_API_EXCEPTION, e);
            return getBackExceptionResponse(e);
        }
    }

    @Override
    public ResponseEntity<AccountDto> updateLanguage(RequestContextDto requestContext,
                                                     Long id,
                                                     IEnumLanguage.Types language) {
        log.info("update account language");
        try {
            return ResponseFactory.ResponseOk(mapper().entityToDto(accountService.updateLanguage(id, language)));
        } catch (Throwable e) {
            log.error(ERROR_API_EXCEPTION, e);
            return getBackExceptionResponse(e);
        }
    }

    //TODO Refactor this API to return AuthResponseDto/SystemInfo
    @Override
    public ResponseEntity<UserDataResponseDto> connectedUser(RequestContextDto requestContext) {
        try {
            Account account = accountService.findByDomainAndUserName(requestContext.getSenderDomain(), requestContext.getSenderUser());
            Domain domain = domainService.findByName(requestContext.getSenderDomain());
            //ThemeDto theme = themeMapper.entityToDto(themeService.findThemeByAccountCodeAndDomainCode(account.getCode(), domain.getCode()));
            UserDataResponseDto userDataResponseDto = UserDataResponseDto.builder()
                    .id(account.getId())
                    .userName(account.getCode())
                    .firstName(account.getAccountDetails().getFirstName())
                    .lastName(account.getAccountDetails().getLastName())
                    //.applications(accountService.buildAllowedTools(account, authenticate.getAccessToken()))
                    .email(account.getEmail())
                    .domainId(domain.getId())
                    .domainImagePath(domain.getImagePath())
                    .language(account.getLanguage())
                    .role(account.getFunctionRole())
                    .build();

            /*
            return ResponseFactory.ResponseOk(AuthResponseDto.builder()
                    .tokenType(IEnumWebToken.Types.Bearer)
                    .accessToken(authenticate.getAccessToken())
                    .refreshToken(authenticate.getRefreshToken())
                    .userDataResponseDto(userDataResponseDto)
                    .theme(theme)
                    .systemInfo(SystemInfoDto
                            .builder()
                            .name(appProperties.getApplicationName())
                            .version(appProperties.getApplicationVersion())
                            .build())
                    .build());
             */

            return ResponseFactory.ResponseOk(userDataResponseDto);
        } catch (Throwable e) {
            log.error(ERROR_API_EXCEPTION, e);
            return getBackExceptionResponse(e);
        }
    }

    @Override
    public ResponseEntity<AccountDto> connectedUserFullData(RequestContextDto requestContext) {
        try {
            return ResponseFactory.ResponseOk(mapper().entityToDto(accountService.findByDomainAndUserName(requestContext.getSenderDomain(),
                    requestContext.getSenderUser())));
        } catch (Throwable e) {
            log.error(ERROR_API_EXCEPTION, e);
            return getBackExceptionResponse(e);
        }
    }

    @Override
    public ResponseEntity<AccountDto> updateConnectedUserProfile(RequestContextDto requestContext,
                                                                 AccountDto accountDto) {
        try {
            Account account = accountService.findByDomainAndUserName(requestContext.getSenderDomain(), requestContext.getSenderUser());
            accountDto.setId(account.getId());
            return ResponseFactory.ResponseOk(mapper().entityToDto(accountService.update(mapper().dtoToEntity(accountDto))));
        } catch (Throwable e) {
            log.error(ERROR_API_EXCEPTION, e);
            return getBackExceptionResponse(e);
        }
    }

    @Override
    public ResponseEntity<AccountDto> connectedUserUpdateAccount(RequestContextDto requestContext,
                                                                 Long id,
                                                                 AccountDto accountDto) {
        try {
            accountDto.setId(id);
            return ResponseFactory.ResponseOk(mapper().entityToDto(accountService.update(mapper().dtoToEntity(accountDto))));
        } catch (Throwable e) {
            log.error(ERROR_API_EXCEPTION, e);
            return getBackExceptionResponse(e);
        }
    }

    @Override
    public ResponseEntity<String> resetPasswordViaToken(RequestContextDto requestContext,
                                                        ResetPwdViaTokenRequestDto resetPasswordViaTokenRequest) {
        try {
            return passwordService.resetPasswordViaToken(//RequestContextDto.builder().build(),
                    resetPasswordViaTokenRequest);
        } catch (Throwable e) {
            log.error(ERROR_API_EXCEPTION, e);
            return getBackExceptionResponse(e);
        }
    }

    @Override
    public ResponseEntity<List<MinAccountDto>> accountsByDomain(RequestContextDto requestContext) {
        log.info("get accounts by domain");
        try {
            List<MinAccountDto> list = minAccountMapper.listEntityToDto(accountService.getByDomain(requestContext.getSenderDomain()));
            if (CollectionUtils.isEmpty(list)) {
                return ResponseFactory.ResponseNoContent();
            }
            return ResponseFactory.ResponseOk(list);
        } catch (Throwable e) {
            log.error(ERROR_API_EXCEPTION, e);
            return getBackExceptionResponse(e);
        }
    }

    @Override
    public ResponseEntity<List<MinAccountDto>> chatAccountsByDomain(RequestContextDto requestContext) {
        log.info("get chat accounts by domain");
        try {
            List<MinAccountDto> list = minAccountMapper.listEntityToDto(accountService.chatAccountsByDomain(requestContext.getSenderDomain()));
            if (CollectionUtils.isEmpty(list)) {
                return ResponseFactory.ResponseNoContent();
            }
            return ResponseFactory.ResponseOk(list);
        } catch (Throwable e) {
            log.error(ERROR_API_EXCEPTION, e);
            return getBackExceptionResponse(e);
        }
    }

    @Override
    public ResponseEntity<?> resendCreationEmail(RequestContextDto requestContext, Long id) {
        try {
            accountService.resendCreationEmail(id);
            return ResponseFactory.ResponseOk();
        } catch (Throwable e) {
            log.error(ERROR_API_EXCEPTION, e);
            return getBackExceptionResponse(e);
        }
    }

    @Override
    public ResponseEntity<Integer> getConfirmedAccountNumberByResume(RequestContextDto requestContext) {
        try {
            return ResponseFactory.ResponseOk(accountService.getConfirmedAccountNumberByResume());
        } catch (Throwable e) {
            log.error(ERROR_API_EXCEPTION, e);
            return getBackExceptionResponse(e);
        }
    }

    @Override
    public ResponseEntity<Integer> getConfirmedAccountNumberByEmployee(RequestContextDto requestContext) {
        try {
            return ResponseFactory.ResponseOk(accountService.getConfirmedAccountNumberByEmployee());
        } catch (Throwable e) {
            log.error(ERROR_API_EXCEPTION, e);
            return getBackExceptionResponse(e);
        }
    }

}
