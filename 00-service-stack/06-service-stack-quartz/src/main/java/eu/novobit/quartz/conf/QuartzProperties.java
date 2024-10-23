package eu.novobit.quartz.conf;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * The type Quartz properties.
 */
@Data
@ConfigurationProperties(prefix = "spring.quartz")
public class QuartzProperties {

}
