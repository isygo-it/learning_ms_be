package eu.novobit.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import eu.novobit.annotation.CodeGenKms;
import eu.novobit.annotation.CodeGenLocal;
import eu.novobit.annotation.SrvRepo;
import eu.novobit.com.rest.service.AbstractCrudCodifiableService;
import eu.novobit.config.AppProperties;
import eu.novobit.constants.AppParameterConstants;
import eu.novobit.dto.AccountModelDto;
import eu.novobit.dto.LinkedFileDto;
import eu.novobit.dto.LinkedFileResponseDto;
import eu.novobit.dto.RequestContextDto;
import eu.novobit.dto.data.DomainDto;
import eu.novobit.dto.data.JobOfferGlobalStatDto;
import eu.novobit.dto.data.JobOfferStatDto;
import eu.novobit.dto.data.MailMessageDto;
import eu.novobit.encrypt.helper.CRC16;
import eu.novobit.encrypt.helper.CRC32;
import eu.novobit.enumerations.IEnumSharedStatType;
import eu.novobit.enumerations.IEnumTemplateName;
import eu.novobit.exception.ResumeNotFoundException;
import eu.novobit.model.*;
import eu.novobit.model.schema.SchemaColumnConstantName;
import eu.novobit.remote.dms.DmsLinkedFileService;
import eu.novobit.remote.ims.ImsAppParameterService;
import eu.novobit.remote.ims.ImsDomainService;
import eu.novobit.remote.kms.KmsIncrementalKeyService;
import eu.novobit.repository.JobApplicationRepository;
import eu.novobit.repository.JobLinkedFileRepository;
import eu.novobit.repository.JobOfferRepository;
import eu.novobit.repository.JobTemplateRepository;
import eu.novobit.service.IJobOfferService;
import eu.novobit.service.IMsgService;
import eu.novobit.types.EmailSubjects;
import eu.novobit.types.TemplateVariables;
import jakarta.ws.rs.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * The type Job offer service.
 */
@Slf4j
@Service
@Transactional
@CodeGenLocal(value = NextCodeService.class)
@CodeGenKms(value = KmsIncrementalKeyService.class)
@SrvRepo(value = JobOfferRepository.class)
public class JobOfferService extends AbstractCrudCodifiableService<JobOffer, JobOfferRepository>
        implements IJobOfferService {

    private final AppProperties appProperties;
    /**
     * The Job offer repository.
     */
    @Autowired
    JobOfferRepository jobOfferRepository;
    @Autowired
    private DmsLinkedFileService dmsLinkedFileService;
    @Autowired
    private JobLinkedFileRepository jobLinkedFileRepository;
    @Autowired
    private IMsgService msgService;
    @Autowired
    private ImsAppParameterService appParameterService;
    @Autowired
    private ImsDomainService domainService;
    @Autowired
    private JobTemplateRepository jobTemplateRepository;
    @Autowired
    private JobApplicationRepository jobApplicationRepository;

    /**
     * Instantiates a new Job offer service.
     *
     * @param appProperties the app properties
     */
    public JobOfferService(AppProperties appProperties) {
        this.appProperties = appProperties;
    }

    @Override
    public AppNextCode initCodeGenerator() {
        return AppNextCode.builder()
                .domain("default")
                .entity(JobOffer.class.getSimpleName())
                .attribute(SchemaColumnConstantName.C_CODE)
                .prefix("RJOB")
                .valueLength(6L)
                .value(1L)
                .increment(1)
                .build();
    }

    @Override
    public List<JobOffer> findJobOffersNotAssignedToResume(String resumeCode) {
        return jobOfferRepository.findJobOffersNotAssignedToResume(resumeCode);
    }

    @Override
    public List<JobLinkedFile> uploadAdditionalFile(Long id, MultipartFile[] files) throws IOException {
        JobOffer jobOffer = findById(id);
        if (jobOffer != null) {
            for (MultipartFile file : files) {
                ResponseEntity<LinkedFileResponseDto> result = dmsLinkedFileService.upload(//RequestContextDto.builder().build(),
                        LinkedFileDto.builder()
                                .domain(jobOffer.getDomain())
                                .path(File.separator + "job" + File.separator + "additional")
                                .tags(Arrays.asList("Job"))
                                .categoryNames(Arrays.asList("Job"))
                                .file(file)
                                .build());
                if (result.getStatusCode().is2xxSuccessful()) {
                    log.info("File uploaded successfully {}", file.getOriginalFilename());
                    JobLinkedFile jobLinkedFile = JobLinkedFile.builder()
                            .code(result.getBody().getCode())
                            .originalFileName(file.getOriginalFilename())
                            .extension(FilenameUtils.getExtension(file.getOriginalFilename()))
                            .crc16(CRC16.calculate(file.getBytes()))
                            .crc32(CRC32.calculate(file.getBytes()))
                            .size(file.getSize())
                            .path("/job/additional")
                            .mimetype(file.getContentType())
                            .version(1L)
                            .build();
                    if (CollectionUtils.isEmpty(jobOffer.getAdditionalFiles())) {
                        jobOffer.setAdditionalFiles(new ArrayList<>());
                    }
                    jobOffer.getAdditionalFiles().add(jobLinkedFile);
                }

                jobOffer = this.update(jobOffer);
            }
            return jobOffer.getAdditionalFiles();
        } else {
            throw new ResumeNotFoundException("with id: " + id);
        }
    }

    @Override
    public boolean deleteAdditionalFile(Long id, String originalFileName) throws IOException {
        JobOffer jobOffer = findById(id);
        if (jobOffer != null) {

            JobLinkedFile file = jobOffer.getAdditionalFiles().stream()
                    .filter((JobLinkedFile item) -> item.getOriginalFileName().equals(originalFileName)).findAny()
                    .orElse(null);
            if (file != null) {
                jobOffer.getAdditionalFiles().removeIf(elm -> elm.getOriginalFileName().equals(originalFileName));
                dmsLinkedFileService.deleteFile(RequestContextDto.builder().build(), jobOffer.getDomain(), file.getCode());
                this.update(jobOffer);
                jobLinkedFileRepository.deleteById(file.getId());
                return true;
            } else {
                throw new FileNotFoundException("with original File name: " + originalFileName);
            }
        } else {
            throw new ResumeNotFoundException("with id: " + id);
        }
    }

    @Override
    public ResponseEntity<Resource> download(String domain, String filename, Long version) throws IOException {
        return dmsLinkedFileService.download(RequestContextDto.builder().build(), domain, filename, version);
    }

    @Override
    public List<JobShareInfo> shareJob(Long id, String jobOwner, List<AccountModelDto> account) throws JsonProcessingException {
        JobOffer job = findById(id);
        List<JobShareInfo> shareInfos = new ArrayList<>();
        if (job != null) {
            for (AccountModelDto acc : account) {
                boolean exists = false;
                JobShareInfo existingShareInfo = null;

                for (JobShareInfo shareInfo : job.getJobShareInfos()) {
                    if (shareInfo.getSharedWith().equals(acc.getCode())) {
                        exists = true;
                        existingShareInfo = shareInfo;
                        break;
                    }
                }

                if (!exists) {
                    JobShareInfo shareInfo = new JobShareInfo();
                    shareInfo.setSharedWith(acc.getCode());
                    shareInfos.add(shareInfo);
                    shareJobNotification(job, jobOwner, acc);

                } else {
                    shareInfos.add(existingShareInfo);
                }
            }
            job.getJobShareInfos().clear();
            job.getJobShareInfos().addAll(shareInfos);
            return jobOfferRepository.save(job).getJobShareInfos();
        } else {
            throw new NotFoundException("Resume not found with ID: " + id);
        }
    }

    @Override
    public JobOfferGlobalStatDto getGlobalStatistics(IEnumSharedStatType.Types statType, RequestContextDto requestContextDto) {
        JobOfferGlobalStatDto.JobOfferGlobalStatDtoBuilder builder = JobOfferGlobalStatDto.builder();
        int confirmedCount = 182;

        switch (statType) {
            case TOTAL_COUNT:
                builder.totalCount(getTotalJobsBydomain(requestContextDto));
                break;
            case ACTIVE_COUNT:
                builder.activeCount(getTotalActiveJobsByDomain(requestContextDto));
                break;
            case CONFIRMED_COUNT:
                builder.confirmedCount(confirmedCount);
                break;
            case EXPIRED_COUNT:
                builder.expiredCount(getTotalExpiredJobsByDomain(requestContextDto));
                break;
            default:
                throw new IllegalArgumentException("Unknown stat type: " + statType);
        }

        return builder.build();
    }

    private Integer getTotalJobsBydomain(RequestContextDto requestContextDto) {
        Long totalJobsByDomain = repository().countByDomainAndCancelDateNull(requestContextDto.getSenderDomain());
        return totalJobsByDomain.intValue();
    }

    private Integer getTotalActiveJobsByDomain(RequestContextDto requestContextDto) {
        Long totalActiveJobsByDomain = repository().countByDomainAndDeadLine(requestContextDto.getSenderDomain());
        return totalActiveJobsByDomain.intValue();
    }

    private Integer getTotalExpiredJobsByDomain(RequestContextDto requestContextDto) {
        Long totalExpiredjobsByDomain = repository().countByDomainAndExpiredDeadLine(requestContextDto.getSenderDomain());
        return totalExpiredjobsByDomain.intValue();
    }

    @Override
    public JobOfferStatDto getObjectStatistics(String code, RequestContextDto requestContextDto) {
        return JobOfferStatDto.builder()
                .completion(75)
                .applicationCount(getNumberOfApplicationsByJob(code, requestContextDto))
                .selectedProfilesCount(26)
                .interviewedProfilesCount(getInterviewdProfilesCount(code, requestContextDto))
                .rejectedProfilesCount(8)
                .build();
    }

    private Integer getNumberOfApplicationsByJob(String code, RequestContextDto requestContextDto) {
        Long totalApplicationsByJob = jobApplicationRepository.countJobsByNumberOfApplications(code, requestContextDto.getSenderDomain());
        return totalApplicationsByJob.intValue();
    }

    private Integer getInterviewdProfilesCount(String code, RequestContextDto requestContextDto) {
        Long totalInterviewdProfiles = jobApplicationRepository.countJobsByNumberOfApplications(code, requestContextDto.getSenderDomain());
        return totalInterviewdProfiles.intValue();
    }

    private void shareJobNotification(JobOffer jobOffer, String jobOwner, AccountModelDto account) throws JsonProcessingException {
        //Send email/notification to the board watchers
        String jobUrl = "https://localhost:4002/apps/job/";
        ResponseEntity<String> result = appParameterService.getValueByDomainAndName(RequestContextDto.builder().build(),
                jobOffer.getDomain(), AppParameterConstants.JOB_VIEW_URL, true, "https://localhost:4002/apps/job/");
        if (result.hasBody() && StringUtils.hasText(result.getBody())) {
            jobUrl = result.getBody() + jobOffer.getId();
        }

        MailMessageDto mailMessageDto = MailMessageDto.builder()
                .domain(jobOffer.getDomain())
                .subject(EmailSubjects.SHARED_JOB)
                .toAddr(account.getEmail())
                .templateName(IEnumTemplateName.Types.JOB_OFFER_SHARED_TEMPLATE)
                .sent(true)
                .build();

        ResponseEntity<DomainDto> resultDomain = domainService.getByName(RequestContextDto.builder().build());
        DomainDto domain = null;
        if (resultDomain.hasBody() && resultDomain.getBody() != null) {
            domain = resultDomain.getBody();
        }

        mailMessageDto.setVariables(mailMessageDto.getVariablesAsString(Map.of(
                //Common vars
                TemplateVariables.V_USER_NAME, account.getCode(),
                TemplateVariables.V_FULLNAME, account.getFullName(),
                TemplateVariables.V_DOMAIN_NAME, jobOffer.getDomain(),
                //Specific vars
                TemplateVariables.V_JOB_TITLE, jobOffer.getTitle(),
                TemplateVariables.V_SHARED_BY, jobOffer.getOwner(),
                TemplateVariables.V_JOB_VIEW_URL, jobUrl)));

        msgService.sendMessage(jobOffer.getDomain(), mailMessageDto, appProperties.isSendAsyncEmail());
    }

    @Override
    public JobOffer afterCreate(JobOffer jobOffer) {
        if (jobOffer.getDetails() == null) {
            jobOffer.setDetails(new JobDetails());
        }
        return super.afterCreate(jobOffer);
    }

    @Override
    public void afterDelete(Long id) {
        jobTemplateRepository.deleteJobTemplateByJobOffer_Id(id);
        super.afterDelete(id);
    }
}
