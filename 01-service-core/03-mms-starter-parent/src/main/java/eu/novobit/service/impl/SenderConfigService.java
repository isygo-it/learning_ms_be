package eu.novobit.service.impl;

import eu.novobit.annotation.SrvRepo;
import eu.novobit.com.rest.service.AbstractCrudService;
import eu.novobit.model.SenderConfig;
import eu.novobit.repository.SenderConfigRepository;
import eu.novobit.service.ISenderConfigService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * The type Sender config service.
 */
@Service
@Transactional
@SrvRepo(value = SenderConfigRepository.class)
public class SenderConfigService extends AbstractCrudService<SenderConfig, SenderConfigRepository> implements ISenderConfigService {

}
