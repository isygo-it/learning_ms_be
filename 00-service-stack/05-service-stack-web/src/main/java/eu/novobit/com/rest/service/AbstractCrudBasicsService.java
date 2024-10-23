package eu.novobit.com.rest.service;

import eu.novobit.annotation.SrvRepo;
import eu.novobit.app.ApplicationContextService;
import eu.novobit.exception.JpaRepositoryNotDefinedException;
import eu.novobit.model.IIdEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * The type Abstract crud basics service.
 *
 * @param <T> the type parameter
 * @param <R> the type parameter
 */
@Slf4j
public abstract class AbstractCrudBasicsService<T extends IIdEntity, R extends JpaRepository>
        implements ICrudBasicsService<T> {

    @Autowired
    private ApplicationContextService applicationContextServie;

    private R repository;

    @Override
    public final R repository() throws JpaRepositoryNotDefinedException {
        if (this.repository == null) {
            SrvRepo controllerDefinition = this.getClass().getAnnotation(SrvRepo.class);
            if (controllerDefinition != null) {
                this.repository = (R) applicationContextServie.getBean(controllerDefinition.value());
                if (this.repository == null) {
                    log.error("<Error>: bean {} not found", controllerDefinition.value().getSimpleName());
                    throw new JpaRepositoryNotDefinedException("JpaRepository " + controllerDefinition.value().getSimpleName() + " not found");
                }
            } else {
                log.error("<Error>: Repository bean not defined for {}", this.getClass().getSimpleName());
                throw new JpaRepositoryNotDefinedException("JpaRepository");
            }
        }

        return this.repository;
    }
}
