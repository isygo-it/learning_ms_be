package eu.novobit.com.rest.controller;

import eu.novobit.com.rest.api.IMappedPersistCrudApi;
import eu.novobit.com.rest.service.ICrudServiceMethods;
import eu.novobit.dto.IDto;
import eu.novobit.model.IIdEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;

/**
 * The type Mapped persist crud controller.
 *
 * @param <T>     the type parameter
 * @param <MinD>  the type parameter
 * @param <FullD> the type parameter
 * @param <S>     the type parameter
 */
@Slf4j
public abstract class MappedPersistCrudController<T extends IIdEntity,
        MinD extends IDto,
        FullD extends MinD,
        S extends ICrudServiceMethods<T>>
        extends AbstractCrudController<T, MinD, FullD, S>
        implements IMappedPersistCrudApi<FullD> {

    public final ResponseEntity<FullD> create(FullD object) {
        /*if(StringUtils.hasText(domain) && ISASEntity.class.isAssignableFrom(object.getClass())){
            if(!StringUtils.hasText(((ISASEntity) object).getDomain())){
                ((ISASEntity) object).setDomain(domain);
            }
        }*/
        return subCreate(object);
    }

    public final ResponseEntity<FullD> update(Long id, FullD object) {
        return subUpdate(id, object);
    }


    public final ResponseEntity<FullD> partialUpdate(Long id, FullD object) {
        return subPartialUpdate(id, object);
    }
}
