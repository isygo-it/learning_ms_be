package eu.novobit.repository;

import eu.novobit.enumerations.IEnumRequest;
import eu.novobit.model.ApiPermission;

import java.util.Optional;

/**
 * The interface Api permission repository.
 */
public interface ApiPermissionRepository extends JpaPagingAndSortingRepository<ApiPermission, Long> {

    /**
     * Find by service name and object and method and rq type and path optional.
     *
     * @param serviceName the service name
     * @param object      the object
     * @param method      the method
     * @param rqType      the rq type
     * @param path        the path
     * @return the optional
     */
    Optional<ApiPermission> findByServiceNameAndObjectAndMethodAndRqTypeAndPath(String serviceName, String object, String method, IEnumRequest.Types rqType, String path);
}
