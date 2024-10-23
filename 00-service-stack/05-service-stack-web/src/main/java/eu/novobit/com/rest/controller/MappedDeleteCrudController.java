package eu.novobit.com.rest.controller;

import eu.novobit.com.rest.api.IMappedDeleteCrudApi;
import eu.novobit.com.rest.service.ICrudServiceMethods;
import eu.novobit.dto.IDto;
import eu.novobit.dto.RequestContextDto;
import eu.novobit.model.IIdEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;

/**
 * The type Mapped delete crud controller.
 *
 * @param <T>     the type parameter
 * @param <MinD>  the type parameter
 * @param <FullD> the type parameter
 * @param <S>     the type parameter
 */
@Slf4j
public abstract class MappedDeleteCrudController<T extends IIdEntity,
        MinD extends IDto,
        FullD extends MinD,
        S extends ICrudServiceMethods<T>>
        extends AbstractCrudController<T, MinD, FullD, S>
        implements IMappedDeleteCrudApi {

    @Override
    public final ResponseEntity<?> delete(RequestContextDto requestContext,
                                          Long id) {
        return subDelete(id);
    }
}
