package eu.novobit.service;

import eu.novobit.com.rest.service.ICrudServiceMethods;
import eu.novobit.enumerations.IEnumBinaryStatus;
import eu.novobit.model.Account;
import eu.novobit.model.KmsDomain;

/**
 * The interface Domain service.
 */
public interface IDomainService extends ICrudServiceMethods<KmsDomain> {

    /**
     * Check domain if exists kms domain.
     *
     * @param domainName        the domain name
     * @param domainUrl         the domain url
     * @param createIfNotExists the create if not exists
     * @return the kms domain
     */
    KmsDomain checkDomainIfExists(String domainName, String domainUrl, boolean createIfNotExists);

    /**
     * Find by name ignore case kms domain.
     *
     * @param domainName the domain name
     * @return the kms domain
     */
    KmsDomain findByNameIgnoreCase(String domainName);

    /**
     * Check account if exists account.
     *
     * @param domainName        the domain name
     * @param domainUrl         the domain url
     * @param email             the email
     * @param userName          the user name
     * @param fullName          the full name
     * @param createIfNotExists the create if not exists
     * @return the account
     */
    Account checkAccountIfExists(String domainName, String domainUrl, String email, String userName, String fullName, boolean createIfNotExists);

    /**
     * Check if exists boolean.
     *
     * @param kmsDomain         the kms domain
     * @param createIfNotExists the create if not exists
     * @return the boolean
     */
    boolean checkIfExists(KmsDomain kmsDomain, boolean createIfNotExists);

    /**
     * Update admin status kms domain.
     *
     * @param domain    the domain
     * @param newStatus the new status
     * @return the kms domain
     */
    KmsDomain updateAdminStatus(String domain, IEnumBinaryStatus.Types newStatus);

    /**
     * Is enabled boolean.
     *
     * @param domain the domain
     * @return the boolean
     */
    boolean isEnabled(String domain);
}
