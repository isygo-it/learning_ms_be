package eu.novobit.com.rest.service;

import eu.novobit.exception.ObjectNotFoundException;
import eu.novobit.model.IIdEntity;
import jakarta.transaction.NotSupportedException;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * The interface Crud service methods.
 *
 * @param <T> the type parameter
 */
public interface ICrudServiceMethods<T extends IIdEntity> extends ICrudBasicsService<T> {

    /**
     * Gets next code.
     *
     * @return the next code
     */
    String getNextCode();

    /**
     * Count long.
     *
     * @return the long
     */
    Long count();

    /**
     * Count long.
     *
     * @param domain the domain
     * @return the long
     */
    Long count(String domain);

    /**
     * Exists by id boolean.
     *
     * @param id the id
     * @return the boolean
     */
    boolean existsById(Long id);

    /**
     * Create t.
     *
     * @param object the object
     * @return the t
     */
    T create(T object);

    /**
     * Create and flush t.
     *
     * @param object the object
     * @return the t
     */
    T createAndFlush(T object);

    /**
     * Create list.
     *
     * @param objects the objects
     * @return the list
     */
    List<T> create(List<T> objects);

    /**
     * Delete.
     *
     * @param id the id
     */
    void delete(Long id);

    /**
     * Delete.
     *
     * @param objects the objects
     */
    void delete(List<T> objects);

    /**
     * Find all list.
     *
     * @return the list
     */
    List<T> findAll();

    /**
     * Find all list.
     *
     * @param domain the domain
     * @return the list
     * @throws NotSupportedException the not supported exception
     */
    List<T> findAll(String domain) throws NotSupportedException;

    /**
     * Find all list.
     *
     * @param pageable the pageable
     * @return the list
     */
    List<T> findAll(Pageable pageable);

    /**
     * Find all list.
     *
     * @param domain   the domain
     * @param pageable the pageable
     * @return the list
     * @throws NotSupportedException the not supported exception
     */
    List<T> findAll(String domain, Pageable pageable) throws NotSupportedException;

    /**
     * Find by id t.
     *
     * @param id the id
     * @return the t
     * @throws ObjectNotFoundException the object not found exception
     */
    T findById(Long id) throws ObjectNotFoundException;

    /**
     * Save or update t.
     *
     * @param object the object
     * @return the t
     */
    T saveOrUpdate(T object);

    /**
     * Save or update list.
     *
     * @param objects the objects
     * @return the list
     */
    List<T> saveOrUpdate(List<T> objects);

    /**
     * Update t.
     *
     * @param object the object
     * @return the t
     */
    T update(T object);

    /**
     * Partial update t.
     *
     * @param object the object
     * @param Id     the id
     * @return the t
     * @throws NoSuchFieldException   the no such field exception
     * @throws IllegalAccessException the illegal access exception
     */
    T partialUpdate(T object, Long Id) throws NoSuchFieldException, IllegalAccessException;

    /**
     * Update list.
     *
     * @param objects the objects
     * @return the list
     */
    List<T> update(List<T> objects);

    /**
     * Before update t.
     *
     * @param object the object
     * @return the t
     */
    T beforeUpdate(T object);

    /**
     * After update t.
     *
     * @param object the object
     * @return the t
     */
    T afterUpdate(T object);

    /**
     * Before delete.
     *
     * @param id the id
     */
    void beforeDelete(Long id);

    /**
     * After delete.
     *
     * @param id the id
     */
    void afterDelete(Long id);

    /**
     * Before delete.
     *
     * @param objects the objects
     */
    void beforeDelete(List<T> objects);

    /**
     * After delete.
     *
     * @param objects the objects
     */
    void afterDelete(List<T> objects);

    /**
     * Before create t.
     *
     * @param object the object
     * @return the t
     */
    T beforeCreate(T object);

    /**
     * After find all list.
     *
     * @param list the list
     * @return the list
     */
    List<T> afterFindAll(List<T> list);

    /**
     * After find by id t.
     *
     * @param object the object
     * @return the t
     */
    T afterFindById(T object);

    /**
     * After create t.
     *
     * @param object the object
     * @return the t
     */
    T afterCreate(T object);
}
