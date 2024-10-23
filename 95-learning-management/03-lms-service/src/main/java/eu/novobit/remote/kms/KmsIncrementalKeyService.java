package eu.novobit.remote.kms;

import eu.novobit.api.IncrementalKeyControllerApi;
import eu.novobit.config.FeignConfig;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * The interface Kms incremental key service.
 */
@FeignClient(configuration = FeignConfig.class, name = "key-service", contextId = "incremental-key", path = "/api/private/key")
public interface KmsIncrementalKeyService extends IncrementalKeyControllerApi {

}
