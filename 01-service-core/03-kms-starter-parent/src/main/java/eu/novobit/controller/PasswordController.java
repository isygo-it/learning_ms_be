package eu.novobit.controller;

import eu.novobit.annotation.CtrlHandler;
import eu.novobit.api.PasswordControllerApi;
import eu.novobit.com.rest.controller.AbstractController;
import eu.novobit.com.rest.controller.ResponseFactory;
import eu.novobit.dto.RequestContextDto;
import eu.novobit.dto.ResetPwdViaTokenRequestDto;
import eu.novobit.dto.request.*;
import eu.novobit.dto.response.AccessResponseDto;
import eu.novobit.enumerations.IEnumAppToken;
import eu.novobit.enumerations.IEnumAuth;
import eu.novobit.enumerations.IEnumPasswordStatus;
import eu.novobit.enumerations.IEnumWebToken;
import eu.novobit.exception.AccountAuthenticationException;
import eu.novobit.exception.handler.KmsExceptionHandler;
import eu.novobit.jwt.IJwtService;
import eu.novobit.mapper.AccountMapper;
import eu.novobit.model.TokenConfig;
import eu.novobit.service.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * The type Password controller.
 */
@Slf4j
@Validated
@RestController
@CtrlHandler(KmsExceptionHandler.class)
@RequestMapping(path = "/api/private/password")
public class PasswordController extends AbstractController implements PasswordControllerApi {

    @Autowired
    private IAccountService accountService;
    @Autowired
    private IDomainService domainService;
    @Autowired
    private AccountMapper accountMapper;
    @Autowired
    private IPasswordService passwordService;
    @Autowired
    private ITokenService tokenService;
    @Autowired
    private IJwtService jwtService;
    @Autowired
    private ITokenConfigService tokenConfigService;

    @Override
    public ResponseEntity<Boolean> generate(//RequestContextDto requestContext,
                                            IEnumAuth.Types type,
                                            GeneratePwdRequestDto generatePwdRequest) {
        log.info("Call generate password for domain {}", generatePwdRequest);
        try {
            String password = passwordService.generateRandomPassword(
                    generatePwdRequest.getDomain()
                    , generatePwdRequest.getDomainUrl()
                    , generatePwdRequest.getEmail()
                    , generatePwdRequest.getUserName()
                    , generatePwdRequest.getFullName()
                    , type);
            //Never return the password
            //TODO Send password to the user by email
            log.info("password genarated for {}/{} : {}", generatePwdRequest.getDomain(), generatePwdRequest.getUserName(), password);
            return ResponseFactory.ResponseOk(Boolean.TRUE);
        } catch (Throwable e) {
            log.error("<Error>: api exception : {} ", e);
            return getBackExceptionResponse(e);
        }
    }

    @Override
    public ResponseEntity<String> resetPasswordViaToken(//RequestContextDto requestContext,
                                                        ResetPwdViaTokenRequestDto resetPwdViaTokenRequestDto) {
        try {
            passwordService.resetPasswordViaToken(resetPwdViaTokenRequestDto);
            return ResponseFactory.ResponseOk("password changed successfully");
        } catch (Throwable e) {
            log.error("<Error>: api exception : {} ", e);
            return getBackExceptionResponse(e);
        }
    }

    @Override
    public ResponseEntity<String> changePassword(RequestContextDto requestContext,
                                                 String oldPassword,
                                                 String newPassword) {
        try {
            passwordService.changePassword(requestContext.getSenderDomain(), requestContext.getSenderUser(),
                    oldPassword, newPassword);
            return ResponseFactory.ResponseOk("password changed successfully");
        } catch (Throwable e) {
            log.error("<Error>: api exception : {} ", e);
            return getBackExceptionResponse(e);
        }
    }

    @Override
    public ResponseEntity<Boolean> patternCheck(//RequestContextDto requestContext,
                                                CheckPwdRequestDto checkPwdRequest) {
        log.info("Call check password for domain {}", checkPwdRequest);
        try {
            return ResponseFactory.ResponseOk(passwordService.checkForPattern(checkPwdRequest.getDomain()
                    , checkPwdRequest.getPassword()));
        } catch (Throwable e) {
            log.error("<Error>: api exception : {} ", e);
            return getBackExceptionResponse(e);
        }
    }

    @Override
    public ResponseEntity<AccessResponseDto> getAccess(//RequestContextDto requestContext,
                                                       AccessRequestDto accessRequest) {
        log.info("Call access for domain {}", accessRequest);
        try {
            if (!domainService.isEnabled(accessRequest.getDomain())) {
                throw new AccountAuthenticationException("domain disabled: " + accessRequest.getDomain());
            }

            if (IEnumAuth.Types.TOKEN == accessRequest.getAuthType()) {
                try {
                    TokenConfig tokenConfig = tokenConfigService.buildTokenConfig(accessRequest.getDomain(), IEnumAppToken.Types.ACCESS);
                    jwtService.validateToken(accessRequest.getPassword(),
                            new StringBuilder(accessRequest.getUserName().toLowerCase()).append("@").append(accessRequest.getDomain()).toString(),
                            tokenConfig.getSecretKey());
                } catch (Exception e) {
                    return ResponseFactory.ResponseOk(AccessResponseDto.builder()
                            .status(IEnumPasswordStatus.Types.UNAUTHORIZED)
                            .build());
                }

                return ResponseFactory.ResponseOk(AccessResponseDto.builder()
                        .status(IEnumPasswordStatus.Types.VALID)
                        .tokenType(IEnumWebToken.Types.Bearer)
                        .accessToken(tokenService.createAccessToken(accessRequest.getDomain(),
                                accessRequest.getApplication(),
                                accessRequest.getUserName(),
                                accessRequest.getIsAdmin()).getToken())
                        .refreshToken(tokenService.createRefreshToken(accessRequest.getDomain(),
                                accessRequest.getApplication(),
                                accessRequest.getUserName()).getToken())
                        .authorityToken(tokenService.createAuthorityToken(accessRequest.getDomain(),
                                accessRequest.getApplication(),
                                accessRequest.getUserName(),
                                accessRequest.getAuthorities()).getToken())
                        .build());
            } else {
                return ResponseFactory.ResponseOk(AccessResponseDto.builder()
                        .status(passwordService.matches(accessRequest.getDomain()
                                , accessRequest.getUserName()
                                , accessRequest.getPassword()
                                , accessRequest.getAuthType()))
                        .tokenType(IEnumWebToken.Types.Bearer)
                        .accessToken(tokenService.createAccessToken(accessRequest.getDomain(),
                                accessRequest.getApplication(),
                                accessRequest.getUserName(),
                                accessRequest.getIsAdmin()).getToken())
                        .refreshToken(tokenService.createRefreshToken(accessRequest.getDomain(),
                                accessRequest.getApplication(),
                                accessRequest.getUserName()).getToken())
                        .authorityToken(tokenService.createAuthorityToken(accessRequest.getDomain(),
                                accessRequest.getApplication(),
                                accessRequest.getUserName(),
                                accessRequest.getAuthorities()).getToken())
                        .build());
            }
        } catch (Throwable e) {
            log.error("<Error>: api exception : {} ", e);
            return getBackExceptionResponse(e);
        }
    }

    @Override
    public ResponseEntity<IEnumPasswordStatus.Types> matches(//RequestContextDto requestContext,
                                                             MatchesRequestDto matchesRequest) {
        log.info("Call match password for domain {}", matchesRequest);
        try {
            return ResponseFactory.ResponseOk(passwordService.matches(matchesRequest.getDomain()
                    , matchesRequest.getUserName()
                    , matchesRequest.getPassword()
                    , matchesRequest.getAuthType()));
        } catch (Throwable e) {
            log.error("<Error>: api exception : {} ", e);
            return getBackExceptionResponse(e);
        }
    }

    @Override
    public ResponseEntity<Boolean> isPasswordExpired(//RequestContextDto requestContext,
                                                     IsPwdExpiredRequestDto isPwdExpiredRequestDto) {
        log.info("Call isPasswordExpired {}", isPwdExpiredRequestDto);
        try {
            return ResponseFactory.ResponseOk(passwordService.isExpired(isPwdExpiredRequestDto.getDomain()
                    , isPwdExpiredRequestDto.getEmail()
                    , isPwdExpiredRequestDto.getUserName()
                    , isPwdExpiredRequestDto.getAuthType()));
        } catch (Throwable e) {
            log.error("<Error>: api exception : {} ", e);
            return getBackExceptionResponse(e);
        }
    }

    @Override
    public ResponseEntity<Boolean> updateAccount(//RequestContextDto requestContext,
                                                 UpdateAccountRequestDto account) {
        log.info("Call update account " + account.toString());
        try {
            return ResponseFactory.ResponseOk(accountService.checkIfExists(accountMapper.dtoToEntity(account),
                    true));
        } catch (Throwable e) {
            log.error("<Error>: api exception : {} ", e);
            return getBackExceptionResponse(e);
        }
    }
}
