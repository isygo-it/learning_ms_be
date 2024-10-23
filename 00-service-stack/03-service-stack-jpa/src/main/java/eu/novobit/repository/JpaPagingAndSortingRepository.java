package eu.novobit.repository;

import eu.novobit.annotation.IgnoreRepository;
import eu.novobit.model.IIdEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * The interface Jpa paging and sorting repository.
 *
 * @param <T> the type parameter
 * @param <I> the type parameter
 */
@IgnoreRepository
public interface JpaPagingAndSortingRepository<T extends IIdEntity, I> extends JpaRepository<T, I> {

}
