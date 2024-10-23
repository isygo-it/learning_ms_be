package eu.novobit.async.kafka;

import eu.novobit.dto.data.MailMessageDto;
import eu.novobit.helper.JsonHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.io.IOException;

/**
 * The type Kafka email sender producer.
 */
@Slf4j
@Service
public class KafkaEmailSenderProducer {

    @Value("${spring.kafka.topics.send-email}")
    private String send_email_topic;

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    /**
     * Send message.
     *
     * @param message the message
     * @throws IOException the io exception
     */
    public void sendMessage(MailMessageDto message) throws IOException {
        log.info("Message sent to {} -> {}", send_email_topic, message);
        kafkaTemplate.send(send_email_topic, JsonHelper.toJson(message));
    }
}
