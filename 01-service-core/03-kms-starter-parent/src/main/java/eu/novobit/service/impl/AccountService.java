package eu.novobit.service.impl;

import eu.novobit.annotation.CodeGenLocal;
import eu.novobit.annotation.SrvRepo;
import eu.novobit.com.rest.service.AbstractCrudService;
import eu.novobit.model.Account;
import eu.novobit.repository.AccountRepository;
import eu.novobit.service.IAccountService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * The type Account service.
 */
@Service
@Transactional
@CodeGenLocal(value = NextCodeService.class)
@SrvRepo(value = AccountRepository.class)
public class AccountService extends AbstractCrudService<Account, AccountRepository> implements IAccountService {

    @Override
    public boolean checkIfExists(Account account, boolean createIfNotExists) {
        Optional<Account> optional = repository().findByCodeIgnoreCase(account.getCode());
        if (optional.isPresent()) {
            //Update the domain if not exists
            account.setId(optional.get().getId());
            this.update(account);
            return true;
        } else if (createIfNotExists) {
            //Create the account if not exists
            this.create(account);
            return true;
        }

        return false;
    }
}
