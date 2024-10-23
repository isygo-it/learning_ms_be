package eu.novobit.filter;

import eu.novobit.dto.RequestContextDto;
import eu.novobit.enumerations.IEnumAppToken;
import eu.novobit.exception.TokenInvalidException;
import eu.novobit.service.TokenServiceApi;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;

import java.util.Map;

/**
 * The type Jwt kms client auth filter.
 */
@Slf4j
public abstract class JwtKmsClientAuthFilter extends AbstractJwtAuthFilter {

    @Autowired
    private TokenServiceApi tokenService;

    @Override
    public boolean isTokenValid(String jwt, String domain, String application, String userName) {
        if (tokenService != null) {
            ResponseEntity<Boolean> result = tokenService.isTokenValid(RequestContextDto.builder().build(),
                    domain, application, IEnumAppToken.Types.ACCESS, jwt,
                    new StringBuilder(userName.toLowerCase()).append("@").append(domain).toString());
            if (!result.getStatusCode().is2xxSuccessful() || !result.hasBody() || Boolean.FALSE.equals(result.getBody())) {
                throw new TokenInvalidException("KMS::isTokenValid");
            }
        } else {
            throw new TokenInvalidException("No token validator available");
        }
        return true;
    }

    @Override
    public void addAttributes(HttpServletRequest request, Map<String, Object> attributes) {
        if (!CollectionUtils.isEmpty(attributes)) {
            attributes.forEach((s, o) -> {
                request.setAttribute(s, o);
            });
        }
    }
}
