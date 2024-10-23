package eu.novobit.audit;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.AuditorAware;

import java.util.Optional;

/**
 * The type Auditor aware.
 */
@Slf4j
public class AuditorAwareImpl implements AuditorAware<String> {

    @Autowired
    private IAuditAwereService auditAwereService;

    /**
     * Instantiates a new Auditor aware.
     *
     * @param auditAwereService the audit awere service
     */
    public AuditorAwareImpl(IAuditAwereService auditAwereService) {
        this.auditAwereService = auditAwereService;
    }

    @Override
    public Optional<String> getCurrentAuditor() {
        return auditAwereService.getCurrentAuditor();
    }
}
