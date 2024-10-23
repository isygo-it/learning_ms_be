package eu.novobit.service.impl;

import eu.novobit.annotation.SrvRepo;
import eu.novobit.com.rest.service.AbstractCrudService;
import eu.novobit.model.AccountDetails;
import eu.novobit.repository.AccountDetailsRepository;
import eu.novobit.service.IAccountDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * The type Account details service.
 */
@Service
@Transactional
@SrvRepo(value = AccountDetailsRepository.class)
public class AccountDetailsService extends AbstractCrudService<AccountDetails, AccountDetailsRepository> implements IAccountDetailsService {
}
