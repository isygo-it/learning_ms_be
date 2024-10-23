package eu.novobit.com.rest.controller;

import eu.novobit.com.rest.api.IMappedFetchCrudApi;
import eu.novobit.com.rest.service.ICrudServiceMethods;
import eu.novobit.dto.IDto;
import eu.novobit.dto.RequestContextDto;
import eu.novobit.model.IIdEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;

import java.util.List;

/**
 * The type Mapped fetch crud controller.
 *
 * @param <T>     the type parameter
 * @param <MinD>  the type parameter
 * @param <FullD> the type parameter
 * @param <S>     the type parameter
 */
@Slf4j
public abstract class MappedFetchCrudController<T extends IIdEntity,
        MinD extends IDto,
        FullD extends MinD,
        S extends ICrudServiceMethods<T>>
        extends AbstractCrudController<T, MinD, FullD, S>
        implements IMappedFetchCrudApi<MinD, FullD> {


    public final ResponseEntity<List<MinD>> findAll(RequestContextDto requestContext) {
        return subFindAll(requestContext);
    }

    public final ResponseEntity<List<FullD>> findAllFull(RequestContextDto requestContext) {
        return subFindAllFull(requestContext);
    }

    public final ResponseEntity<List<MinD>> findAll(RequestContextDto requestContext, Integer page, Integer size) {
        return subFindAll(requestContext, page, size);
    }

    public final ResponseEntity<List<FullD>> findAllFull(RequestContextDto requestContext, Integer page, Integer size) {
        return subFindAllFull(requestContext, page, size);
    }

    public final ResponseEntity<FullD> findById(RequestContextDto requestContext, Long id) {
        return subFindById(requestContext, id);
    }

    /**
     * Gets count.
     *
     * @param domain the domain
     * @return the count
     */
    public final ResponseEntity<Long> getCount(String domain) {
        return subGetCount(domain);
    }
}
