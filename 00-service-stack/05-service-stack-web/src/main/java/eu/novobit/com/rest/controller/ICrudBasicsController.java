package eu.novobit.com.rest.controller;

import eu.novobit.com.rest.service.ICrudBasicsService;
import eu.novobit.dto.IDto;
import eu.novobit.exception.BeanNotFoundException;
import eu.novobit.exception.MapperNotDefinedException;
import eu.novobit.exception.ServiceNotDefinedException;
import eu.novobit.mapper.EntityMapper;
import eu.novobit.model.IIdEntity;

/**
 * The interface Crud basics controller.
 *
 * @param <T> the type parameter
 * @param <D> the type parameter
 * @param <S> the type parameter
 */
interface ICrudBasicsController<T extends IIdEntity,
        D extends IDto,
        S extends ICrudBasicsService<T>> {

    /**
     * Mapper entity mapper.
     *
     * @return the entity mapper
     * @throws BeanNotFoundException     the bean not found exception
     * @throws MapperNotDefinedException the mapper not defined exception
     */
    EntityMapper<T, D> mapper() throws BeanNotFoundException, MapperNotDefinedException;

    /**
     * Crud service s.
     *
     * @return the s
     * @throws BeanNotFoundException      the bean not found exception
     * @throws ServiceNotDefinedException the service not defined exception
     */
    S crudService() throws BeanNotFoundException, ServiceNotDefinedException;
}
