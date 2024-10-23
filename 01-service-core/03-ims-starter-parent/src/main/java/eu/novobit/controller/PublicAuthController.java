package eu.novobit.controller;

import eu.novobit.annotation.CtrlHandler;
import eu.novobit.api.PublicAuthControllerApi;
import eu.novobit.com.rest.controller.AbstractController;
import eu.novobit.com.rest.controller.ResponseFactory;
import eu.novobit.config.AppProperties;
import eu.novobit.config.JwtProperties;
import eu.novobit.dto.SystemInfoDto;
import eu.novobit.dto.UserContextDto;
import eu.novobit.dto.data.DomainDto;
import eu.novobit.dto.data.ThemeDto;
import eu.novobit.dto.request.AccountAuthTypeRequest;
import eu.novobit.dto.request.AuthRequestDto;
import eu.novobit.dto.request.RegisterNewAccountDto;
import eu.novobit.dto.request.RequestTrackingDto;
import eu.novobit.dto.response.AuthResponseDto;
import eu.novobit.dto.response.UserContext;
import eu.novobit.dto.response.UserDataResponseDto;
import eu.novobit.enumerations.IEnumJwtStorage;
import eu.novobit.enumerations.IEnumWebToken;
import eu.novobit.exception.handler.ImsExceptionHandler;
import eu.novobit.mapper.DomainMapper;
import eu.novobit.mapper.RegisterNewAccountMapper;
import eu.novobit.mapper.ThemeMapper;
import eu.novobit.model.Account;
import eu.novobit.model.Domain;
import eu.novobit.remote.kms.KmsPublicPasswordService;
import eu.novobit.service.IAccountService;
import eu.novobit.service.IAuthService;
import eu.novobit.service.IDomainService;
import eu.novobit.service.IThemeService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * The type Public auth controller.
 */
@Slf4j
@Validated
@RestController
@CtrlHandler(ImsExceptionHandler.class)
@RequestMapping(path = "/api/public/user")
public class PublicAuthController extends AbstractController implements PublicAuthControllerApi {

    private final AppProperties appProperties;
    private final JwtProperties jwtProperties;

    @Autowired
    RegisterNewAccountMapper registerNewAccountMapper;
    @Autowired
    private KmsPublicPasswordService kmsPublicPasswordService;
    @Autowired
    private IAccountService accountService;
    @Autowired
    private IThemeService themeService;
    @Autowired
    private IAuthService authService;
    @Autowired
    private IDomainService domainService;
    @Autowired
    private ThemeMapper themeMapper;
    @Autowired
    private DomainMapper domainMapper;

    public PublicAuthController(AppProperties appProperties, JwtProperties jwtProperties) {
        this.appProperties = appProperties;
        this.jwtProperties = jwtProperties;
    }

    public ResponseEntity<AuthResponseDto> authenticate(HttpServletRequest request, HttpServletResponse response,
                                                        AuthRequestDto authRequestDto) {
        try {
            AuthResponseDto authenticate = authService.authenticate(RequestTrackingDto.getFromRequest(request),
                    authRequestDto.getDomain().trim(), authRequestDto.getUserName().trim(), authRequestDto.getApplication(),
                    authRequestDto.getPassword().trim(), authRequestDto.getAuthType());
            if (jwtProperties.getJwtStorageType() == IEnumJwtStorage.Types.COOKIE) {
                response.addCookie(this.createCookie("token_type", IEnumWebToken.Types.Bearer.meaning()));
                response.addCookie(this.createCookie("access_token", authenticate.getAccessToken()));
                response.addCookie(this.createCookie("refresh_token", authenticate.getRefreshToken()));
                return ResponseFactory.ResponseOk(AuthResponseDto.builder()
                        .build());
            }
            Account account = accountService.findByDomainAndUserName(authRequestDto.getDomain(), authRequestDto.getUserName());
            Domain domain = domainService.findByName(authRequestDto.getDomain());
            ThemeDto theme = themeMapper.entityToDto(themeService.findThemeByAccountCodeAndDomainCode(account.getCode(), domain.getCode()));
            UserDataResponseDto userDataResponseDto = UserDataResponseDto.builder()
                    .id(account.getId())
                    .userName(account.getCode())
                    .firstName(account.getAccountDetails().getFirstName())
                    .lastName(account.getAccountDetails().getLastName())
                    .applications(accountService.buildAllowedTools(account, authenticate.getAccessToken()))
                    .email(account.getEmail())
                    .domainId(domain.getId())
                    .domainImagePath(domain.getImagePath())
                    .language(account.getLanguage())
                    .role(account.getFunctionRole())
                    .build();

            return ResponseFactory.ResponseOk(AuthResponseDto.builder()
                    .tokenType(IEnumWebToken.Types.Bearer)
                    .accessToken(authenticate.getAccessToken())
                    .refreshToken(authenticate.getRefreshToken())
                    .authorityToken(authenticate.getAuthorityToken())
                    .userDataResponseDto(userDataResponseDto)
                    .theme(theme)
                    .systemInfo(SystemInfoDto
                            .builder()
                            .name(appProperties.getApplicationName())
                            .version(appProperties.getApplicationVersion())
                            .build())
                    .build());
        } catch (Throwable e) {
            log.error("<Error>: api exception : {} ", e);
            return getBackExceptionResponse(e);
        }
    }

    @Override
    public ResponseEntity<Boolean> generateForgotPWDToken(UserContextDto userContextDto) {
        try {
            kmsPublicPasswordService.generateForgotPasswordAccessToken(userContextDto);
            return ResponseFactory.ResponseOk(true);
        } catch (Throwable e) {
            log.error("<Error>: api exception : {} ", e);
            return getBackExceptionResponse(e);
        }
    }

    private Cookie createCookie(String name, String value) {
        Cookie cookie = new Cookie(name, value);
        cookie.setMaxAge(7 * 24 * 60 * 60); // expires in 7 days
        cookie.setSecure(true);
        cookie.setHttpOnly(true);
        cookie.setPath("/"); // Global
        return cookie;
    }

    @Override
    public ResponseEntity<Boolean> registerNewAccount(RegisterNewAccountDto registerNewAccountDto) {
        try {
            return ResponseFactory.ResponseOk(authService.registerNewAccount(registerNewAccountMapper.dtoToEntity(registerNewAccountDto)));
        } catch (Throwable e) {
            log.error("<Error>: api exception : {} ", e);
            return getBackExceptionResponse(e);
        }
    }

    @Override
    public ResponseEntity<DomainDto> getDomainByName(String domain) {
        log.info("get domain by name {}", domain);
        try {
            return ResponseFactory.ResponseOk(domainMapper.entityToDto(domainService.findByName(domain)));
        } catch (Throwable e) {
            log.error("<Error>: get by name : {} ", e);
            return getBackExceptionResponse(e);
        }
    }

    @Override
    public ResponseEntity<UserContext> getAuthenticationType(AccountAuthTypeRequest accountAuthTypeRequest) {
        try {
            return ResponseFactory.ResponseOk(accountService.getAuthenticationType(accountAuthTypeRequest));
        } catch (Throwable e) {
            log.error("<Error>: api exception : {} ", e);
            return getBackExceptionResponse(e);
        }
    }

    @Override
    public ResponseEntity<Boolean> switchAuthType(AccountAuthTypeRequest accountAuthTypeRequest) {
        try {
            return ResponseFactory.ResponseOk(accountService.switchAuthType(accountAuthTypeRequest));
        } catch (Throwable e) {
            log.error("<Error>: api exception : {} ", e);
            return getBackExceptionResponse(e);
        }
    }
}
