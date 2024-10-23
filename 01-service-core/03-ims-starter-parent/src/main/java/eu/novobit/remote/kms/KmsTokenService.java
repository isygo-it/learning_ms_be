package eu.novobit.remote.kms;


import eu.novobit.config.FeignConfig;
import eu.novobit.service.TokenServiceApi;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * The interface Kms token service.
 */
@FeignClient(configuration = FeignConfig.class, name = "key-service", contextId = "kms-token", path = "/api/private/token")
public interface KmsTokenService extends TokenServiceApi {

}
