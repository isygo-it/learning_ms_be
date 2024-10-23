package eu.novobit.service;

import eu.novobit.com.rest.service.ICrudServiceMethods;
import eu.novobit.model.StorageConfig;

/**
 * The interface Storage config service.
 */
public interface IStorageConfigService extends ICrudServiceMethods<StorageConfig> {

    /**
     * Find by domain ignore case storage config.
     *
     * @param domain the domain
     * @return the storage config
     */
    StorageConfig findByDomainIgnoreCase(String domain);
}
