package eu.novobit.com.rest.controller;

import eu.novobit.com.rest.service.ICrudServiceMethods;
import eu.novobit.dto.IDto;
import eu.novobit.dto.RequestContextDto;
import eu.novobit.model.IIdEntity;
import org.springframework.http.ResponseEntity;

import java.util.List;

/**
 * The interface Crud controller.
 *
 * @param <T>     the type parameter
 * @param <MinD>  the type parameter
 * @param <FullD> the type parameter
 * @param <S>     the type parameter
 */
interface ICrudController<T extends IIdEntity, MinD extends IDto, FullD extends MinD, S extends ICrudServiceMethods<T>> {

    /**
     * Sub create response entity.
     *
     * @param object the object
     * @return the response entity
     */
    ResponseEntity<FullD> subCreate(FullD object);

    /**
     * Before create full d.
     *
     * @param object the object
     * @return the full d
     */
    FullD beforeCreate(FullD object);

    /**
     * After create t.
     *
     * @param object the object
     * @return the t
     */
    T afterCreate(T object);

    /**
     * Sub create response entity.
     *
     * @param objects the objects
     * @return the response entity
     */
    ResponseEntity<List<FullD>> subCreate(List<FullD> objects);

    /**
     * Sub update response entity.
     *
     * @param id     the id
     * @param object the object
     * @return the response entity
     */
    ResponseEntity<FullD> subUpdate(Long id, FullD object);

    /**
     * Before update full d.
     *
     * @param id     the id
     * @param object the object
     * @return the full d
     */
    FullD beforeUpdate(Long id, FullD object);

    /**
     * After update t.
     *
     * @param object the object
     * @return the t
     */
    T afterUpdate(T object);

    /**
     * Sub update response entity.
     *
     * @param objects the objects
     * @return the response entity
     */
    ResponseEntity<List<FullD>> subUpdate(List<FullD> objects);

    /**
     * Sub delete response entity.
     *
     * @param id the id
     * @return the response entity
     */
    ResponseEntity<?> subDelete(Long id);

    /**
     * Before delete boolean.
     *
     * @param id the id
     * @return the boolean
     */
    boolean beforeDelete(Long id);

    /**
     * After delete boolean.
     *
     * @param id the id
     * @return the boolean
     */
    boolean afterDelete(Long id);

    /**
     * Before delete boolean.
     *
     * @param objects the objects
     * @return the boolean
     */
    boolean beforeDelete(List<FullD> objects);

    /**
     * After delete boolean.
     *
     * @param objects the objects
     * @return the boolean
     */
    boolean afterDelete(List<FullD> objects);

    /**
     * After find by id full d.
     *
     * @param object the object
     * @return the full d
     */
    FullD afterFindById(FullD object);

    /**
     * Sub delete response entity.
     *
     * @param objects the objects
     * @return the response entity
     */
    ResponseEntity<?> subDelete(List<FullD> objects);

    /**
     * Sub find all full response entity.
     *
     * @param requestContext the request context
     * @return the response entity
     */
    ResponseEntity<List<FullD>> subFindAllFull(RequestContextDto requestContext);

    /**
     * After find all full list.
     *
     * @param requestContext the request context
     * @param list           the list
     * @return the list
     */
    List<FullD> afterFindAllFull(RequestContextDto requestContext, List<FullD> list);

    /**
     * Sub find all full response entity.
     *
     * @param requestContext the request context
     * @param page           the page
     * @param size           the size
     * @return the response entity
     */
    ResponseEntity<List<FullD>> subFindAllFull(RequestContextDto requestContext, Integer page, Integer size);

    /**
     * Sub find all response entity.
     *
     * @param requestContext the request context
     * @return the response entity
     */
    ResponseEntity<List<MinD>> subFindAll(RequestContextDto requestContext);

    /**
     * After find all list.
     *
     * @param requestContext the request context
     * @param list           the list
     * @return the list
     */
    List<MinD> afterFindAll(RequestContextDto requestContext, List<MinD> list);

    /**
     * Sub find all response entity.
     *
     * @param requestContext the request context
     * @param page           the page
     * @param size           the size
     * @return the response entity
     */
    ResponseEntity<List<MinD>> subFindAll(RequestContextDto requestContext, Integer page, Integer size);

    /**
     * Sub find by id response entity.
     *
     * @param requestContext the request context
     * @param id             the id
     * @return the response entity
     */
    ResponseEntity<FullD> subFindById(RequestContextDto requestContext, Long id);


    /**
     * Sub get count response entity.
     *
     * @param domain the domain
     * @return the response entity
     */
    ResponseEntity<Long> subGetCount(String domain);
}
