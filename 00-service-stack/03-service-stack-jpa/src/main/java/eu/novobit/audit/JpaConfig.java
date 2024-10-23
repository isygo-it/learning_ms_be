package eu.novobit.audit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

/**
 * The type Jpa config.
 */
@Configuration
@EnableJpaAuditing(auditorAwareRef = "auditorAware")
public class JpaConfig {

    @Autowired
    private IAuditAwereService<String> auditAwereService;

    /**
     * Auditor aware auditor aware.
     *
     * @return the auditor aware
     */
    @Bean
    public AuditorAware<String> auditorAware() {
        return new AuditorAwareImpl(auditAwereService);
    }
}
