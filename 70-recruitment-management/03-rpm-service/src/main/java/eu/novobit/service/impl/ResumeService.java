package eu.novobit.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import eu.novobit.annotation.CodeGenKms;
import eu.novobit.annotation.CodeGenLocal;
import eu.novobit.annotation.LnkFileService;
import eu.novobit.annotation.SrvRepo;
import eu.novobit.async.kafka.KafkaRegisterAccountProducer;
import eu.novobit.com.camel.repository.ICamelRepository;
import eu.novobit.com.rest.service.AbstractCrudFileImageService;
import eu.novobit.config.AppProperties;
import eu.novobit.constants.AppParameterConstants;
import eu.novobit.dto.AccountModelDto;
import eu.novobit.dto.LinkedFileDto;
import eu.novobit.dto.LinkedFileResponseDto;
import eu.novobit.dto.RequestContextDto;
import eu.novobit.dto.data.*;
import eu.novobit.dto.request.RegisterNewAccountDto;
import eu.novobit.enumerations.IEnumAccountOrigin;
import eu.novobit.enumerations.IEnumResumeStatType;
import eu.novobit.enumerations.IEnumTemplateName;
import eu.novobit.exception.ResumeNotFoundException;
import eu.novobit.model.*;
import eu.novobit.model.schema.SchemaColumnConstantName;
import eu.novobit.remote.dms.DmsLinkedFileService;
import eu.novobit.remote.ims.ImAccountService;
import eu.novobit.remote.ims.ImsAppParameterService;
import eu.novobit.remote.ims.ImsDomainService;
import eu.novobit.remote.kms.KmsIncrementalKeyService;
import eu.novobit.remote.quiz.QuizCandidateQuizService;
import eu.novobit.repository.AssoAccountResumeRepository;
import eu.novobit.repository.JobApplicationRepository;
import eu.novobit.repository.ResumeLinkedFileRepository;
import eu.novobit.repository.ResumeRepository;
import eu.novobit.service.IMsgService;
import eu.novobit.service.IResumeService;
import eu.novobit.types.EmailSubjects;
import eu.novobit.types.TemplateVariables;
import jakarta.ws.rs.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;

/**
 * The type Resume service.
 */
@Slf4j
@Service
@Transactional
@LnkFileService(DmsLinkedFileService.class)
@CodeGenLocal(value = NextCodeService.class)
@CodeGenKms(value = KmsIncrementalKeyService.class)
@SrvRepo(value = ResumeRepository.class)
public class ResumeService extends AbstractCrudFileImageService<Resume, ResumeRepository>
        implements IResumeService {

    private final AppProperties appProperties;

    @Autowired
    private DmsLinkedFileService dmsLinkedFileService;
    @Autowired
    private AssoAccountResumeRepository assoAccountResumeRepository;
    @Autowired
    private IMsgService msgService;
    @Autowired
    private ICamelRepository camelRepository;
    @Autowired
    private ResumeLinkedFileRepository resumeLinkedFileRepository;
    @Autowired
    private ImsAppParameterService appParameterService;
    @Autowired
    private ImsDomainService domainService;
    @Autowired
    private JobApplicationRepository jobApplicationRepository;
    @Autowired
    private KafkaRegisterAccountProducer kafkaRegisterAccountProducer;
    @Autowired
    private QuizCandidateQuizService quizCandidateQuizService;
    @Autowired
    private ImAccountService imAccountService;


    /**
     * Instantiates a new Resume service.
     *
     * @param appProperties the app properties
     */
    public ResumeService(AppProperties appProperties) {
        this.appProperties = appProperties;
    }

    @Override
    public AppNextCode initCodeGenerator() {
        return AppNextCode.builder()
                .domain(Resume.class.getSimpleName())
                .entity(Resume.class.getSimpleName())
                .attribute(SchemaColumnConstantName.C_CODE)
                .prefix("RRESU")
                .valueLength(6L)
                .value(1L)
                .build();
    }

    @Override
    public Resume beforeUpload(String domain, Resume resume, MultipartFile file) throws IOException {
        camelRepository.asyncSendBody(ICamelRepository.parse_resume_queue, ResumeParseDto.builder()
                .domain(resume.getDomain())
                .code(resume.getCode())
                .file(file)
                .build());
        return resume;
    }

    @Override
    protected String getUploadDirectory() {
        return this.appProperties.getUploadDirectory();
    }

    @Override
    public List<ResumeShareInfo> shareWithAccounts(Long id, String resumeOwner, List<AccountModelDto> account) throws JsonProcessingException {
        Resume resume = findById(id);
        List<ResumeShareInfo> shareInfos = new ArrayList<>();
        if (resume != null) {
            for (AccountModelDto acc : account) {
                boolean exists = false;
                ResumeShareInfo existingShareInfo = null;

                for (ResumeShareInfo shareInfo : resume.getResumeShareInfos()) {
                    if (shareInfo.getSharedWith().equals(acc.getCode())) {
                        exists = true;
                        existingShareInfo = shareInfo;
                        break;
                    }
                }

                if (!exists) {
                    ResumeShareInfo shareInfo = new ResumeShareInfo();
                    shareInfo.setSharedWith(acc.getCode());
                    shareInfos.add(shareInfo);
                    shareResumeNotification(resume, resumeOwner, acc);
                } else {
                    shareInfos.add(existingShareInfo);
                }
            }
            resume.getResumeShareInfos().clear();
            resume.getResumeShareInfos().addAll(shareInfos);
            return repository().save(resume).getResumeShareInfos();
        } else {
            // Handle the case where the Resume with the given ID doesn't exist
            throw new NotFoundException("Resume not found with ID: " + id);
        }
    }

    @Override
    public List<ResumeLinkedFile> uploadAdditionalFile(Long id, MultipartFile[] files) throws IOException {
        Resume resume = findById(id);

        // int crc16 = CRC16.calculate(buffer);
        //int crc32 = CRC32.calculate(buffer);
        if (resume != null) {
            for (MultipartFile file : files) {
                ResponseEntity<LinkedFileResponseDto> result = dmsLinkedFileService.upload(//RequestContextDto.builder().build(),
                        LinkedFileDto.builder()
                                .domain(resume.getDomain())
                                .path(File.separator + "resume" + File.separator + "additional")
                                .tags(resume.getTags())
                                .categoryNames(Arrays.asList("Resume"))
                                .file(file)
                                .build());
                ResumeLinkedFile resumeLinkedFile = ResumeLinkedFile.builder()
                        .code(result.getBody().getCode())
                        .originalFileName(file.getOriginalFilename())
                        .extension(FilenameUtils.getExtension(file.getOriginalFilename()))
                        .crc16(254147)
                        .crc32(365214)
                        .size(file.getSize())
                        .path("/resume/additional")
                        .mimetype(file.getContentType())
                        .version(1L)
                        .build();
                if (CollectionUtils.isEmpty(resume.getAdditionalFiles())) {
                    resume.setAdditionalFiles(new ArrayList<>());
                }
                resume.getAdditionalFiles().add(resumeLinkedFile);
                resume = this.update(resume);
            }
            return resume.getAdditionalFiles();
        } else {
            throw new ResumeNotFoundException("with id: " + id);
        }
    }

    @Override
    public boolean deleteAdditionalFile(Long id, String originalFileName) throws IOException {
        Resume resume = findById(id);
        if (resume != null) {

            ResumeLinkedFile file = resume.getAdditionalFiles().stream()
                    .filter((ResumeLinkedFile item) -> item.getOriginalFileName().equals(originalFileName)).findAny()
                    .orElse(null);
            if (file != null) {
                resume.getAdditionalFiles().removeIf(elm -> elm.getOriginalFileName().equals(originalFileName));
                dmsLinkedFileService.deleteFile(RequestContextDto.builder().build(), resume.getDomain(), file.getCode());
                this.update(resume);
                resumeLinkedFileRepository.deleteById(file.getId());
                return true;
            } else {
                throw new FileNotFoundException("with original File name: " + originalFileName);
            }
        } else {
            throw new ResumeNotFoundException("with id: " + id);
        }
    }

    private void shareResumeNotification(Resume resume, String resumeOwner, AccountModelDto account) throws JsonProcessingException {
        //Send email/notification to the board watchers
        String resumeUrl = "https://localhost:4004/apps/resumes/view/";
        ResponseEntity<String> result = appParameterService.getValueByDomainAndName(RequestContextDto.builder().build(),
                resume.getDomain(), AppParameterConstants.RESUME_VIEW_URL, true, "https://localhost:4004/apps/resumes/view/");
        if (result.hasBody() && StringUtils.hasText(result.getBody())) {
            resumeUrl = result.getBody() + resume.getId();
        }

        MailMessageDto mailMessageDto = MailMessageDto.builder()
                .domain(resume.getDomain())
                .subject(EmailSubjects.SHARED_RESUME)
                .toAddr(account.getEmail())
                .templateName(IEnumTemplateName.Types.RESUME_SHARED_TEMPLATE)
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
                TemplateVariables.V_DOMAIN_NAME, resume.getDomain(),
                //Specific vars
                TemplateVariables.V_CONDIDATE_FULLNAME, resume.getFullName(),
                TemplateVariables.V_SHARED_BY, resumeOwner,
                TemplateVariables.V_RESUME_VIEW_URL, resumeUrl)));

        msgService.sendMessage(resume.getDomain(), mailMessageDto, appProperties.isSendAsyncEmail());
    }


    public Resume getResumeByAccountCode(String accountCode) {
        Optional<AssoAccountResume> optional = assoAccountResumeRepository.findByAccountCodeIgnoreCase(accountCode);
        if (optional.isPresent()) {
            completeSkills(optional.get().getResume(), accountCode);
            return optional.get().getResume();
        }

        return null;
    }

    public String getResumeAccountCode(String resumeCode) {
        Optional<AssoAccountResume> optional = assoAccountResumeRepository.findByResume_Code(resumeCode);
        if (optional.isPresent()) {
            return optional.get().getAccountCode();
        }

        return null;
    }

    @Override
    public Resume createResumeForAccount(String accountCode, Resume resume) throws IOException {
        AssoAccountResume assoAccountResume = null;
        Optional<AssoAccountResume> optional = assoAccountResumeRepository.findByAccountCodeIgnoreCase(accountCode);
        if (!optional.isPresent()) {
            assoAccountResume = assoAccountResumeRepository.save(AssoAccountResume.builder()
                    .accountCode(accountCode)
                    .build());
        } else {
            assoAccountResume = optional.get();
        }

        if (assoAccountResume.getResume() != null) {
            resume.setId(optional.get().getResume().getId());
            return this.update(resume);
        }

        resume = this.create(resume);
        assoAccountResume.setResume(resume);
        assoAccountResumeRepository.save(assoAccountResume);
        return resume;
    }

    @Override
    public Resume beforeCreate(Resume resume) {
        if (resume.getDetails() == null) {
            resume.setDetails(ResumeDetails.builder().build());
        }
        return super.beforeCreate(resume);
    }

    @Override
    public Resume findResumeByCode(String code) {
        Optional<Resume> optional = repository().findByCode(code);
        if (optional.isPresent()) {
            Optional<AssoAccountResume> accountResume = assoAccountResumeRepository.findByResume_Code(code);
            if (accountResume.isPresent()) {
                completeSkills(optional.get(), accountResume.get().getAccountCode());
            }
            return optional.get();
        } else {
            throw new NotFoundException("Resume not found with CODE: " + code);
        }
    }

    @Override
    public ResumeGlobalStatDto getGlobalStatistics(IEnumResumeStatType.Types statType, RequestContextDto requestContext) {
        ResumeGlobalStatDto.ResumeGlobalStatDtoBuilder builder = ResumeGlobalStatDto.builder();
        int completedCount = 58;
        switch (statType) {
            case TOTAL_COUNT:
                builder.totalCount(getTotalResumeByDomain(requestContext));
                break;
            case UPLOADED_BY_ME_COUNT:
                builder.uploadedByMeCount(getUploadedByMe(requestContext));
                break;
            case CONFIRMED_COUNT:
                builder.confirmedCount(getcinfirmedCount(requestContext));
                break;
            case COMPLETED_COUNT:
                builder.completedCount(completedCount);
                break;
            case INTERVIEWED_COUNT:
                builder.interviewedCount(getInterviewdCount(requestContext));
                break;
            default:
                throw new IllegalArgumentException("Unknown stat type: " + statType);
        }

        return builder.build();
    }

    private Integer getTotalResumeByDomain(RequestContextDto requestContext) {
        Long nbreCreatedByDomain = repository().countByDomainAndCancelDateNull(requestContext.getSenderDomain());
        return nbreCreatedByDomain.intValue();
    }

    private Integer getUploadedByMe(RequestContextDto requestContext) {
        String createdBy = requestContext.getSenderUser() + '@' + requestContext.getSenderDomain();
        Long nbrecreatedByMe = repository().countByCreatedByAndCancelDateNull(createdBy);
        return nbrecreatedByMe.intValue();
    }

    private Integer getcinfirmedCount(RequestContextDto requestContext) {
        ResponseEntity<Integer> responseEntity = imAccountService.getConfirmedAccountNumberByResume(requestContext);
        return responseEntity.getBody();
    }

    private Integer getInterviewdCount(RequestContextDto requestContextDto) {
        Long nbreInterviewd = jobApplicationRepository.countOngoingGlobalJobApplication(requestContextDto.getSenderDomain());
        return nbreInterviewd.intValue();

    }

    @Override
    public ResumeStatDto getObjectStatistics(String code, RequestContextDto requestContextDto) {
        return ResumeStatDto.builder()
                .completion(75)
                .realizedTestsCount(getTotalInterviewed(code))
                .applicationsCount(getTotalJobApplicationByResume(code, requestContextDto))
                .ongoingApplicationsCount(getOngoingJobApplicationByResume(code, requestContextDto))
                .build();
    }

    private Integer getTotalInterviewed(String code) {
        Optional<AssoAccountResume> assoAccountResume = assoAccountResumeRepository.findByResume_Code(code);
        if (assoAccountResume.isPresent()) {
            ResponseEntity<Integer> responseEntity = quizCandidateQuizService.getCountRealizedTestByAccount(assoAccountResume.get().getAccountCode());
            return responseEntity.getBody();
        }
        return 0;
    }

    private Integer getTotalJobApplicationByResume(String code, RequestContextDto requestContextDto) {
        Long countAppliedOffersByResume = jobApplicationRepository.countByResumeCodeAndDomain(code, requestContextDto.getSenderDomain());
        return countAppliedOffersByResume.intValue();
    }

    private Integer getOngoingJobApplicationByResume(String code, RequestContextDto requestContextDto) {
        Long onGoingByResume = jobApplicationRepository.countOnGoingJobApplicationByResume(requestContextDto.getSenderDomain(), code);
        return onGoingByResume.intValue();
    }

    @Override
    public void afterDelete(Long id) {
        this.jobApplicationRepository.cancelJobApplication(id);
        super.afterDelete(id);
    }

    @Override
    public Resume afterCreate(Resume resume) {
        try {
            //Create linked account
            if (resume.getIsLinkedToUser()) {
                kafkaRegisterAccountProducer.sendMessage(RegisterNewAccountDto.builder()
                        .origin(new StringBuilder(IEnumAccountOrigin.Types.RESUME.name()).append("-").append(resume.getCode()).toString())
                        .domain(resume.getDomain())
                        .email(resume.getEmail())
                        .firstName(resume.getFirstName())
                        .lastName(resume.getLastName())
                        .phoneNumber(resume.getPhone())
                        .build());
            }
        } catch (IOException e) {
            log.error("<Error>: api exception : {} ", e);
        }
        return super.afterCreate(resume);
    }

    @Override
    public Resume afterFindById(Resume resume) {
        if (resume.getDetails() != null && !CollectionUtils.isEmpty(resume.getDetails().getSkills())) {
            Optional<AssoAccountResume> accountResume = assoAccountResumeRepository.findByResume_Code(resume.getCode());
            if (accountResume.isPresent()) {
                completeSkills(resume, accountResume.get().getAccountCode());
            }
        }
        return super.afterFindById(resume);
    }

    private void completeSkills(Resume resume, String accountCode) {
        resume.getDetails().getSkills().forEach(resumeSkills -> {
            ResponseEntity<List<QuizReportDto>> result = quizCandidateQuizService.getByCandidateAndTags(RequestContextDto.builder().build(),
                    accountCode,
                    Arrays.asList(resumeSkills.getName())
            );

            if (result.getStatusCode().is2xxSuccessful() && result.hasBody()) {
                resumeSkills.setScore(result.getBody().stream()
                        .mapToDouble(quizReportDto -> (quizReportDto.getScale() != 0 ? (quizReportDto.getScore() / quizReportDto.getScale()) : 0) * 100).average()
                        .orElse(Double.NaN));
            }
        });
    }
}

