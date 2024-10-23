package eu.novobit.service;

import eu.novobit.com.rest.service.ICrudImageService;
import eu.novobit.com.rest.service.ICrudServiceMethods;
import eu.novobit.enumerations.IEnumBinaryStatus;
import eu.novobit.model.Application;

/**
 * The interface Application service.
 */
public interface IApplicationService extends ICrudServiceMethods<Application>, ICrudImageService<Application> {

    /**
     * Update status application.
     *
     * @param id        the id
     * @param newStatus the new status
     * @return the application
     */
    Application updateStatus(Long id, IEnumBinaryStatus.Types newStatus);
}
