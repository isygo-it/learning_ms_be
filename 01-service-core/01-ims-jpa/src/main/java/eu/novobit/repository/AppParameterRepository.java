package eu.novobit.repository;

import eu.novobit.model.AppParameter;
import org.springframework.stereotype.Repository;

import java.util.Optional;


/**
 * The interface App parameter repository.
 */
@Repository
public interface AppParameterRepository extends JpaPagingAndSortingSASRepository<AppParameter, Long> {

    /**
     * Find by domain ignore case and name ignore case optional.
     *
     * @param domain the domain
     * @param name   the name
     * @return the optional
     */
    Optional<AppParameter> findByDomainIgnoreCaseAndNameIgnoreCase(String domain, String name);
}
