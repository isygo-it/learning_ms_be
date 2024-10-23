package eu.novobit.com.rest.controller;

import eu.novobit.com.rest.api.IMappedCrudApi;
import eu.novobit.dto.IDto;
import eu.novobit.dto.RequestContextDto;
import eu.novobit.model.IIdEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;

import java.util.List;

/**
 * The type Fake crud controller.
 *
 * @param <T>     the type parameter
 * @param <MinD>  the type parameter
 * @param <FullD> the type parameter
 */
@Slf4j
public abstract class FakeCrudController<T extends IIdEntity,
        MinD extends IDto,
        FullD extends MinD>
        implements IMappedCrudApi<MinD, FullD> {


    @Override
    public ResponseEntity<?> delete(RequestContextDto requestContext,
                                    Long id) {
        throw new UnsupportedOperationException("This is a fake controller");
    }

    @Override
    public ResponseEntity<List<MinD>> findAll(RequestContextDto requestContext) {
        throw new UnsupportedOperationException("This is a fake controller");
    }

    @Override
    public ResponseEntity<List<MinD>> findAll(RequestContextDto requestContext,
                                              Integer page,
                                              Integer size) {
        throw new UnsupportedOperationException("This is a fake controller");
    }

    @Override
    public ResponseEntity<List<FullD>> findAllFull(RequestContextDto requestContext) {
        throw new UnsupportedOperationException("This is a fake controller");
    }

    @Override
    public ResponseEntity<List<FullD>> findAllFull(RequestContextDto requestContext,
                                                   Integer page,
                                                   Integer size) {
        throw new UnsupportedOperationException("This is a fake controller");
    }

    @Override
    public ResponseEntity<FullD> findById(RequestContextDto requestContext,
                                          Long id) {
        throw new UnsupportedOperationException("This is a fake controller");
    }

    @Override
    public ResponseEntity<Long> getCount(RequestContextDto requestContext) {
        throw new UnsupportedOperationException("This is a fake controller");
    }

    @Override
    public ResponseEntity<FullD> create(//RequestContextDto requestContext,
                                        FullD object) {
        throw new UnsupportedOperationException("This is a fake controller");
    }

    @Override
    public ResponseEntity<FullD> update(//RequestContextDto requestContext,
                                        Long id,
                                        FullD object) {
        throw new UnsupportedOperationException("This is a fake controller");
    }

    @Override
    public ResponseEntity<FullD> partialUpdate(//RequestContextDto requestContext,
                                               Long id,
                                               FullD object) {
        throw new UnsupportedOperationException("This is a fake controller");
    }
}
