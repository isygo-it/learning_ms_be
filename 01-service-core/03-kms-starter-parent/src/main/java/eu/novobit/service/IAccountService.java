package eu.novobit.service;

import eu.novobit.com.rest.service.ICrudServiceMethods;
import eu.novobit.model.Account;

/**
 * The interface Account service.
 */
public interface IAccountService extends ICrudServiceMethods<Account> {

    /**
     * Check if exists boolean.
     *
     * @param account           the account
     * @param createIfNotExists the create if not exists
     * @return the boolean
     */
    boolean checkIfExists(Account account, boolean createIfNotExists);
}
