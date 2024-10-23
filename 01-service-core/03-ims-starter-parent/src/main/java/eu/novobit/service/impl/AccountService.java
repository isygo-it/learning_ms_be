package eu.novobit.service.impl;

import eu.novobit.annotation.CodeGenKms;
import eu.novobit.annotation.CodeGenLocal;
import eu.novobit.annotation.SrvRepo;
import eu.novobit.com.rest.service.AbstractCrudImageService;
import eu.novobit.config.AppProperties;
import eu.novobit.constants.AppParameterConstants;
import eu.novobit.constants.DomainConstants;
import eu.novobit.constants.JwtContants;
import eu.novobit.dto.RequestContextDto;
import eu.novobit.dto.TokenDto;
import eu.novobit.dto.data.*;
import eu.novobit.dto.request.AccountAuthTypeRequest;
import eu.novobit.dto.request.GeneratePwdRequestDto;
import eu.novobit.dto.response.UserContext;
import eu.novobit.dto.wsocket.WsConnectDto;
import eu.novobit.enumerations.*;
import eu.novobit.exception.AccountAuthenticationException;
import eu.novobit.exception.AccountNotFoundException;
import eu.novobit.exception.SendEmailException;
import eu.novobit.mapper.ApplicationMapper;
import eu.novobit.mapper.MinAccountMapper;
import eu.novobit.model.*;
import eu.novobit.model.schema.SchemaColumnConstantName;
import eu.novobit.model.schema.SchemaTableConstantName;
import eu.novobit.remote.kms.KmsIncrementalKeyService;
import eu.novobit.remote.kms.KmsPasswordService;
import eu.novobit.remote.kms.KmsTokenService;
import eu.novobit.remote.mms.MmsChatMessageService;
import eu.novobit.repository.AccountRepository;
import eu.novobit.repository.RegistredNewAccountRepository;
import eu.novobit.service.IAccountService;
import eu.novobit.service.IAppParameterService;
import eu.novobit.service.IDomainService;
import jakarta.transaction.NotSupportedException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.event.EventListener;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.*;

/**
 * The type Account service.
 */
@Slf4j
@Service
@Transactional
@CodeGenLocal(value = NextCodeService.class)
@CodeGenKms(value = KmsIncrementalKeyService.class)
@SrvRepo(value = AccountRepository.class)
public class AccountService extends AbstractCrudImageService<Account, AccountRepository>
        implements IAccountService {

    private final AppProperties appProperties;

    @Autowired
    private KmsPasswordService passwordService;
    @Autowired
    private KmsTokenService kmsTokenService;
    @Autowired
    private ApplicationMapper applicationMapper;
    @Autowired
    private MinAccountMapper minAccountMapper;
    @Autowired
    private IDomainService domainService;
    @Autowired
    private IAppParameterService parameterService;
    @Autowired
    private MmsChatMessageService chatMessageService;

    /**
     * Instantiates a new Account service.
     *
     * @param appProperties                 the app properties
     * @param registredNewAccountRepository the registred new account repository
     */
    public AccountService(AppProperties appProperties, RegistredNewAccountRepository registredNewAccountRepository) {
        this.appProperties = appProperties;
    }

    @Override
    public AppNextCode initCodeGenerator() {
        return AppNextCode.builder()
                .domain("default")
                .entity(Account.class.getSimpleName())
                .attribute(SchemaColumnConstantName.C_CODE)
                .prefix("RACT")
                .valueLength(6L)
                .value(1L)
                .increment(1)
                .build();
    }

    @Override
    public Account findByDomainAndUserName(String domain, String userName) {
        Optional<Account> optional = repository().findByDomainIgnoreCaseAndCodeIgnoreCase(domain, userName);
        if (optional.isPresent()) {
            return optional.get();
        }
        return null;
    }

    @Override
    public List<String> findEmailsByDomain(String domain) {
        if (DomainConstants.SUPER_DOMAIN_NAME.equals(domain)) {
            return repository().findDistinctEmails();
        } else {
            return repository().findDistinctEmailsByDomain(domain);
        }
    }

    @Override
    public List<Application> findDistinctAllowedToolsByDomainAndUserName(String domain, String userName) {
        Account account = findByDomainAndUserName(domain, userName);
        if (account != null) {
            List<Application> applications = new ArrayList<>();
            for (RoleInfo role : account.getRoleInfo()) {
                applications.addAll(role.getAllowedTools());
            }
            return applications.stream().distinct().toList();
        }
        return Collections.EMPTY_LIST;
    }

    @Override
    public Account updateAccountAdminStatus(Long id, IEnumBinaryStatus.Types newStatus) {
        repository().updateAccountAdminStatus(newStatus, id);
        return repository().findById(id).orElse(null);
    }


    @Override
    public Account updateAccountIsAdmin(Long id, boolean newStatus) {
        repository().updateAccountIsAdmin(newStatus, id);
        return repository().findById(id).orElse(null);
    }

    @Override
    public Account updateLanguage(Long id, IEnumLanguage.Types language) {
        repository().updateLanguage(language, id);
        return repository().findById(id).orElse(null);
    }

    public List<ApplicationDto> buildAllowedTools(Account account, String token) {
        List<Application> applications = new ArrayList<>();
        for (RoleInfo role : account.getRoleInfo()) {
            applications.addAll(role.getAllowedTools());
        }

        //Add param to load disabled applications
        String hideDisabledApp = parameterService.getValueByDomainAndName(account.getDomain(), AppParameterConstants.HIDE_DISABLED_APP, true, AppParameterConstants.NO);
        if (AppParameterConstants.YES.equals(hideDisabledApp)) {
            return applications.stream()
                    .filter(application -> IEnumBinaryStatus.Types.ENABLED == application.getAdminStatus())
                    .distinct()
                    .map(application -> {
                        ApplicationDto app = applicationMapper.entityToDto(application);
                        app.setToken(kmsTokenService.createTokenByDomain(//RequestContextDto.builder().build(),
                                account.getDomain(),
                                application.getName(),
                                IEnumAppToken.Types.ACCESS,
                                TokenRequestDto.builder()
                                        .subject(account.getCode())
                                        .claims(Map.of(JwtContants.JWT_SENDER_DOMAIN, account.getDomain(),
                                                JwtContants.JWT_SENDER_ACCOUNT_TYPE, account.getAccountType(),
                                                JwtContants.JWT_SENDER_USER, account.getCode(),
                                                JwtContants.JWT_LOG_APP, application.getName()))
                                        .build()).getBody());
                        return app;
                    }).toList();
        } else {
            return applications.stream()//.filter(application -> IEnumBinaryStatus.Types.ENABLED == application.getAdminStatus())
                    .distinct().
                    map(application -> {
                        ApplicationDto app = applicationMapper.entityToDto(application);
                        app.setToken(kmsTokenService.createTokenByDomain(//RequestContextDto.builder().build(),
                                account.getDomain(),
                                application.getName(),
                                IEnumAppToken.Types.ACCESS,
                                TokenRequestDto.builder()
                                        .subject(account.getCode())
                                        .claims(Map.of(JwtContants.JWT_SENDER_DOMAIN, account.getDomain(),
                                                JwtContants.JWT_SENDER_ACCOUNT_TYPE, account.getAccountType(),
                                                JwtContants.JWT_SENDER_USER, account.getCode(),
                                                JwtContants.JWT_LOG_APP, application.getName()))
                                        .build()).getBody());
                        return app;
                    }).toList();
        }
    }


    @Override
    public List<Account> getByDomain(String domain) {
        if (DomainConstants.SUPER_DOMAIN_NAME.equals(domain)) {
            return repository().findAll();
        } else {
            return repository().findByDomainIgnoreCaseIn(Arrays.asList(DomainConstants.DEFAULT_DOMAIN_NAME, domain));
        }
    }

    @Override
    public List<MinAccountDto> getMinInfoByDomain(String domain) throws NotSupportedException {
        if (DomainConstants.SUPER_DOMAIN_NAME.equals(domain)) {
            return minAccountMapper.listEntityToDto(findAll());
        } else {
            return minAccountMapper.listEntityToDto(findAll(domain));
        }
    }

    @Override
    public UserContext getAuthenticationType(AccountAuthTypeRequest accountAuthTypeRequest) throws AccountNotFoundException {
        if (!domainService.isEnabled(accountAuthTypeRequest.getDomain())) {
            throw new AccountAuthenticationException("domain disabled: " + accountAuthTypeRequest.getDomain());
        }
        Account account = findByDomainAndUserName(accountAuthTypeRequest.getDomain().trim(), accountAuthTypeRequest.getUserName().trim());
        if (account != null) {
            if (account.getAuthType().equals(IEnumAuth.Types.OTP)) {
                ResponseEntity<Boolean> result = passwordService.generate(//RequestContextDto.builder().build(),
                        IEnumAuth.Types.OTP, GeneratePwdRequestDto.builder()
                                .domain(account.getDomain())
                                .email(account.getEmail())
                                .userName(account.getCode())
                                .fullName(account.getFullName())
                                .build());
                if (result.getStatusCode().is2xxSuccessful() && result.hasBody() && result.getBody()) {
                    return UserContext.builder().authTypeMode(IEnumAuth.Types.OTP)
                            .otpLength(4)
                            .build();
                } else {
                    throw new AccountAuthenticationException("OTP code was not generated");
                }
            } else if (account.getAuthType().equals(IEnumAuth.Types.QRC)) {
                ResponseEntity<TokenDto> result = kmsTokenService.createTokenByDomain(//RequestContextDto.builder().build(),
                        account.getDomain(),
                        "QRC_AUTH",
                        IEnumAppToken.Types.QRC,
                        TokenRequestDto.builder()
                                .subject(account.getCode())
                                .claims(Map.of(JwtContants.JWT_SENDER_DOMAIN, accountAuthTypeRequest.getDomain(),
                                        JwtContants.JWT_LOG_APP, "QRC_AUTH",
                                        JwtContants.JWT_SENDER_USER, account.getCode()))
                                .build());
                if (result.getStatusCode().is2xxSuccessful() && result.hasBody()) {
                    return UserContext.builder().authTypeMode(IEnumAuth.Types.QRC)
                            .qrCodeToken(result.getBody().getToken())
                            .build();
                }
                return UserContext.builder().authTypeMode(IEnumAuth.Types.QRC)
                        .qrCodeToken(null)
                        .build();
            } else {
                return UserContext.builder().authTypeMode(IEnumAuth.Types.PWD)
                        .build();
            }

        } else {
            throw new AccountNotFoundException("with domain: " + accountAuthTypeRequest.getDomain() + " and username with " + accountAuthTypeRequest.getUserName());
        }
    }

    @Override
    public boolean switchAuthType(AccountAuthTypeRequest accountAuthTypeRequest) throws AccountNotFoundException {
        Account account = findByDomainAndUserName(accountAuthTypeRequest.getDomain(), accountAuthTypeRequest.getUserName());
        if (account != null) {
            if (accountAuthTypeRequest.getAuthType() == null) {
                if (account.getAuthType().equals(IEnumAuth.Types.OTP)) {
                    account.setAuthType(IEnumAuth.Types.PWD);
                } else {
                    account.setAuthType(IEnumAuth.Types.OTP);
                }
            } else {
                account.setAuthType(accountAuthTypeRequest.getAuthType());
            }
            this.update(account);
            return true;
        } else {
            throw new AccountNotFoundException("with domain: " + accountAuthTypeRequest.getDomain() + " and username with " + accountAuthTypeRequest.getUserName());
        }
    }

    @Override
    protected String getUploadDirectory() {
        return this.appProperties.getUploadDirectory();
    }

    @Override
    public boolean checkIfApplicationAllowed(String domain, String userName, String application) {
        Optional<Account> optional = repository().findByDomainIgnoreCaseAndCodeIgnoreCase(domain, userName);
        if (optional.isPresent()) {
            for (RoleInfo roleInfo : optional.get().getRoleInfo()) {
                if (roleInfo.getAllowedTools().stream().parallel().anyMatch(app -> app.getName().equals(application))) {
                    return true;
                }
            }
        } else {
            throw new AccountNotFoundException(domain + "/" + userName);
        }

        return false;
    }

    @Override
    public void trackUserConnections(String domain, String userName, ConnectionTracking connectionTracking) {
        Account account = this.findByDomainAndUserName(domain, userName);
        if (account != null) {
            if (CollectionUtils.isEmpty(account.getConnectionTrackings())) {
                account.setConnectionTrackings(new ArrayList<>());
            }

            account.getConnectionTrackings().add(connectionTracking);
            this.saveOrUpdate(account);
        }
    }

    @Override
    public List<Account> chatAccountsByDomain(String domain) {
        List<Account> list = this.getByDomain(domain);
        if (CollectionUtils.isEmpty(list)) {
            return Collections.EMPTY_LIST;
        }
        ResponseEntity<List<WsConnectDto>> result = chatMessageService.getChatStatus(RequestContextDto.builder().build(),
                domainService.findByName(domain).getId());
        if (result.getStatusCode().is2xxSuccessful() && result.hasBody()) {
            List<WsConnectDto> connections = result.getBody();
            if (!CollectionUtils.isEmpty(connections)) {
                list.stream().forEach(account -> {
                    Optional<WsConnectDto> optional = connections.stream()
                            .filter(wsConnectDto -> wsConnectDto.getSenderId() == account.getId())
                            .findFirst();
                    if (optional.isPresent()) {
                        account.setChatStatus(optional.get().getStatus());
                    }
                });
            }
        }
        return list;
    }

    @Override
    public boolean resendCreationEmail(Long id) {
        Account account = this.findById(id);
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
            return true;
        } else {
            throw new SendEmailException("Account email reminder");
        }
    }

    @Override
    public AccountGlobalStatDto getGlobalStatistics(IEnumSharedStatType.Types statType, RequestContextDto requestContextDto) {
        AccountGlobalStatDto.AccountGlobalStatDtoBuilder builder = AccountGlobalStatDto.builder();
        switch (statType) {
            case TOTAL_COUNT:
                builder.totalCount(getTotalAccountCount(requestContextDto));
                break;
            case ACTIVE_COUNT:
                builder.activeCount(getActiveAccount(requestContextDto));
                break;
            case CONFIRMED_COUNT:
                builder.confirmedCount(getConfirmedAccount(requestContextDto));
                break;
            default:
                throw new IllegalArgumentException("Unknown stat type: " + statType);
        }

        return builder.build();
    }
    private Integer getTotalAccountCount(RequestContextDto requestContextDto) {
        Long totalEmployeeCount = repository().countByDomainAndCancelDateNull(requestContextDto.getSenderDomain());
        return totalEmployeeCount.intValue();
    }
    private Integer getActiveAccount(RequestContextDto requestContextDto) {
        Long totalActiveCount = repository().countByDomainAndCancelDateNullAndSystemStatus(requestContextDto.getSenderDomain(), IEnumBinaryStatus.Types.ENABLED);
        return totalActiveCount.intValue();
    }
    private Integer getConfirmedAccount(RequestContextDto requestContextDto) {
        return repository().countByOriginAccount(requestContextDto.getSenderDomain());

    }


    @Override
    public AccountStatDto getObjectStatistics(String code) {
        return AccountStatDto.builder()
                .build();
    }

    @Override
    public Integer getConfirmedAccountNumberByResume() {
        return repository().countByOriginResume();

    }

    @Override
    public Integer getConfirmedAccountNumberByEmployee() {
        return repository().countByOriginEmployee();
    }

    /**
     * Gets all accounts min.
     *
     * @return the all accounts min
     */
    @Cacheable(cacheNames = SchemaTableConstantName.T_ACCOUNT)
    @EventListener(ApplicationReadyEvent.class)
    public List<MinAccountDto> getAllAccountsMin() {
        return minAccountMapper.listEntityToDto(this.findAll());
    }
}
