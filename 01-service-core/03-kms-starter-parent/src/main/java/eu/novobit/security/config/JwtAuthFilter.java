package eu.novobit.security.config;

import eu.novobit.filter.JwtKmsAuthFilter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * The type Jwt auth filter.
 */
@Slf4j
@Component
public class JwtAuthFilter extends JwtKmsAuthFilter {

}
