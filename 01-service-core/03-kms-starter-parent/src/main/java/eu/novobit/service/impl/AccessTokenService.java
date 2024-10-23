package eu.novobit.service.impl;

import eu.novobit.annotation.SrvRepo;
import eu.novobit.com.rest.service.AbstractCrudService;
import eu.novobit.config.AppProperties;
import eu.novobit.enumerations.IEnumAppToken;
import eu.novobit.model.AccessToken;
import eu.novobit.model.schema.SchemaTableConstantName;
import eu.novobit.repository.AccessTokenRepository;
import eu.novobit.service.IAccessTokenService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * The type Access token service.
 */
@Slf4j
@Service
@Transactional
@SrvRepo(value = AccessTokenRepository.class)
public class AccessTokenService extends AbstractCrudService<AccessToken, AccessTokenRepository> implements IAccessTokenService {

    private final AppProperties appProperties;

    /**
     * Instantiates a new Access token service.
     *
     * @param appProperties the app properties
     */
    public AccessTokenService(AppProperties appProperties) {
        this.appProperties = appProperties;
    }

    @Cacheable(cacheNames = SchemaTableConstantName.T_ACCESS_TOKEN, key = "{#application, #accountCode, #token, #tokenType}")
    public AccessToken findByApplicationAndAccountCodeAndTokenAndTokenType(String application, String accountCode, String token, IEnumAppToken.Types tokenType) {
        Optional<AccessToken> optional = repository().findFirstByApplicationAndAccountCodeIgnoreCaseAndTokenAndTokenTypeAndDeprecatedFalseOrderByCreateDateDesc(application, accountCode, token, tokenType);
        if (optional.isPresent()) {
            return optional.get();
        }
        return null;
    }

    @Override
    public AccessToken findByAccountCodeAndTokenAndTokenType(String accountCode, String token, IEnumAppToken.Types tokenType) {
        Optional<AccessToken> optional = repository().findFirstByAccountCodeIgnoreCaseAndTokenAndTokenTypeAndDeprecatedFalseOrderByCreateDateDesc(accountCode, token, tokenType);
        if (optional.isPresent()) {
            return optional.get();
        }
        return null;
    }

    @Override
    public AccessToken beforeCreate(AccessToken accessToken) {
        //Deactivate old tokens (user, token type, application)
        if (appProperties.isDeactivateOldTokens()) {
            repository().deactivateOldTokens(accessToken.getAccountCode(),
                    accessToken.getTokenType(),
                    accessToken.getApplication());
        }
        return accessToken;
    }

    @CachePut(cacheNames = SchemaTableConstantName.T_ACCESS_TOKEN,
            key = "{#accessToken.application, #accessToken.accountCode, #accessToken.token, #accessToken.tokenType}")
    @Override
    public AccessToken create(AccessToken accessToken) {
        return super.create(accessToken);
    }

    @CachePut(cacheNames = SchemaTableConstantName.T_ACCESS_TOKEN,
            key = "{#accessToken.application, #accessToken.accountCode, #accessToken.token, #accessToken.tokenType}")
    @Override
    public AccessToken update(AccessToken accessToken) {
        return super.update(accessToken);
    }
}
