package eu.novobit.remote.sms;

import eu.novobit.api.ObjectStorageControllerApi;
import eu.novobit.config.FeignConfig;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * The interface Sms storage linked file service.
 */
@FeignClient(configuration = FeignConfig.class, name = "storage-service", contextId = "sms-storage", path = "/api/private/storage")
public interface SmsStorageLinkedFileService extends ObjectStorageControllerApi {
}
