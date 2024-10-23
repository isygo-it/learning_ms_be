package eu.novobit.com.rest.service;

import eu.novobit.exception.JpaRepositoryNotDefinedException;
import eu.novobit.model.IIdEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * The interface Crud basics service.
 *
 * @param <T> the type parameter
 */
public interface ICrudBasicsService<T extends IIdEntity> {

    /**
     * Repository jpa repository.
     *
     * @return the jpa repository
     * @throws JpaRepositoryNotDefinedException the jpa repository not defined exception
     */
    JpaRepository repository() throws JpaRepositoryNotDefinedException;
}
