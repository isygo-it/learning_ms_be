package eu.novobit.service;

import eu.novobit.com.rest.service.ICrudServiceMethods;
import eu.novobit.model.RoleInfo;

/**
 * The interface Role info service.
 */
public interface IRoleInfoService extends ICrudServiceMethods<RoleInfo> {

    /**
     * Find by name role info.
     *
     * @param name the name
     * @return the role info
     */
    RoleInfo findByName(String name);
}
