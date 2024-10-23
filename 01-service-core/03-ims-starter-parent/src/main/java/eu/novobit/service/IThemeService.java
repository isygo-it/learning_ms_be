package eu.novobit.service;

import eu.novobit.com.rest.service.ICrudServiceMethods;
import eu.novobit.model.Theme;


/**
 * The interface Theme service.
 */
public interface IThemeService extends ICrudServiceMethods<Theme> {

    /**
     * Find theme by account code and domain code theme.
     *
     * @param accountCode the account code
     * @param domainCode  the domain code
     * @return the theme
     */
    Theme findThemeByAccountCodeAndDomainCode(String accountCode, String domainCode);

    /**
     * Update theme theme.
     *
     * @param theme the theme
     * @return the theme
     */
    Theme updateTheme(Theme theme);
}
