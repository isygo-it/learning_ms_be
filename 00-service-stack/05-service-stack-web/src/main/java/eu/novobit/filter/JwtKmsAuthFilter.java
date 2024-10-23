package eu.novobit.filter;

import eu.novobit.enumerations.IEnumAppToken;
import eu.novobit.exception.TokenInvalidException;
import eu.novobit.service.ITokenService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * The type Jwt kms auth filter.
 */
@Slf4j
public class JwtKmsAuthFilter extends AbstractJwtAuthFilter {

    /**
     * The constant shouldNotFilterHosts.
     */
    public static Map<String, String> shouldNotFilterHosts = new HashMap<>();

    @Autowired
    private ITokenService tokenService;

    @Override
    public boolean isTokenValid(String jwt, String domain, String application, String userName) {
        if (tokenService != null) {
            if (!tokenService.isTokenValid(domain, application, IEnumAppToken.Types.ACCESS, jwt,
                    new StringBuilder(userName.toLowerCase()).append("@").append(domain).toString())) {
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
