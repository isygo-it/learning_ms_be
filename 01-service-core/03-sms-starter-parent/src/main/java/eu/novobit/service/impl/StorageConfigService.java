package eu.novobit.service.impl;

import eu.novobit.annotation.SrvRepo;
import eu.novobit.com.rest.service.AbstractCrudService;
import eu.novobit.constants.DomainConstants;
import eu.novobit.dto.exception.StorageConfigNotFoundException;
import eu.novobit.model.StorageConfig;
import eu.novobit.repository.StorageConfigRepository;
import eu.novobit.service.IStorageConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.Arrays;
import java.util.List;

/**
 * The type Storage config service.
 */
@Service
@Transactional
@SrvRepo(value = StorageConfigRepository.class)
public class StorageConfigService extends AbstractCrudService<StorageConfig, StorageConfigRepository> implements IStorageConfigService {

    @Autowired
    private StorageConfigRepository storageConfigRepository;

    @Override
    public StorageConfig findByDomainIgnoreCase(String domain) {
        List<StorageConfig> list = storageConfigRepository.findByDomainIgnoreCaseIn(Arrays.asList(DomainConstants.DEFAULT_DOMAIN_NAME, domain));
        if (CollectionUtils.isEmpty(list)) {
            list = storageConfigRepository.findByDomainIgnoreCaseIn(Arrays.asList(DomainConstants.DEFAULT_DOMAIN_NAME));
        }

        if (!CollectionUtils.isEmpty(list)) {
            return list.get(0);
        } else {
            throw new StorageConfigNotFoundException("for domain: " + domain);
        }
    }

    @Override
    public StorageConfig afterUpdate(StorageConfig object) {
        //TODO Remove client connection
        return super.afterUpdate(object);
    }
}
