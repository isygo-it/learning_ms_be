package eu.novobit.service.impl;

import eu.novobit.annotation.CodeGenKms;
import eu.novobit.annotation.CodeGenLocal;
import eu.novobit.annotation.SrvRepo;
import eu.novobit.com.rest.service.AbstractCrudBasicsService;
import eu.novobit.model.Timeline;
import eu.novobit.remote.kms.KmsIncrementalKeyService;
import eu.novobit.repository.TimelineRepository;
import eu.novobit.service.ITimeLineService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * The type Timeline service.
 */
@Service
@Transactional
@CodeGenLocal(value = NextCodeService.class)
@CodeGenKms(value = KmsIncrementalKeyService.class)
@SrvRepo(value = TimelineRepository.class)
public class TimelineService extends AbstractCrudBasicsService<Timeline, TimelineRepository> implements ITimeLineService {

    @Override
    public List<Timeline> getTimelineByDomainAndCode(String code, String domain) {
        return repository().findAllByDomainIgnoreCaseAndCodeIgnoreCase(domain, code);
    }
}
