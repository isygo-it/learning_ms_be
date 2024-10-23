package eu.novobit.repository;

import eu.novobit.annotation.IgnoreRepository;
import eu.novobit.model.ISASEntity;
import eu.novobit.model.extendable.NextCodeModel;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

/**
 * The interface Next code repository.
 *
 * @param <T>  the type parameter
 * @param <ID> the type parameter
 */
@IgnoreRepository
public interface NextCodeRepository<T extends NextCodeModel & ISASEntity, ID> extends JpaPagingAndSortingSASRepository<T, ID> {

    /**
     * Find by entity optional.
     *
     * @param entity the entity
     * @return the optional
     */
    Optional<T> findByEntity(String entity);

    /**
     * Find by domain ignore case and entity and attribute optional.
     *
     * @param domain    the domain
     * @param entity    the entity
     * @param attribute the attribute
     * @return the optional
     */
    Optional<T> findByDomainIgnoreCaseAndEntityAndAttribute(String domain, String entity, String attribute);

    /**
     * Increment.
     *
     * @param domain    the domain
     * @param entity    the entity
     * @param increment the increment
     */
    @Modifying
    @Query("update AppNextCode set value = value + :increment where domain = :domain and entity = :entity")
    void increment(@Param("domain") String domain,
                   @Param("entity") String entity,
                   @Param("increment") Integer increment);
}
