package eu.novobit.repository;

import eu.novobit.model.RoleInfo;

import java.util.Optional;

/**
 * The interface Role info repository.
 */
public interface RoleInfoRepository extends JpaPagingAndSortingCodifiableRepository<RoleInfo, Long> {

    /**
     * Find by name optional.
     *
     * @param name the name
     * @return the optional
     */
    Optional<RoleInfo> findByName(String name);
}
