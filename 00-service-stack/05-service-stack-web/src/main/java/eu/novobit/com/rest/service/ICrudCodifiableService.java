package eu.novobit.com.rest.service;

import eu.novobit.exception.NextCodeServiceNotDefinedException;
import eu.novobit.exception.RemoteNextCodeServiceNotDefinedException;
import eu.novobit.model.ICodifiable;
import eu.novobit.model.IIdEntity;
import eu.novobit.model.extendable.NextCodeModel;
import eu.novobit.service.IRemoteNextCodeService;
import eu.novobit.service.nextCode.INextCodeService;

/**
 * The interface Crud codifiable service.
 *
 * @param <T> the type parameter
 */
public interface ICrudCodifiableService<T extends IIdEntity & ICodifiable>
        extends ICrudServiceMethods<T> {

    /**
     * Init code generator next code model.
     *
     * @return the next code model
     */
    NextCodeModel initCodeGenerator();

    /**
     * Next code service next code service.
     *
     * @return the next code service
     * @throws NextCodeServiceNotDefinedException the next code service not defined exception
     */
    INextCodeService<NextCodeModel> nextCodeService() throws NextCodeServiceNotDefinedException;

    /**
     * Remote next code service remote next code service.
     *
     * @return the remote next code service
     * @throws RemoteNextCodeServiceNotDefinedException the remote next code service not defined exception
     */
    IRemoteNextCodeService remoteNextCodeService() throws RemoteNextCodeServiceNotDefinedException;

    /**
     * Gets next code key.
     *
     * @param initNextCode the init next code
     * @return the next code key
     */
    String getNextCodeKey(NextCodeModel initNextCode);
}
