package eu.novobit.controller;

import eu.novobit.annotation.CtrlDef;
import eu.novobit.api.MailMessageControllerApi;
import eu.novobit.com.rest.controller.MappedCrudController;
import eu.novobit.com.rest.controller.ResponseFactory;
import eu.novobit.dto.data.MailMessageDto;
import eu.novobit.dto.data.MailOptionsDto;
import eu.novobit.enumerations.IEnumTemplateName;
import eu.novobit.exception.handler.MmsExceptionHandler;
import eu.novobit.mapper.MailMessageMapper;
import eu.novobit.model.MailMessage;
import eu.novobit.service.ITemplateService;
import eu.novobit.service.impl.MailMessageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * The type Mail message controller.
 */
//http://localhost:8060/webjars/swagger-ui/index.html#/
//http://localhost:8060/messaging/mms/private/account
@Slf4j
@Validated
@RestController
@CtrlDef(handler = MmsExceptionHandler.class, mapper = MailMessageMapper.class, service = MailMessageService.class)
@RequestMapping(path = "/api/private/mail")
public class MailMessageController extends MappedCrudController<MailMessage, MailMessageDto, MailMessageDto, MailMessageService> implements MailMessageControllerApi {

    @Autowired
    private MailMessageService mailMessageService;

    @Autowired
    private MailMessageMapper mailMessageMapper;

    @Autowired
    private ITemplateService templateService;

    public ResponseEntity<?> sendMail(String senderDomainName, IEnumTemplateName.Types template, MailMessageDto mailMessage) {
        try {
            if (template != null) {
                String body = templateService.composeMessageBody(senderDomainName, template,
                        mailMessage.getVariablesAsMap(mailMessage.getVariables()));
                mailMessage.setBody(body);
            } else if (!StringUtils.hasText(mailMessage.getBody())) {
                mailMessage.setBody(mailMessage.getVariables());
            }
            mailMessageService.sendMail(senderDomainName,
                    mailMessageMapper.dtoToEntity(mailMessage)
                    , MailOptionsDto.builder()
                            .returnDelivered(mailMessage.isReturnDelivered())
                            .returnRead(mailMessage.isReturnRead())
                            .build()
                    , mailMessageService.multiPartFileToResource(senderDomainName, mailMessage.getResources())); //convert multipart file list to resources
            return ResponseFactory.ResponseOk();
        } catch (Throwable e) {
            log.error("<Error>: api exception : {} ", e);
            return getBackExceptionResponse(e);
        }
    }
}
