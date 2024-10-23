package eu.novobit.remote.kms;

import eu.novobit.api.PasswordControllerApi;
import eu.novobit.config.FeignConfig;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * The interface Kms password service.
 */
@FeignClient(configuration = FeignConfig.class, name = "key-service", contextId = "kms-password", path = "/api/private/password")
public interface KmsPasswordService extends PasswordControllerApi {

}
