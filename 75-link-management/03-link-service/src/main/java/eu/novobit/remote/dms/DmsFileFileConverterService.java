package eu.novobit.remote.dms;

import eu.novobit.api.FileConverterApi;
import eu.novobit.config.FeignConfig;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * The interface Dms file file converter service.
 */
@FeignClient(configuration = FeignConfig.class, name = "document-service", contextId = "dms-file-converter", path = "/api/private/dms/file/convert")
public interface DmsFileFileConverterService extends FileConverterApi {

}
