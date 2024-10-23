package eu.novobit.repository;

import eu.novobit.model.Annex;

import java.util.List;

/**
 * The interface Annex repository.
 */
public interface AnnexRepository extends JpaPagingAndSortingRepository<Annex, Long> {
    /**
     * Find by table code list.
     *
     * @param code the code
     * @return the list
     */
    List<Annex> findByTableCode(String code);

    /**
     * Find by table code list.
     *
     * @param code      the code
     * @param reference
     * @return the list
     */
    List<Annex> findByTableCodeAndReference(String code, String reference);
}
