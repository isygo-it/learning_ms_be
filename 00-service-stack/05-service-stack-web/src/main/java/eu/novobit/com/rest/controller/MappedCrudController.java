package eu.novobit.com.rest.controller;

import eu.novobit.com.rest.api.IMappedCrudApi;
import eu.novobit.com.rest.service.ICrudServiceMethods;
import eu.novobit.dto.IDto;
import eu.novobit.dto.RequestContextDto;
import eu.novobit.model.IIdEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;

import java.util.List;

/**
 * The type Mapped crud controller.
 *
 * @param <T>     the type parameter
 * @param <MinD>  the type parameter
 * @param <FullD> the type parameter
 * @param <S>     the type parameter
 */
@Slf4j
public abstract class MappedCrudController<T extends IIdEntity,
        MinD extends IDto,
        FullD extends MinD,
        S extends ICrudServiceMethods<T>>
        extends AbstractCrudController<T, MinD, FullD, S>
        implements IMappedCrudApi<MinD, FullD> {

    @Override
    public final ResponseEntity<FullD> create(//RequestContextDto requestContext,
                                              FullD object) {
        return subCreate(object);
    }

    @Override
    public final ResponseEntity<?> delete(RequestContextDto requestContext,
                                          Long id) {
        return subDelete(id);
    }

    @Override
    public final ResponseEntity<List<MinD>> findAll(RequestContextDto requestContext) {
        return subFindAll(requestContext);
    }

    @Override
    public final ResponseEntity<List<FullD>> findAllFull(RequestContextDto requestContext) {
        return subFindAllFull(requestContext);
    }

    @Override
    public final ResponseEntity<List<MinD>> findAll(RequestContextDto requestContext,
                                                    Integer page,
                                                    Integer size) {
        return subFindAll(requestContext, page, size);
    }

    @Override
    public final ResponseEntity<List<FullD>> findAllFull(RequestContextDto requestContext,
                                                         Integer page,
                                                         Integer size) {
        return subFindAllFull(requestContext, page, size);
    }

    @Override
    public final ResponseEntity<FullD> findById(RequestContextDto requestContext,
                                                Long id) {
        return subFindById(requestContext, id);
    }

    @Override
    public final ResponseEntity<Long> getCount(RequestContextDto requestContext) {
        return subGetCount(requestContext.getSenderDomain());
    }

    @Override
    public final ResponseEntity<FullD> update(//RequestContextDto requestContext,
                                              Long id,
                                              FullD object) {
        return subUpdate(id, object);
    }

    @Override
    public final ResponseEntity<FullD> partialUpdate(//RequestContextDto requestContext,
                                                     Long id,
                                                     FullD object) {
        return subPartialUpdate(id, object);
    }
}
