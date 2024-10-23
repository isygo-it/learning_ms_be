package eu.novobit.repository;

import eu.novobit.enumerations.IEnumAuth;
import eu.novobit.model.PasswordConfig;

import java.util.Optional;

/**
 * The interface Password config repository.
 */
public interface PasswordConfigRepository extends JpaPagingAndSortingSASCodifiableRepository<PasswordConfig, Long> {

    /**
     * Find by domain ignore case and type optional.
     *
     * @param domain the domain
     * @param type   the type
     * @return the optional
     */
    Optional<PasswordConfig> findByDomainIgnoreCaseAndType(String domain, IEnumAuth.Types type);
}
