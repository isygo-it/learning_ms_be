package eu.novobit.security;

import eu.novobit.dto.request.MatchesRequestDto;
import eu.novobit.enumerations.IEnumAuth;
import eu.novobit.enumerations.IEnumPasswordStatus;
import eu.novobit.remote.kms.KmsPasswordService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;

/**
 * The type Password encoder service.
 */
@Slf4j
@Service
@Transactional
public class PasswordEncoderService implements PasswordEncoder {

    @Autowired
    private KmsPasswordService passwordService;

    @Override
    public String encode(CharSequence rawPassword) {
        log.warn("Sorry, Encoding is delegated to KMS service");
        return rawPassword.toString(); //prepareTimingAttackProtection
    }

    @Override
    public boolean matches(CharSequence rawPassword, String encodedPassword /*userName@Domain*/) {
        String[] encodedPasswordArray = encodedPassword.split("@");
        if (encodedPasswordArray.length == 3) {
            return !Arrays.asList(IEnumPasswordStatus.Types.BAD, IEnumPasswordStatus.Types.BROKEN).contains(
                    passwordService.matches(//RequestContextDto.builder().build(),
                            MatchesRequestDto.builder()
                                    .domain(encodedPasswordArray[1])
                                    .userName(encodedPasswordArray[0])
                                    .authType(IEnumAuth.Types.valueOf(encodedPasswordArray[2]))
                                    .password(rawPassword.toString())
                                    .build()).getBody()
            );
        }
        return false;
    }
}
