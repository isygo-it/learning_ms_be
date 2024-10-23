package eu.novobit.service;

import eu.novobit.com.rest.service.ICrudServiceMethods;
import eu.novobit.enumerations.IEnumAppToken;
import eu.novobit.model.AccessToken;

/**
 * The interface Access token service.
 */
public interface IAccessTokenService extends ICrudServiceMethods<AccessToken> {

    /**
     * Find by application and account code and token and token type access token.
     *
     * @param application the application
     * @param accountCode the account code
     * @param token       the token
     * @param tokenType   the token type
     * @return the access token
     */
    public AccessToken findByApplicationAndAccountCodeAndTokenAndTokenType(String application, String accountCode, String token, IEnumAppToken.Types tokenType);

    /**
     * Find by account code and token and token type access token.
     *
     * @param accountCode the account code
     * @param token       the token
     * @param tokenType   the token type
     * @return the access token
     */
    public AccessToken findByAccountCodeAndTokenAndTokenType(String accountCode, String token, IEnumAppToken.Types tokenType);
}
