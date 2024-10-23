package eu.novobit.service;

import eu.novobit.com.rest.service.ICrudImageService;
import eu.novobit.com.rest.service.ICrudServiceMethods;
import eu.novobit.dto.RequestContextDto;
import eu.novobit.dto.data.AccountGlobalStatDto;
import eu.novobit.dto.data.AccountStatDto;
import eu.novobit.dto.data.ApplicationDto;
import eu.novobit.dto.data.MinAccountDto;
import eu.novobit.dto.request.AccountAuthTypeRequest;
import eu.novobit.dto.response.UserContext;
import eu.novobit.enumerations.IEnumBinaryStatus;
import eu.novobit.enumerations.IEnumLanguage;
import eu.novobit.enumerations.IEnumSharedStatType;
import eu.novobit.exception.AccountNotFoundException;
import eu.novobit.model.Account;
import eu.novobit.model.Application;
import eu.novobit.model.ConnectionTracking;
import jakarta.transaction.NotSupportedException;

import java.util.List;

/**
 * The interface Account service.
 */
public interface IAccountService extends ICrudServiceMethods<Account>, ICrudImageService<Account> {

    /**
     * Find by domain and user name account.
     *
     * @param domain   the domain
     * @param userName the user name
     * @return the account
     */
    Account findByDomainAndUserName(String domain, String userName);

    /**
     * Find distinct allowed tools by domain and user name list.
     *
     * @param domain   the domain
     * @param userName the user name
     * @return the list
     */
    List<Application> findDistinctAllowedToolsByDomainAndUserName(String domain, String userName);

    /**
     * Update account admin status account.
     *
     * @param id        the id
     * @param newStatus the new status
     * @return the account
     */
    Account updateAccountAdminStatus(Long id, IEnumBinaryStatus.Types newStatus);

    /**
     * Update account is admin account.
     *
     * @param id        the id
     * @param newStatus the new status
     * @return the account
     */
    Account updateAccountIsAdmin(Long id, boolean newStatus);

    /**
     * Find emails by domain list.
     *
     * @param domain the domain
     * @return the list
     */
    List<String> findEmailsByDomain(String domain);

    /**
     * Build allowed tools list.
     *
     * @param account the account
     * @param token   the token
     * @return the list
     */
    List<ApplicationDto> buildAllowedTools(Account account, String token);


    /**
     * Gets min info by domain.
     *
     * @param domain the domain
     * @return the min info by domain
     * @throws NotSupportedException the not supported exception
     */
    List<MinAccountDto> getMinInfoByDomain(String domain) throws NotSupportedException;


    /**
     * Gets authentication type.
     *
     * @param accountAuthTypeRequest the switch auth type request
     * @return the authentication type
     * @throws AccountNotFoundException the account not found exception
     */
    UserContext getAuthenticationType(AccountAuthTypeRequest accountAuthTypeRequest) throws AccountNotFoundException;

    /**
     * Switch auth type boolean.
     *
     * @param accountAuthTypeRequest the switch auth type request
     * @return the boolean
     * @throws AccountNotFoundException the account not found exception
     */
    boolean switchAuthType(AccountAuthTypeRequest accountAuthTypeRequest) throws AccountNotFoundException;

    /**
     * Update language account.
     *
     * @param id       the id
     * @param language the language
     * @return the account
     */
    Account updateLanguage(Long id, IEnumLanguage.Types language);

    /**
     * Gets by domain.
     *
     * @param domain the domain
     * @return the by domain
     */
    List<Account> getByDomain(String domain);

    /**
     * Check if application allowed boolean.
     *
     * @param domain      the domain
     * @param userName    the user name
     * @param application the application
     * @return the boolean
     */
    boolean checkIfApplicationAllowed(String domain, String userName, String application);

    /**
     * Track user connections.
     *
     * @param domain             the domain
     * @param userName           the user name
     * @param connectionTracking the connection tracking
     */
    void trackUserConnections(String domain, String userName, ConnectionTracking connectionTracking);

    /**
     * Chat accounts by domain list.
     *
     * @param domain the domain
     * @return the list
     */
    List<Account> chatAccountsByDomain(String domain);

    /**
     * Resend creation email boolean.
     *
     * @param id the id
     * @return the boolean
     */
    boolean resendCreationEmail(Long id);

    /**
     * Gets global statistics.
     *
     * @return the global statistics
     */
    AccountGlobalStatDto getGlobalStatistics(IEnumSharedStatType.Types statType, RequestContextDto requestContext);

    /**
     * Gets object statistics.
     *
     * @param code the code
     * @return the object statistics
     */
    AccountStatDto getObjectStatistics(String code);

    /**
     * Gets total connected Account.
     */
    Integer getConfirmedAccountNumberByResume();

    Integer getConfirmedAccountNumberByEmployee();
}
