package eu.novobit.repository;

import eu.novobit.annotation.IgnoreRepository;
import eu.novobit.model.ICodifiable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * The interface Jpa paging and sorting codifiable repository.
 *
 * @param <T> the type parameter
 * @param <I> the type parameter
 */
@IgnoreRepository
public interface JpaPagingAndSortingCodifiableRepository<T extends ICodifiable, I> extends JpaRepository<T, I> {


    /**
     * Find by code ignore case optional.
     *
     * @param code the code
     * @return the optional
     */
    Optional<T> findByCodeIgnoreCase(String code);
}
