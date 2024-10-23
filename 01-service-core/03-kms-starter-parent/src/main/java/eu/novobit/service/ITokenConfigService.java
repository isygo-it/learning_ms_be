package eu.novobit.service;

import eu.novobit.com.rest.service.ICrudServiceMethods;
import eu.novobit.enumerations.IEnumAppToken;
import eu.novobit.model.TokenConfig;

/**
 * The interface Token config service.
 */
public interface ITokenConfigService extends ICrudServiceMethods<TokenConfig> {

    /**
     * Build token config token config.
     *
     * @param domain    the domain
     * @param tokenType the token type
     * @return the token config
     */
    TokenConfig buildTokenConfig(String domain, IEnumAppToken.Types tokenType);
}
