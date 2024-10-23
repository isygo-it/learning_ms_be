package eu.novobit.async.kafka;

import eu.novobit.dto.request.RegisterNewAccountDto;
import eu.novobit.helper.JsonHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.io.IOException;

/**
 * The type Kafka register account producer.
 */
@Slf4j
@Service
public class KafkaRegisterAccountProducer {

    @Value("${spring.kafka.topics.register-account}")
    private String register_account_topic;

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    /**
     * Send message.
     *
     * @param message the message
     * @throws IOException the io exception
     */
    public void sendMessage(RegisterNewAccountDto message) throws IOException {
        log.info("Message sent to {} -> {}", register_account_topic, message);
        kafkaTemplate.send(register_account_topic, JsonHelper.toJson(message));
    }
}
