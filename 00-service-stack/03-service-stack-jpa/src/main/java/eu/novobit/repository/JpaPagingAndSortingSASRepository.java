package eu.novobit.repository;

import eu.novobit.annotation.IgnoreRepository;
import eu.novobit.model.ISASEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * The interface Jpa paging and sorting sas repository.
 *
 * @param <T> the type parameter
 * @param <I> the type parameter
 */
@IgnoreRepository
public interface JpaPagingAndSortingSASRepository<T extends ISASEntity, I> extends JpaRepository<T, I> {

    /**
     * Find by domain ignore case list.
     *
     * @param domain the domain
     * @return the list
     */
    List<T> findByDomainIgnoreCaseIn(List<String> domain);

    /**
     * Find by domain ignore case page.
     *
     * @param domain   the domain
     * @param pageable the pageable
     * @return the page
     */
    Page<T> findByDomainIgnoreCaseIn(List<String> domain, Pageable pageable);

    /**
     * Count by domain long.
     *
     * @param domain the domain
     * @return the long
     */
}
