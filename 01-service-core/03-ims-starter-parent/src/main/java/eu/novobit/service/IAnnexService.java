package eu.novobit.service;

import eu.novobit.com.rest.service.ICrudServiceMethods;
import eu.novobit.model.Annex;

import java.util.List;

/**
 * The interface Annex service.
 */
public interface IAnnexService extends ICrudServiceMethods<Annex> {

    /**
     * Find annex by code list.
     *
     * @param code the code
     * @return the list
     */
    List<Annex> findAnnexByCode(String code);

    /**
     * Find annex by code list.
     *
     * @param code the code
     * @param code the reference
     * @return the list
     */
    List<Annex> findAnnexByCodeAndRef(String code, String reference);
}
