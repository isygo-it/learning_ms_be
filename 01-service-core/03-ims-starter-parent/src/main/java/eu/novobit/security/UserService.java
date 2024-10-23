package eu.novobit.security;

import eu.novobit.dto.request.IsPwdExpiredRequestDto;
import eu.novobit.enumerations.IEnumAccountSystemStatus;
import eu.novobit.enumerations.IEnumAuth;
import eu.novobit.enumerations.IEnumBinaryStatus;
import eu.novobit.model.Account;
import eu.novobit.model.Domain;
import eu.novobit.remote.kms.KmsPasswordService;
import eu.novobit.repository.AccountRepository;
import eu.novobit.repository.DomainRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * The type User service.
 */
@Service
@Transactional
public class UserService implements UserDetailsService {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private KmsPasswordService passwordService;
    @Autowired
    private DomainRepository domainRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        String[] userNameArray = username.split("@");
        if (userNameArray.length >= 2) {
            Domain domain = domainRepository.findByNameIgnoreCase(userNameArray[1]).orElseThrow(() -> new UsernameNotFoundException("User Not Found!"));
            Account account = accountRepository.findByDomainIgnoreCaseAndCodeIgnoreCase(userNameArray[1], userNameArray[0]).orElseThrow(() -> new UsernameNotFoundException("User Not Found!"));
            return CustomUserDetails.builder()
                    .username(account.getCode())
                    .isAdmin(account.getIsAdmin())
                    .password(username)
                    .passwordExpired(passwordService.isPasswordExpired(//RequestContextDto.builder().build(),
                            IsPwdExpiredRequestDto.builder()
                                    .domain(account.getDomain())
                                    .email(account.getEmail())
                                    .userName(account.getCode())
                                    .authType(IEnumAuth.Types.valueOf(userNameArray[2]))
                                    .build()).getBody())
                    .authorities(Account.getAuthorities(account))
                    .domainEnabled(domain.getAdminStatus() == IEnumBinaryStatus.Types.ENABLED)
                    .accountEnabled(account.getAdminStatus() == IEnumBinaryStatus.Types.ENABLED)
                    .accountExpired(account.getSystemStatus() == IEnumAccountSystemStatus.Types.EXPIRED)
                    .accountLocked(account.getSystemStatus() == IEnumAccountSystemStatus.Types.LOCKED
                            || account.getSystemStatus() == IEnumAccountSystemStatus.Types.TEM_LOCKED)
                    .build();
        }

        throw new UsernameNotFoundException(username);
    }
}
