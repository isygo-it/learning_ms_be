package eu.novobit.service.impl;

import eu.novobit.annotation.CodeGenKms;
import eu.novobit.annotation.CodeGenLocal;
import eu.novobit.annotation.SrvRepo;
import eu.novobit.com.rest.service.AbstractCrudService;
import eu.novobit.model.JobTemplate;
import eu.novobit.remote.kms.KmsIncrementalKeyService;
import eu.novobit.repository.JobTemplateRepository;
import eu.novobit.service.IJobTemplateService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * The type Job template service.
 */
@Service
@Transactional
@CodeGenLocal(value = NextCodeService.class)
@CodeGenKms(value = KmsIncrementalKeyService.class)
@SrvRepo(value = JobTemplateRepository.class)
public class JobTemplateService extends AbstractCrudService<JobTemplate, JobTemplateRepository> implements IJobTemplateService {


}
