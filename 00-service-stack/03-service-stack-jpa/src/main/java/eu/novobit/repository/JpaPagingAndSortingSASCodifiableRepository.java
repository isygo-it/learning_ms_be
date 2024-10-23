package eu.novobit.repository;

import eu.novobit.annotation.IgnoreRepository;
import eu.novobit.model.ICodifiable;
import eu.novobit.model.ISASEntity;

import java.util.Optional;

/**
 * The interface Jpa paging and sorting sas codifiable repository.
 *
 * @param <T> the type parameter
 * @param <I> the type parameter
 */
@IgnoreRepository
public interface JpaPagingAndSortingSASCodifiableRepository<T extends ISASEntity & ICodifiable, I>
        extends JpaPagingAndSortingSASRepository<T, I>, JpaPagingAndSortingCodifiableRepository<T, I> {

    /**
     * Find by domain ignore case and code ignore case optional.
     *
     * @param domain the domain
     * @param code   the code
     * @return the optional
     */
    Optional<T> findByDomainIgnoreCaseAndCodeIgnoreCase(String domain, String code);
}
