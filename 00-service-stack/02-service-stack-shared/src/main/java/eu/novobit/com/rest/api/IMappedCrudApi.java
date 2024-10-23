package eu.novobit.com.rest.api;

import eu.novobit.dto.IDto;

/**
 * The interface Mapped crud api.
 *
 * @param <MinD>  the type parameter
 * @param <FullD> the type parameter
 */
public interface IMappedCrudApi<MinD extends IDto, FullD extends MinD>
        extends IMappedFetchCrudApi<MinD, FullD>, IMappedPersistCrudApi<FullD>, IMappedDeleteCrudApi {
}
