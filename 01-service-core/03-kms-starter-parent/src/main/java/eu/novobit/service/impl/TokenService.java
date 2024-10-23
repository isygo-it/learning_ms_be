package eu.novobit.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import eu.novobit.config.AppProperties;
import eu.novobit.constants.AppParameterConstants;
import eu.novobit.constants.JwtContants;
import eu.novobit.dto.RequestContextDto;
import eu.novobit.dto.TokenDto;
import eu.novobit.dto.data.MailMessageDto;
import eu.novobit.enumerations.IEnumAppToken;
import eu.novobit.enumerations.IEnumTemplateName;
import eu.novobit.exception.TokenConfigNotFoundException;
import eu.novobit.exception.TokenInvalidException;
import eu.novobit.exception.UserNotFoundException;
import eu.novobit.jwt.JwtService;
import eu.novobit.model.AccessToken;
import eu.novobit.model.Account;
import eu.novobit.model.TokenConfig;
import eu.novobit.remote.ims.ImsAppParameterService;
import eu.novobit.service.*;
import eu.novobit.types.EmailSubjects;
import eu.novobit.types.TemplateVariables;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Map;

/**
 * The type Token service.
 */
@Slf4j
@Service
@Transactional
public class TokenService extends JwtService implements ITokenService {

    private final AppProperties appProperties;

    @Autowired
    private ITokenConfigService tokenConfigService;
    @Autowired
    private IDomainService domainService;
    @Autowired
    private IMsgService msgService;
    @Autowired
    private IAccessTokenService accessTokenService;
    @Autowired
    private ImsAppParameterService appParameterService;

    /**
     * Instantiates a new Token service.
     *
     * @param appProperties the app properties
     */
    public TokenService(AppProperties appProperties) {
        this.appProperties = appProperties;
    }

    public TokenDto buildTokenAndSave(String domain, String application, IEnumAppToken.Types tokenType, String subject, Map<String, Object> claims) {
        //Get Token config configured by domain and type, otherwise, default one
        TokenConfig tokenConfig = tokenConfigService.buildTokenConfig(domain, tokenType);
        if (tokenConfig != null) {
            TokenDto token = super.createToken(new StringBuilder(subject.toLowerCase()).append("@").append(domain).toString(),
                    claims,
                    tokenConfig.getIssuer(),
                    tokenConfig.getAudience(),
                    SignatureAlgorithm.valueOf(tokenConfig.getSignatureAlgorithm()),
                    tokenConfig.getSecretKey(),
                    tokenConfig.getLifeTimeInMs());
            //Save generated token
            AccessToken accessToken = AccessToken.builder()
                    .tokenType(tokenType)
                    .application(application)
                    .token(token.getToken())
                    .expiryDate(token.getExpiryDate())
                    .accountCode(subject)
                    .deprecated(Boolean.FALSE)
                    .build();
            accessTokenService.create(accessToken);
            return token;
        } else {
            throw new TokenConfigNotFoundException("for domain: " + domain + "/" + tokenType.name());
        }
    }

    public TokenDto buildToken(String domain, String application, IEnumAppToken.Types tokenType, String subject, Map<String, Object> claims) {
        //Get Token config configured by domain and type, otherwise, default one
        TokenConfig tokenConfig = tokenConfigService.buildTokenConfig(domain, tokenType);
        if (tokenConfig != null) {
            TokenDto token = super.createToken(new StringBuilder(subject.toLowerCase()).append("@").append(domain).toString(),
                    claims,
                    tokenConfig.getIssuer(),
                    tokenConfig.getAudience(),
                    SignatureAlgorithm.valueOf(tokenConfig.getSignatureAlgorithm()),
                    tokenConfig.getSecretKey(),
                    tokenConfig.getLifeTimeInMs());
            return token;
        } else {
            throw new TokenConfigNotFoundException("for domain: " + domain + "/" + tokenType.name());
        }
    }

    @Override
    public boolean isTokenValid(String domain, String application, IEnumAppToken.Types tokenType, String token, String subject) {
        //Get Token config configured by domain and type, otherwise, default one
        TokenConfig tokenConfig = tokenConfigService.buildTokenConfig(domain, tokenType);
        if (tokenConfig != null) {
            //Validate token content
            super.validateToken(token, subject, tokenConfig.getSecretKey());
        } else {
            log.error("Token config not found for domain: " + domain + "/" + tokenType.name());
            throw new TokenConfigNotFoundException("for domain: " + domain + "/" + tokenType.name());
        }

        //Validate token existance
        String[] userNameArray = subject.split("@");
        //TEMP COMMENTED: AccessToken accessToken = accessTokenService.findByApplicationAndAccountCodeAndTokenAndTokenType(application, userNameArray[0], token, tokenType);
        AccessToken accessToken = accessTokenService.findByAccountCodeAndTokenAndTokenType(userNameArray[0], token, tokenType);
        if (accessToken == null) {
            log.error("Token not found for domain: " + domain + "/" + tokenType.name());
            throw new TokenInvalidException("Invalid JWT: not found or deprecated");
        }
        return true;
    }

    @Override
    public void createForgotPasswordAccessToken(String domain, String application, String accountCode) throws JsonProcessingException {
        //Get the account
        Account account = domainService.checkAccountIfExists(domain, null, null, accountCode, null, false);
        if (account == null) {
            log.error("Account not found for domain/username: " + domain + "/" + accountCode);
            throw new UserNotFoundException("domain/username: " + domain + "/" + accountCode);
        }

        //Generate reset password token
        TokenDto token = this.buildTokenAndSave(domain, application, IEnumAppToken.Types.RSTPWD, accountCode,
                Map.of(JwtContants.JWT_SENDER_DOMAIN, domain,
                        JwtContants.JWT_SENDER_USER, accountCode,
                        JwtContants.JWT_LOG_APP, application));

        //Send reset password email
        sendForgotPasswordEmail(domain, application, account, token);
    }

    private void sendForgotPasswordEmail(String domain, String application, Account account, TokenDto token) throws JsonProcessingException {
        //Build Email template data
        String resetPwdUrl = "http://localhost:4000/reset-password/";
        ResponseEntity<String> result = appParameterService.getValueByDomainAndName(RequestContextDto.builder().build(),
                domain, AppParameterConstants.APPURL + "." + application.toUpperCase(), true, "http://localhost:4000/reset-password/");
        if (result.getStatusCode().is2xxSuccessful() && result.hasBody() && StringUtils.hasText(result.getBody())) {
            resetPwdUrl = result.getBody();
        }
        MailMessageDto mailMessageDto = MailMessageDto.builder()
                .subject(EmailSubjects.FORGOT_PASSWORD_EMAIL_SUBJECT)
                .domain(domain)
                .toAddr(account.getEmail())
                .templateName(IEnumTemplateName.Types.FORGOT_PASSWORD_TEMPLATE)
                .variables(MailMessageDto.getVariablesAsString(Map.of(
                        //Common vars
                        TemplateVariables.V_USER_NAME, account.getCode(),
                        TemplateVariables.V_FULLNAME, account.getFullName(),
                        TemplateVariables.V_DOMAIN_NAME, account.getDomain(),
                        //Specific vars
                        TemplateVariables.V_RESET_TOKEN, token.getToken(),
                        TemplateVariables.V_RESET_PWD_URL, resetPwdUrl + "/reset-password/?token=")))
                .build();
        //Send the email message
        msgService.sendMessage(domain, mailMessageDto, appProperties.isSendAsyncEmail());
    }

    @Override
    public TokenDto createAccessToken(String domain, String application, String userName, Boolean isAdmin) {
        TokenDto token = this.buildTokenAndSave(domain, application, IEnumAppToken.Types.ACCESS, userName,
                Map.of(JwtContants.JWT_SENDER_DOMAIN, domain,
                        JwtContants.JWT_SENDER_USER, userName,
                        JwtContants.JWT_LOG_APP, application,
                        JwtContants.JWT_IS_ADMIN, isAdmin)
        );
        return token;
    }

    @Override
    public TokenDto createRefreshToken(String domain, String application, String userName) {
        TokenDto token = this.buildTokenAndSave(domain, application, IEnumAppToken.Types.REFRESH, userName,
                Map.of(JwtContants.JWT_SENDER_DOMAIN, domain,
                        JwtContants.JWT_SENDER_USER, userName,
                        JwtContants.JWT_LOG_APP, application)
        );
        return token;
    }

    @Override
    public TokenDto createAuthorityToken(String domain, String application, String userName, List<String> authorities) {
        TokenDto token = this.buildToken(domain, application, IEnumAppToken.Types.AUTHORITY, userName,
                Map.of(JwtContants.JWT_SENDER_DOMAIN, domain,
                        JwtContants.JWT_SENDER_USER, userName,
                        JwtContants.JWT_LOG_APP, application,
                        JwtContants.JWT_GRANTED_AUTHORITY, authorities)
        );
        return token;
    }
}
