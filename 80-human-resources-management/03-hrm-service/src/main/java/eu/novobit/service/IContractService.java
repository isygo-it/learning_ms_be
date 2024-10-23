package eu.novobit.service;

import eu.novobit.com.rest.service.ICrudFileService;
import eu.novobit.com.rest.service.ICrudServiceMethods;
import eu.novobit.model.Contract;

/**
 * The interface Contract service.
 */
public interface IContractService extends ICrudServiceMethods<Contract>, ICrudFileService<Contract> {
    /**
     * Update contract status contract.
     *
     * @param id       the id
     * @param isLocked the isLocked
     * @return the contract
     */
    Contract updateContractStatus(Long id, Boolean isLocked);

}
