package eu.novobit.quartz.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import eu.novobit.config.AppProperties;
import eu.novobit.dto.data.MailMessageDto;
import eu.novobit.enumerations.IEnumAuth;
import eu.novobit.enumerations.IEnumPasswordStatus;
import eu.novobit.enumerations.IEnumTemplateName;
import eu.novobit.model.Account;
import eu.novobit.model.PasswordInfo;
import eu.novobit.repository.AccountRepository;
import eu.novobit.repository.PasswordInfoRepository;
import eu.novobit.service.IDomainService;
import eu.novobit.service.IMsgService;
import eu.novobit.types.EmailSubjects;
import eu.novobit.types.TemplateVariables;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.quartz.JobExecutionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * The type Password expired service.
 */
@Data
@Slf4j
@Service
public class PasswordExpiredService extends AbstractJobService {

    private final AppProperties appProperties;

    @Autowired
    private PasswordInfoRepository passwordInfoRepository;
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private IDomainService domainService;
    @Autowired
    private IMsgService msgService;

    @Override
    public void performJob(JobExecutionContext jobExecutionContext) {
        List<PasswordInfo> list = passwordInfoRepository.findByStatusAndAuthType(IEnumPasswordStatus.Types.VALID, IEnumAuth.Types.PWD);
        if (!CollectionUtils.isEmpty(list)) {
            list.forEach(passwordInfo -> {
                if (passwordInfo.remainingDays() <= appProperties.getPwdExpiredLessRemainigDays() && passwordInfo.remainingDays() > 0) {
                    Optional<Account> optional = accountRepository.findById(passwordInfo.getUserId());
                    if (optional.isPresent()) {
                        Account account = optional.get();
                        MailMessageDto mailMessageDto = null;
                        try {
                            mailMessageDto = MailMessageDto.builder()
                                    .subject(EmailSubjects.PASSWORD_WILL_EXPIRE_EMAIL_SUBJECT)
                                    .domain(account.getDomain())
                                    .toAddr(account.getEmail())
                                    .templateName(IEnumTemplateName.Types.PASSWORD_EXPIRE_TEMPLATE)
                                    .variables(MailMessageDto.getVariablesAsString(Map.of(
                                            //Common vars
                                            TemplateVariables.V_USER_NAME, account.getCode(),
                                            TemplateVariables.V_FULLNAME, account.getFullName(),
                                            TemplateVariables.V_DOMAIN_NAME, account.getDomain(),
                                            //Specific vars
                                            TemplateVariables.V_PWD_EXP_REMAINING_DAYS, passwordInfo.remainingDays().toString())))
                                    .build();
                        } catch (JsonProcessingException e) {
                            log.error("<Error>: send password expire email : {} ", e);
                        }
                        //Send the email message
                        msgService.sendMessage(account.getDomain(), mailMessageDto, appProperties.isSendAsyncEmail());
                    }
                }
            });
        }
    }
}
