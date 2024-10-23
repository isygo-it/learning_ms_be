package eu.novobit.remote.kms;

import eu.novobit.config.FeignConfig;
import eu.novobit.service.KeyServiceApi;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * The interface Kms key service.
 */
@FeignClient(configuration = FeignConfig.class, name = "key-service", contextId = "kms-key", path = "/api/private/key")
public interface KmsKeyService extends KeyServiceApi {

}
