package eu.novobit.service.impl;

import eu.novobit.api.MailMessageControllerApi;
import eu.novobit.com.camel.repository.ICamelRepository;
import eu.novobit.dto.data.MailMessageDto;
import eu.novobit.service.IMsgService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * The type Msg service.
 */
@Slf4j
@Service
public class MsgServiceImpl implements IMsgService {

    @Autowired
    private MailMessageControllerApi messageService;

    @Autowired
    private ICamelRepository camelRepository;

    @Override
    public void sendMessage(String senderDomainName, MailMessageDto mailMessage, boolean async) {
        if (async) {
            camelRepository.asyncSendBody(ICamelRepository.send_email_queue, mailMessage);
        } else {
            this.messageService.sendMail(senderDomainName, mailMessage.getTemplateName(), mailMessage);
        }
    }
}
