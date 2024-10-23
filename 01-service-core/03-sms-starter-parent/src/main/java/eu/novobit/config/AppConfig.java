package eu.novobit.config;

import eu.novobit.i18n.helper.LocaleResolver;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * The type App config.
 */
@Configuration
@EnableCaching //https://docs.spring.io/spring-framework/reference/integration/cache/annotations.html
@EnableConfigurationProperties(AppProperties.class)
public class AppConfig {

    /**
     * Gets local resolver.
     *
     * @return the local resolver
     */
    @Bean(name = "localResolver")
    public LocaleResolver getLocalResolver() {
        return new LocaleResolver();
    }

    /**
     * Gets extended message map.
     *
     * @return the extended message map
     */
    @Bean("extendedMessageMap")
    public Map<String, String> getExtendedMessageMap() {
        return new HashMap<>();
    }

    /**
     * Gets message map.
     *
     * @return the message map
     */
    @Bean("messageMap")
    public Map<String, String> getMessageMap() {
        return new HashMap<>();
    }
}
