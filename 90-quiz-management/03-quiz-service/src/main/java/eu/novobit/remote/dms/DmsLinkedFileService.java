package eu.novobit.remote.dms;

import eu.novobit.api.LinkedFileApi;
import eu.novobit.config.FeignConfig;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * The interface Dms linked file service.
 */
@FeignClient(configuration = FeignConfig.class, name = "document-service", contextId = "dms-linked-file", path = "/api/private/dms/document")
public interface DmsLinkedFileService extends LinkedFileApi {

}
