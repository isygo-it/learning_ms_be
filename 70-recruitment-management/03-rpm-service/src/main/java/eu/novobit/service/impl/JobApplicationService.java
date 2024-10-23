package eu.novobit.service.impl;

import eu.novobit.annotation.CodeGenKms;
import eu.novobit.annotation.CodeGenLocal;
import eu.novobit.annotation.SrvRepo;
import eu.novobit.com.rest.service.AbstractCrudCodifiableService;
import eu.novobit.model.AppNextCode;
import eu.novobit.model.JobApplication;
import eu.novobit.model.Resume;
import eu.novobit.model.schema.SchemaColumnConstantName;
import eu.novobit.remote.kms.KmsIncrementalKeyService;
import eu.novobit.repository.JobApplicationRepository;
import eu.novobit.repository.JobOfferRepository;
import eu.novobit.repository.ResumeRepository;
import eu.novobit.service.IJobApplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * The type Job application service.
 */
@Service
@Transactional
@CodeGenLocal(value = NextCodeService.class)
@CodeGenKms(value = KmsIncrementalKeyService.class)
@SrvRepo(value = JobApplicationRepository.class)
public class JobApplicationService extends AbstractCrudCodifiableService<JobApplication, JobApplicationRepository> implements IJobApplicationService {

    @Autowired
    private ResumeRepository resumeRepository;

    @Autowired
    private JobOfferRepository jobOfferRepository;

    @Override
    public JobApplication beforeCreate(JobApplication jobApplication) {
        Optional<Resume> resume = resumeRepository.findByCodeIgnoreCase(jobApplication.getResume().getCode());
        jobApplication.setResume(resume.get());
        jobApplication.setDomain(resume.get().getDomain());
        jobApplication.setJobOffer(jobOfferRepository.findByCodeIgnoreCase(jobApplication.getJobOffer().getCode()).get());
        //init job application status
        return super.beforeCreate(jobApplication);
    }

    @Override
    public JobApplication beforeUpdate(JobApplication jobApplication) {
        if (jobApplication.getResume().getCode() != null) {
            jobApplication.setResume(resumeRepository.findByCodeIgnoreCase(jobApplication.getResume().getCode()).get());
        }
        if (jobApplication.getJobOffer().getCode() != null) {
            jobApplication.setJobOffer(jobOfferRepository.findByCodeIgnoreCase(jobApplication.getJobOffer().getCode()).get());
        }
        return super.beforeUpdate(jobApplication);
    }

    @Override
    public AppNextCode initCodeGenerator() {
        return AppNextCode.builder()
                .domain("default")
                .entity(JobApplication.class.getSimpleName())
                .attribute(SchemaColumnConstantName.C_CODE)
                .prefix("RJAPP")
                .valueLength(6L)
                .value(1L)
                .increment(1)
                .build();
    }
}
