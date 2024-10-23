package eu.novobit.com.rest.controller;

import eu.novobit.annotation.CtrlDef;
import eu.novobit.annotation.CtrlMapper;
import eu.novobit.annotation.CtrlService;
import eu.novobit.com.rest.service.ICrudBasicsService;
import eu.novobit.dto.IDto;
import eu.novobit.exception.BeanNotFoundException;
import eu.novobit.exception.MapperNotDefinedException;
import eu.novobit.exception.ServiceNotDefinedException;
import eu.novobit.mapper.EntityMapper;
import eu.novobit.model.IIdEntity;
import lombok.extern.slf4j.Slf4j;

/**
 * The type Abstract crud basics controller.
 *
 * @param <T>     the type parameter
 * @param <MinD>  the type parameter
 * @param <FullD> the type parameter
 * @param <S>     the type parameter
 */
@Slf4j
public abstract class AbstractCrudBasicsController<T extends IIdEntity,
        MinD extends IDto,
        FullD extends MinD,
        S extends ICrudBasicsService<T>>
        extends AbstractController
        implements ICrudBasicsController<T, FullD, S> {

    /**
     * The constant ERROR_BEAN_NOT_FOUND.
     */
    public static final String ERROR_BEAN_NOT_FOUND = "<Error>: bean {} not found";
    /**
     * The constant CONTROL_EU_NOVOBIT_SERVICE.
     */
    public static final String CONTROL_EU_NOVOBIT_SERVICE = "control eu.novobit.service";
    private EntityMapper<T, FullD> entityMapper;
    private S crudService;

    @Override
    public final S crudService() throws BeanNotFoundException, ServiceNotDefinedException {
        if (this.crudService == null) {
            CtrlDef ctrlDef = this.getClass().getAnnotation(CtrlDef.class);
            if (ctrlDef != null) {
                this.crudService = (S) getApplicationContextService().getBean(ctrlDef.service());
                if (this.crudService == null) {
                    log.error(ERROR_BEAN_NOT_FOUND, ctrlDef.service().getSimpleName());
                    throw new BeanNotFoundException(CONTROL_EU_NOVOBIT_SERVICE);
                }
            } else {
                CtrlService ctrlService = this.getClass().getAnnotation(CtrlService.class);
                if (ctrlService != null) {
                    this.crudService = (S) getApplicationContextService().getBean(ctrlService.value());
                    if (this.crudService == null) {
                        log.error(ERROR_BEAN_NOT_FOUND, ctrlService.value().getSimpleName());
                        throw new BeanNotFoundException(CONTROL_EU_NOVOBIT_SERVICE);
                    }
                }
                log.error("<Error>: Service bean not defined");
                throw new ServiceNotDefinedException(CONTROL_EU_NOVOBIT_SERVICE);
            }
        }

        return this.crudService;
    }

    @Override
    public final EntityMapper<T, FullD> mapper() throws BeanNotFoundException, MapperNotDefinedException {
        if (this.entityMapper == null) {
            CtrlDef ctrlDef = this.getClass().getAnnotation(CtrlDef.class);
            if (ctrlDef != null) {
                this.entityMapper = getApplicationContextService().getBean(ctrlDef.mapper());
                if (this.entityMapper == null) {
                    log.error(ERROR_BEAN_NOT_FOUND, ctrlDef.mapper().getSimpleName());
                    throw new BeanNotFoundException(ctrlDef.mapper().getSimpleName());
                }
            } else {
                CtrlMapper ctrlMapper = this.getClass().getAnnotation(CtrlMapper.class);
                this.entityMapper = getApplicationContextService().getBean(ctrlMapper.value());
                if (this.entityMapper == null) {
                    log.error(ERROR_BEAN_NOT_FOUND, ctrlMapper.value().getSimpleName());
                    throw new BeanNotFoundException(ctrlMapper.value().getSimpleName());
                } else {
                    log.error("<Error>: Mapper bean not defined");
                    throw new MapperNotDefinedException("Mapper");
                }
            }
        }

        return this.entityMapper;
    }
}
