package eu.novobit.com.rest.controller;

import eu.novobit.com.rest.service.ICrudServiceMethods;
import eu.novobit.constants.DomainConstants;
import eu.novobit.dto.IDto;
import eu.novobit.dto.RequestContextDto;
import eu.novobit.model.IIdEntity;
import eu.novobit.model.ISASEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;

import java.lang.reflect.ParameterizedType;
import java.util.List;

/**
 * The type Abstract crud controller.
 *
 * @param <T>     the type parameter
 * @param <MinD>  the type parameter
 * @param <FullD> the type parameter
 * @param <S>     the type parameter
 */
@Slf4j
public abstract class AbstractCrudController<T extends IIdEntity,
        MinD extends IDto,
        FullD extends MinD, S
        extends ICrudServiceMethods<T>>
        extends AbstractCrudBasicsController<T, MinD, FullD, S>
        implements ICrudController<T, MinD, FullD, S> {

    /**
     * The constant ERROR_API_EXCEPTION.
     */
    public static final String ERROR_API_EXCEPTION = "<Error>: api exception : {} ";
    private final Class<T> persistentClass = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];

    @Override
    public ResponseEntity<FullD> subCreate(FullD object) {
        log.info("Create {} request received", persistentClass.getSimpleName());
        if (object == null) {
            return ResponseFactory.ResponseBadRequest();
        }

        try {
            object = this.beforeCreate(object);
            return ResponseFactory.ResponseOk(mapper().entityToDto(this.afterCreate(this.crudService().create(mapper().dtoToEntity(object)))));
        } catch (Throwable e) {
            log.error(ERROR_API_EXCEPTION, e);
            return getBackExceptionResponse(e);
        }
    }

    @Override
    public ResponseEntity<List<FullD>> subCreate(List<FullD> objects) {
        log.info("Create {} request received", persistentClass.getSimpleName());
        if (CollectionUtils.isEmpty(objects)) {
            return ResponseFactory.ResponseBadRequest();
        }

        try {
            objects.forEach(FullD -> this.beforeCreate(FullD));
            List<T> entities = this.crudService().create(mapper().listDtoToEntity(objects));
            entities.forEach(t -> this.afterCreate(t));
            return ResponseFactory.ResponseOk(mapper().listEntityToDto(entities));
        } catch (Throwable e) {
            log.error(ERROR_API_EXCEPTION, e);
            return getBackExceptionResponse(e);
        }
    }

    @Override
    public ResponseEntity<?> subDelete(Long id) {
        log.info("Delete {} request received", persistentClass.getSimpleName());
        if (id == null) {
            return ResponseFactory.ResponseBadRequest();
        }

        try {
            if (this.beforeDelete(id)) {
                this.crudService().delete(id);
                this.afterDelete(id);
            }
            return ResponseFactory.ResponseOk(exceptionHandler().handleMessage("object.deleted.successfully"));
        } catch (Throwable e) {
            log.error(ERROR_API_EXCEPTION, e);
            return getBackExceptionResponse(e);
        }
    }

    @Override
    public ResponseEntity<?> subDelete(List<FullD> objects) {
        log.info("Delete {} request received", persistentClass.getSimpleName());
        if (CollectionUtils.isEmpty(objects)) {
            return ResponseFactory.ResponseBadRequest();
        }

        try {
            if (this.beforeDelete(objects)) {
                this.crudService().delete(mapper().listDtoToEntity(objects));
                this.afterDelete(objects);
            }
            return ResponseFactory.ResponseOk(exceptionHandler().handleMessage("object.deleted.successfully"));
        } catch (Throwable e) {
            log.error(ERROR_API_EXCEPTION, e);
            return getBackExceptionResponse(e);
        }
    }

    @Override
    public ResponseEntity<List<MinD>> subFindAll(RequestContextDto requestContext) {
        log.info("Find all {}s request received", persistentClass.getSimpleName());
        try {
            List<MinD> list = null;
            if (!DomainConstants.SUPER_DOMAIN_NAME.equals(requestContext.getSenderDomain()) && ISASEntity.class.isAssignableFrom(persistentClass)) {
                list = (List<MinD>) this.mapper().listEntityToDto(this.crudService().findAll(requestContext.getSenderDomain()));
            } else {
                list = (List<MinD>) this.mapper().listEntityToDto(this.crudService().findAll());
            }

            if (CollectionUtils.isEmpty(list)) {
                return ResponseFactory.ResponseNoContent();
            }

            this.afterFindAll(requestContext, list);
            return ResponseFactory.ResponseOk(list);
        } catch (Throwable e) {
            log.error(ERROR_API_EXCEPTION, e);
            return getBackExceptionResponse(e);
        }
    }

    @Override
    public ResponseEntity<List<MinD>> subFindAll(RequestContextDto requestContext, Integer page, Integer size) {
        log.info("Find all {}s by page/size request received {}/{}", persistentClass.getSimpleName(), page, size);
        try {
            List<MinD> list = null;
            if (!DomainConstants.SUPER_DOMAIN_NAME.equals(requestContext.getSenderDomain()) && ISASEntity.class.isAssignableFrom(persistentClass)) {
                list = (List<MinD>) this.mapper().listEntityToDto(this.crudService().findAll(requestContext.getSenderDomain(), PageRequest.of(page, size)));
            } else {
                list = (List<MinD>) this.mapper().listEntityToDto(this.crudService().findAll(PageRequest.of(page, size)));
            }

            if (CollectionUtils.isEmpty(list)) {
                return ResponseFactory.ResponseNoContent();
            }

            this.afterFindAll(requestContext, list);
            return ResponseFactory.ResponseOk(list);
        } catch (Throwable e) {
            log.error(ERROR_API_EXCEPTION, e);
            return getBackExceptionResponse(e);
        }
    }

    @Override
    public ResponseEntity<List<FullD>> subFindAllFull(RequestContextDto requestContext) {
        log.info("Find all {}s request received", persistentClass.getSimpleName());
        try {
            List<FullD> list = null;
            if (!DomainConstants.SUPER_DOMAIN_NAME.equals(requestContext.getSenderDomain()) && ISASEntity.class.isAssignableFrom(persistentClass)) {
                list = this.mapper().listEntityToDto(this.crudService().findAll(requestContext.getSenderDomain()));
            } else {
                list = this.mapper().listEntityToDto(this.crudService().findAll());
            }

            if (CollectionUtils.isEmpty(list)) {
                return ResponseFactory.ResponseNoContent();
            }

            this.afterFindAllFull(requestContext, list);
            return ResponseFactory.ResponseOk(list);
        } catch (Throwable e) {
            log.error(ERROR_API_EXCEPTION, e);
            return getBackExceptionResponse(e);
        }
    }

    @Override
    public ResponseEntity<List<FullD>> subFindAllFull(RequestContextDto requestContext, Integer page, Integer size) {
        log.info("Find all {}s by page/size request received {}/{}", persistentClass.getSimpleName(), page, size);
        if (page == null || size == null) {
            return ResponseFactory.ResponseBadRequest();
        }

        try {
            List<FullD> list = null;
            if (!DomainConstants.SUPER_DOMAIN_NAME.equals(requestContext.getSenderDomain()) && ISASEntity.class.isAssignableFrom(persistentClass)) {
                list = this.mapper().listEntityToDto(this.crudService().findAll(requestContext.getSenderDomain(), PageRequest.of(page, size)));
            } else {
                list = this.mapper().listEntityToDto(this.crudService().findAll(PageRequest.of(page, size)));
            }

            if (CollectionUtils.isEmpty(list)) {
                return ResponseFactory.ResponseNoContent();
            }

            this.afterFindAllFull(requestContext, list);
            return ResponseFactory.ResponseOk(list);
        } catch (Throwable e) {
            log.error(ERROR_API_EXCEPTION, e);
            return getBackExceptionResponse(e);
        }
    }


    @Override
    public ResponseEntity<FullD> subFindById(RequestContextDto requestContext, Long id) {
        log.info("Find {} by id request received", persistentClass.getSimpleName());
        try {
            final FullD object = this.mapper().entityToDto(this.crudService().findById(id));
            if (object == null) {
                return ResponseFactory.ResponseNoContent();
            }

            return ResponseFactory.ResponseOk(this.afterFindById(object));
        } catch (Throwable e) {
            log.error(ERROR_API_EXCEPTION, e);
            return getBackExceptionResponse(e);
        }
    }

    @Override
    public ResponseEntity<Long> subGetCount(String domain) {
        log.info("Get count {} request received", persistentClass.getSimpleName());
        try {
            List<FullD> list = null;
            if (!DomainConstants.SUPER_DOMAIN_NAME.equals(domain) && ISASEntity.class.isAssignableFrom(persistentClass)) {
                return ResponseFactory.ResponseOk(this.crudService().count(domain));
            } else {
                return ResponseFactory.ResponseOk(this.crudService().count());
            }
        } catch (Throwable e) {
            log.error(ERROR_API_EXCEPTION, e);
            return getBackExceptionResponse(e);
        }
    }

    @Override
    public ResponseEntity<FullD> subUpdate(Long id, FullD object) {
        log.info("Update {} request received", persistentClass.getSimpleName());
        if (object == null || id == null) {
            return ResponseFactory.ResponseBadRequest();
        }

        try {
            object.setId(id);
            object = this.beforeUpdate(id, object);
            return ResponseFactory.ResponseOk(this.mapper().entityToDto(this.afterUpdate(this.crudService().update(mapper().dtoToEntity(object)))));
        } catch (Throwable e) {
            log.error(ERROR_API_EXCEPTION, e);
            return getBackExceptionResponse(e);
        }
    }

    /**
     * Sub partial update response entity.
     *
     * @param id     the id
     * @param object the object
     * @return the response entity
     */
    public ResponseEntity<FullD> subPartialUpdate(Long id, FullD object) {
        log.info("Partial update {} request received", persistentClass.getSimpleName());
        if (object == null || id == null) {
            return ResponseFactory.ResponseBadRequest();
        }
        try {
            object.setId(id);
            object = this.beforeUpdate(id, object);
            return ResponseFactory.ResponseOk(this.mapper().entityToDto(this.afterUpdate(this.crudService().partialUpdate(mapper().dtoToEntity(object), id))));
        } catch (Throwable e) {
            log.error(ERROR_API_EXCEPTION, e);
            return getBackExceptionResponse(e);
        }
    }

    @Override
    public ResponseEntity<List<FullD>> subUpdate(List<FullD> objects) {
        log.info("Update {} request received", persistentClass.getSimpleName());
        if (CollectionUtils.isEmpty(objects)) {
            return ResponseFactory.ResponseBadRequest();
        }

        try {
            return ResponseFactory.ResponseOk(mapper().listEntityToDto(this.crudService().update(mapper().listDtoToEntity(objects))));
        } catch (Throwable e) {
            log.error(ERROR_API_EXCEPTION, e);
            return getBackExceptionResponse(e);
        }
    }

    @Override
    public FullD beforeCreate(FullD object) {
        return object;
    }

    @Override
    public T afterCreate(T object) {
        return object;
    }

    @Override
    public FullD beforeUpdate(Long id, FullD object) {
        return object;
    }

    @Override
    public T afterUpdate(T object) {
        return object;
    }

    @Override
    public boolean beforeDelete(Long id) {
        return true;
    }

    @Override
    public boolean afterDelete(Long id) {
        return true;
    }

    @Override
    public boolean beforeDelete(List<FullD> objects) {
        return true;
    }

    @Override
    public boolean afterDelete(List<FullD> objects) {
        return true;
    }

    @Override
    public FullD afterFindById(FullD object) {
        return object;
    }

    @Override
    public List<FullD> afterFindAllFull(RequestContextDto requestContext, List<FullD> list) {
        return list;
    }

    @Override
    public List<MinD> afterFindAll(RequestContextDto requestContext, List<MinD> list) {
        return list;
    }
}
