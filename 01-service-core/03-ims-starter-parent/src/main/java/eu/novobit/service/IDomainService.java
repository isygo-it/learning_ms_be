package eu.novobit.service;

import eu.novobit.com.rest.service.ICrudImageService;
import eu.novobit.com.rest.service.ICrudServiceMethods;
import eu.novobit.enumerations.IEnumBinaryStatus;
import eu.novobit.model.Domain;

import java.util.List;

/**
 * The interface Domain service.
 */
public interface IDomainService extends ICrudServiceMethods<Domain>, ICrudImageService<Domain> {

    /**
     * Gets all domain names.
     *
     * @param domain the domain
     * @return the all domain names
     */
    List<String> getAllDomainNames(String domain);

    /**
     * Update admin status domain.
     *
     * @param id        the id
     * @param newStatus the new status
     * @return the domain
     */
    Domain updateAdminStatus(Long id, IEnumBinaryStatus.Types newStatus);

    /**
     * Gets image.
     *
     * @param domainName the domain name
     * @return the image
     */
    String getImage(String domainName);

    /**
     * Find domain idby domain name long.
     *
     * @param name the name
     * @return the long
     */
    Long findDomainIdbyDomainName(String name);

    /**
     * Find by name domain.
     *
     * @param name the name
     * @return the domain
     */
    Domain findByName(String name);

    /**
     * Is enabled boolean.
     *
     * @param domain the domain
     * @return the boolean
     */
    boolean isEnabled(String domain);
}
