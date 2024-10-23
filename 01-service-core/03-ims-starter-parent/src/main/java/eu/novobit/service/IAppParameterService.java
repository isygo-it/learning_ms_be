package eu.novobit.service;

import eu.novobit.com.rest.service.ICrudServiceMethods;
import eu.novobit.model.AppParameter;


/**
 * The interface App parameter service.
 */
public interface IAppParameterService extends ICrudServiceMethods<AppParameter> {

    /**
     * Gets value by domain and name.
     *
     * @param domain       the domain
     * @param name         the name
     * @param allowDefault the allow default
     * @return the value by domain and name
     */
    String getValueByDomainAndName(String domain, String name, boolean allowDefault, String defaultValue);
}
