package eu.novobit.service.impl;

import eu.novobit.annotation.SrvRepo;
import eu.novobit.com.rest.service.AbstractCrudService;
import eu.novobit.config.AppProperties;
import eu.novobit.dto.data.MailOptionsDto;
import eu.novobit.exception.StoreFileException;
import eu.novobit.factory.SenderFactory;
import eu.novobit.model.MailMessage;
import eu.novobit.repository.MailMessageRepository;
import eu.novobit.service.IMailMessageService;
import jakarta.activation.DataHandler;
import jakarta.activation.DataSource;
import jakarta.activation.FileDataSource;
import jakarta.mail.BodyPart;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeBodyPart;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.internet.MimeMultipart;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * The type Mail message service.
 */
@Service
@Transactional
@Slf4j
@SrvRepo(value = MailMessageRepository.class)
public class MailMessageService extends AbstractCrudService<MailMessage, MailMessageRepository> implements IMailMessageService {

    private final AppProperties appProperties;

    @Autowired
    private MailMessageRepository mailMessageRepository;
    @Autowired
    private SenderFactory senderFactory;

    /**
     * Instantiates a new Mail message service.
     *
     * @param appProperties the app properties
     */
    public MailMessageService(AppProperties appProperties) {
        this.appProperties = appProperties;
    }

    @Override
    public boolean sendMail(JavaMailSenderImpl mailSender, MailMessage mailMessageData, boolean returnDelivered, boolean returnRead, Map<String, File> resources) {
        try {
            MimeMessage mail = mailSender.createMimeMessage();
            mail.setHeader("Content-Type", IMailMessageService.encodingOptions);

            if (returnRead) {
                mail.setHeader("Disposition-Notification-To", mailSender.getUsername());
            }

            if (returnDelivered) {
                mail.setHeader("Return-Receipt-To", mailSender.getUsername());
            }

            MimeMessageHelper mailMessage = new MimeMessageHelper(mail, true, IMailMessageService.encodingOptions);
            // FROM
            mailMessage.setFrom(mailSender.getUsername());

            // TO
            if (StringUtils.hasText(mailMessageData.getToAddr())) {
                mailMessage.setTo(mailMessageData.getToAddr().split(","));
            }

            // TO BCC
            if (StringUtils.hasText(mailMessageData.getCcAddr())) {
                mailMessage.setBcc(mailMessageData.getCcAddr().split(","));
            }

            // TO CC
            if (StringUtils.hasText(mailMessageData.getBccAddr())) {
                mailMessage.setCc(mailMessageData.getBccAddr().split(","));
            }

            // SUBJECT
            mail.setSubject(mailMessageData.getSubject(), "UTF-8");

            if (resources != null && resources.size() > 0) {
                MimeMultipart multipart = new MimeMultipart("related");
                BodyPart messageBodyPart = new MimeBodyPart();
                messageBodyPart.setContent(mailMessageData.getBody(), "text/html");
                multipart.addBodyPart(messageBodyPart);

                for (String key : resources.keySet()) {
                    DataSource fds = new FileDataSource(resources.get(key));

                    messageBodyPart = new MimeBodyPart();
                    messageBodyPart.setDataHandler(new DataHandler(fds));
                    messageBodyPart.setHeader("Content-ID", "<" + key + ">");

                    // Set the file name as the attachment's name
                    messageBodyPart.setFileName(key);  // Add this line

                    // add image to the multipart
                    multipart.addBodyPart(messageBodyPart);
                }

                mail.setContent(multipart);
            } else {
                // BODY
                mail.setContent(mailMessageData.getBody(), IMailMessageService.encodingOptions);
            }

            mailSender.send(mail);
        } catch (MailException e) {
            log.error("<Error>: {} ", e);
            return false;
        } catch (MessagingException e) {
            log.error("<Error>: {} ", e);
            return false;
        }

        return true;
    }

    @Override
    public boolean sendMail(String senderDomainName, MailMessage mailMessageData, MailOptionsDto options, Map<String, File> resources) {
        JavaMailSenderImpl mailSender = senderFactory.getSender(senderDomainName);
        boolean result = this.sendMail(mailSender,
                mailMessageData
                , options.isReturnDelivered()
                , options.isReturnRead()
                , resources);
        mailMessageRepository.save(mailMessageData);
        return result;
    }

    @Override
    public Map<String, File> multiPartFileToResource(String senderDomainName, List<MultipartFile> resources) {
        if (CollectionUtils.isEmpty(resources)) {
            return null;
        }

        return resources.stream().map(multipartFile -> {
            File file = new File(appProperties.getUploadDirectory() + File.separator + senderDomainName + File.separator + "resources" + File.separator + multipartFile.getOriginalFilename());
            try {
                multipartFile.transferTo(file);
                return multipartFile;
            } catch (IOException e) {
                throw new StoreFileException("Resource to file");
            }
        }).collect(Collectors.toMap(multipartFile -> multipartFile.getOriginalFilename(), multipartFile
                -> new File(appProperties.getUploadDirectory() + File.separator + senderDomainName + File.separator + "resources" + File.separator + multipartFile.getOriginalFilename())));
    }

}
