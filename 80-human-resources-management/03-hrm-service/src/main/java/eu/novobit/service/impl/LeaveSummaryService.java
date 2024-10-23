package eu.novobit.service.impl;

import eu.novobit.annotation.CodeGenKms;
import eu.novobit.annotation.CodeGenLocal;
import eu.novobit.annotation.SrvRepo;
import eu.novobit.com.rest.service.AbstractCrudService;
import eu.novobit.config.AppProperties;
import eu.novobit.model.LeaveSummary;
import eu.novobit.remote.kms.KmsIncrementalKeyService;
import eu.novobit.repository.LeaveSummaryRepository;
import eu.novobit.service.ILeaveSummaryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * The type Leave summary service.
 */
@Slf4j
@Service
@Transactional
@CodeGenLocal(value = NextCodeService.class)
@CodeGenKms(value = KmsIncrementalKeyService.class)
@SrvRepo(value = LeaveSummaryRepository.class)
public class LeaveSummaryService extends AbstractCrudService<LeaveSummary, LeaveSummaryRepository> implements ILeaveSummaryService {

    private final AppProperties appProperties;


    /**
     * Instantiates a new Leave summary service.
     *
     * @param appProperties the app properties
     */
    public LeaveSummaryService(AppProperties appProperties) {
        this.appProperties = appProperties;
    }

}
