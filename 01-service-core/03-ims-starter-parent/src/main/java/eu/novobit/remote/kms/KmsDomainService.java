package eu.novobit.remote.kms;

import eu.novobit.api.KmsDomainControllerApi;
import eu.novobit.config.FeignConfig;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * The interface Kms domain service.
 */
@FeignClient(configuration = FeignConfig.class, name = "key-service", contextId = "kms-domain", path = "/api/private/domain")
public interface KmsDomainService extends KmsDomainControllerApi {

}
