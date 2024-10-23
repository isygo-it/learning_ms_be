package eu.novobit.factory;

import eu.novobit.constants.DomainConstants;
import eu.novobit.model.SenderConfig;
import eu.novobit.repository.SenderConfigRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.*;

/**
 * The type Sender factory.
 */
@Slf4j
@Component
public class SenderFactory {

    private final Map<String, JavaMailSenderImpl> senderMap = new HashMap<>();

    @Autowired
    private SenderConfigRepository senderConfigRepository;
    @Qualifier("defaultSender")
    @Autowired
    private JavaMailSender defaultSender;

    /**
     * Remove sender.
     *
     * @param domain the domain
     */
    public void removeSender(String domain) {
        senderMap.remove(domain);
    }

    /**
     * Gets sender.
     *
     * @param domain the domain
     * @return the sender
     */
    public JavaMailSenderImpl getSender(String domain) {
        // get data from table config
        if (senderMap.containsKey(domain)) {
            return senderMap.get(domain);
        }

        List<SenderConfig> list = senderConfigRepository.findByDomainIgnoreCaseIn(Arrays.asList(DomainConstants.DEFAULT_DOMAIN_NAME, domain));
        if (!CollectionUtils.isEmpty(list)) {
            SenderConfig senderConfig = list.get(0);
            JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
            mailSender.setHost(senderConfig.getHost());
            int port = Integer.parseInt(senderConfig.getPort());
            mailSender.setPort(port);
            mailSender.setUsername(senderConfig.getUsername());
            mailSender.setPassword(senderConfig.getPassword());
            Properties props = mailSender.getJavaMailProperties();
            props.put("mail.transport.protocol", senderConfig.getTransportProtocol());
            props.put("mail.smtp.auth", senderConfig.getSmtpAuth());
            props.put("mail.smtp.starttls.enable", senderConfig.getSmtpStarttlsRequired());
            props.put("mail.debug", senderConfig.getDebug());

            senderMap.put(domain, mailSender);
            return mailSender;
        } else {
            return (JavaMailSenderImpl) defaultSender;
        }
    }
}
