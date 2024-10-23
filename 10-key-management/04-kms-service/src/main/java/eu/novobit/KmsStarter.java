package eu.novobit;

import eu.novobit.annotation.IgnoreRepository;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * The type Kms starter.
 */
//http://localhost:55403/swagger-ui/index.html
@SpringBootApplication
@EnableFeignClients
@EnableConfigurationProperties
@ConfigurationPropertiesScan
@EntityScan(basePackages = {"eu.novobit.model"})
@EnableJpaRepositories(basePackages = {"eu.novobit.repository"}
        , excludeFilters = {@ComponentScan.Filter(IgnoreRepository.class)})
@OpenAPIDefinition(info =
@Info(title = "Key management API", version = "1.0", description = "Documentation Key management API v1.0")
)
@PropertySource(encoding = "UTF-8", value = {"classpath:i18n/messages.properties"})
public class KmsStarter extends ServiceStarter {

    /**
     * The entry point of application.
     *
     * @param args the input arguments
     */
    public static void main(String[] args) {
        SpringApplication.run(KmsStarter.class, args);
    }
}
