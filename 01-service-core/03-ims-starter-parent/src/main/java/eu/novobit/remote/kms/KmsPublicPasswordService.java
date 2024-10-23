package eu.novobit.remote.kms;

import eu.novobit.api.PublicPasswordControllerApi;
import eu.novobit.config.FeignConfig;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * The interface Kms public password service.
 */
@FeignClient(configuration = FeignConfig.class, name = "key-service", contextId = "kms-public-password", path = "/api/public/password")
public interface KmsPublicPasswordService extends PublicPasswordControllerApi {

}
