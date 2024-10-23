package eu.novobit.com.rest.service;

import eu.novobit.constants.DomainConstants;
import eu.novobit.exception.BadArgumentException;
import eu.novobit.exception.EmptyListException;
import eu.novobit.exception.ObjectNotFoundException;
import eu.novobit.model.ICodifiable;
import eu.novobit.model.IIdEntity;
import eu.novobit.model.ISASEntity;
import eu.novobit.model.base.AuditableCancelableEntity;
import eu.novobit.model.base.CancelableEntity;
import eu.novobit.repository.JpaPagingAndSortingSASRepository;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.NotSupportedException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.*;

/**
 * The type Abstract crud service.
 *
 * @param <T> the type parameter
 * @param <R> the type parameter
 */
@Slf4j
public abstract class AbstractCrudService<T extends IIdEntity, R extends JpaRepository> extends AbstractCrudBasicsService<T, R>
        implements ICrudServiceMethods<T> {

    /**
     * The constant NULL_OBJECT_PROVIDED.
     */
    public static final String NULL_OBJECT_PROVIDED = "Null object provided";
    /**
     * The constant EMPTY_OBJECT_LIST_PROVIDED.
     */
    public static final String EMPTY_OBJECT_LIST_PROVIDED = "Empty object list provided";
    /**
     * The Persistent class.
     */
    Class<T> persistentClass = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];

    @Override
    public String getNextCode() {
        return null;
    }

    @Override
    @Transactional(readOnly = true)
    public Long count() {
        return repository().count();
    }

    @Override
    @Transactional(readOnly = true)
    public Long count(String domain) {
        return repository().count();
    }

    @Override
    public boolean existsById(Long id) {
        return repository().existsById(id);
    }

    @Override
    public T beforeCreate(T object) {
        return object;
    }

    @Override
    public T afterCreate(T object) {
        return object;
    }

    @Override
    @Transactional
    public T create(T object) {
        if (Objects.isNull(object)) {
            throw new BadArgumentException(NULL_OBJECT_PROVIDED);
        }

        if (object.getId() == null) {
            //BEFORE CREATE HERE
            object = this.beforeCreate(object);
            if (ICodifiable.class.isAssignableFrom(object.getClass()) && !StringUtils.hasText(((ICodifiable) object).getCode())) {
                ((ICodifiable) object).setCode(this.getNextCode());
            }
            return this.afterCreate((T) repository().save(object));
        } else {
            throw new EntityExistsException();
        }
    }

    @Override
    @Transactional
    public T createAndFlush(T object) {
        if (Objects.isNull(object)) {
            throw new BadArgumentException(NULL_OBJECT_PROVIDED);
        }

        if (object.getId() == null) {
            object = this.beforeCreate(object);
            if (ICodifiable.class.isAssignableFrom(object.getClass()) && !StringUtils.hasText(((ICodifiable) object).getCode())) {
                ((ICodifiable) object).setCode(this.getNextCode());
            }
            return this.afterCreate((T) repository().saveAndFlush(object));
        } else {
            throw new EntityExistsException();
        }
    }

    @Override
    public List<T> create(List<T> objects) {
        if (CollectionUtils.isEmpty(objects)) {
            throw new EmptyListException(EMPTY_OBJECT_LIST_PROVIDED);
        }

        List<T> createdObjects = new ArrayList<>();
        objects.forEach(object -> {
            createdObjects.add(this.create(object));
        });

        return createdObjects;
    }

    @Override
    @Transactional
    public T update(T object) {
        if (Objects.isNull(object)) {
            throw new BadArgumentException(NULL_OBJECT_PROVIDED);
        }

        if (object.getId() != null) {
            object = this.beforeUpdate(object);
            if (ICodifiable.class.isAssignableFrom(object.getClass()) && !StringUtils.hasText(((ICodifiable) object).getCode())) {
                ((ICodifiable) object).setCode(this.getNextCode());
            }
            return this.afterUpdate((T) repository().save(object));
        } else {
            throw new EntityNotFoundException();
        }
    }


    @Override
    @Transactional
    public T partialUpdate(T object, Long id) throws NoSuchFieldException, IllegalAccessException {
        if (Objects.isNull(object)) {
            throw new BadArgumentException(NULL_OBJECT_PROVIDED);
        }

        if (object.getId() != null) {
            object = this.beforeUpdate(object);
            if (ICodifiable.class.isAssignableFrom(object.getClass()) && !StringUtils.hasText(((ICodifiable) object).getCode())) {
                ((ICodifiable) object).setCode(this.getNextCode());
            }
            T existingEntity = this.findById(id);
            if (existingEntity == null) {
                throw new EntityNotFoundException(object.getClass().getSimpleName() + " Entity not found with id: " + id);
            }

            Field[] fields = object.getClass().getDeclaredFields();
            for (Field field : fields) {
                try {
                    field.setAccessible(true);
                    Object value = field.get(object);
                    if (value != null) {
                        Field entityField = object.getClass().getDeclaredField(field.getName());
                        entityField.setAccessible(true);
                        entityField.set(existingEntity, value);
                        //TODO : Can be improved by add nested patching ( look next line )
                        /*
                           if (entityField.getType().isAssignableFrom(value.getClass())) {
                            // Recursively update the nested entity
                            entityField.set(existingEntity, patchEntity(entityField.getType(), entityId, value));
                            } else {
                                entityField.set(existingEntity, value);
                            }
                         */
                    }
                } catch (Exception e) {
                    // Handle any exceptions
                    throw e;
                }
            }

            // Save the updated entity
            return this.afterUpdate((T) repository().save(existingEntity));
        } else {
            throw new EntityNotFoundException();
        }
    }

    @Override
    @Transactional
    public List<T> update(List<T> objects) {
        if (CollectionUtils.isEmpty(objects)) {
            throw new EmptyListException(EMPTY_OBJECT_LIST_PROVIDED);
        }
        List<T> updatedObjects = new ArrayList<>();
        objects.forEach(object -> {
            updatedObjects.add(this.update(object));
        });

        return updatedObjects;
    }

    @Override
    @Transactional
    public void delete(Long id) {
        if (Objects.isNull(id)) {
            throw new BadArgumentException(NULL_OBJECT_PROVIDED);
        }

        this.beforeDelete(id);
        if (CancelableEntity.class.isAssignableFrom(persistentClass) || AuditableCancelableEntity.class.isAssignableFrom(persistentClass)) {
            T object = this.findById(id);
            ((CancelableEntity<Object>) object).setCheckCancel(true);
            ((CancelableEntity) object).setCancelDate(new Date());
            this.update(object);
        } else {
            repository().deleteById(id);
        }
        this.afterDelete(id);
    }

    @Override
    public void beforeDelete(Long id) {
        return;
    }

    @Override
    public void afterDelete(Long id) {
        return;
    }

    @Override
    public void beforeDelete(List<T> objects) {
        return;
    }

    @Override
    public void afterDelete(List<T> objects) {
        return;
    }

    @Override
    @Transactional
    public void delete(List<T> objects) {
        if (CollectionUtils.isEmpty(objects)) {
            throw new EmptyListException(EMPTY_OBJECT_LIST_PROVIDED);
        }
        this.beforeDelete(objects);
        repository().deleteAll(objects);
        this.afterDelete(objects);
    }

    @Override
    @Transactional(readOnly = true)
    public List<T> findAll() {
        if (ISASEntity.class.isAssignableFrom(persistentClass) && JpaPagingAndSortingSASRepository.class.isAssignableFrom(repository().getClass())) {
            log.warn("Find all give vulnerability to SAS entity...");
        }
        List<T> list = repository().findAll();
        if (CollectionUtils.isEmpty(list)) {
            return Collections.EMPTY_LIST;

        }

        return this.afterFindAll(list);
    }

    @Override
    @Transactional(readOnly = true)
    public List<T> findAll(Pageable pageable) {
        if (ISASEntity.class.isAssignableFrom(persistentClass) && JpaPagingAndSortingSASRepository.class.isAssignableFrom(repository().getClass())) {
            log.warn("Find all give vulnerability to SAS entity...");
        }

        Page<T> page = repository().findAll(pageable);
        if (page.isEmpty()) {
            return Collections.EMPTY_LIST;
        }

        return this.afterFindAll(page.getContent());
    }

    @Override
    public List<T> findAll(String domain) throws NotSupportedException {
        if (ISASEntity.class.isAssignableFrom(persistentClass) && JpaPagingAndSortingSASRepository.class.isAssignableFrom(repository().getClass())) {
            List<T> list = ((JpaPagingAndSortingSASRepository) repository()).findByDomainIgnoreCaseIn(Arrays.asList(DomainConstants.DEFAULT_DOMAIN_NAME, domain));
            if (CollectionUtils.isEmpty(list)) {
                return Collections.EMPTY_LIST;

            }
            return this.afterFindAll(list);
        } else {
            throw new NotSupportedException("find all by domain for :" + persistentClass.getSimpleName());
        }
    }

    @Override
    public List<T> findAll(String domain, Pageable pageable) throws NotSupportedException {
        if (ISASEntity.class.isAssignableFrom(persistentClass) && JpaPagingAndSortingSASRepository.class.isAssignableFrom(repository().getClass())) {
            Page<T> page = ((JpaPagingAndSortingSASRepository) repository()).findByDomainIgnoreCaseIn(Arrays.asList(DomainConstants.DEFAULT_DOMAIN_NAME, domain), pageable);
            if (page.isEmpty()) {
                return Collections.EMPTY_LIST;
            }

            return this.afterFindAll(page.getContent());
        } else {
            throw new NotSupportedException("find all by domain for :" + persistentClass.getSimpleName());
        }
    }

    @Override
    @Transactional(readOnly = true)
    public T findById(Long id) throws ObjectNotFoundException {
        if (Objects.isNull(id)) {
            throw new BadArgumentException(NULL_OBJECT_PROVIDED);
        }
        Optional<T> optional = repository().findById(id);
        if (optional.isPresent()) {
            return this.afterFindById(optional.get());
        }
        return null;
    }

    @Override
    @Transactional
    public T saveOrUpdate(T object) {
        if (Objects.isNull(object)) {
            throw new BadArgumentException(NULL_OBJECT_PROVIDED);
        }

        if (Objects.isNull(object.getId())) { // to create
            return this.create(object);
        } else { //to update
            return this.update(object);
        }
    }

    @Override
    @Transactional
    public List<T> saveOrUpdate(List<T> objects) {
        if (CollectionUtils.isEmpty(objects)) {
            throw new EmptyListException(EMPTY_OBJECT_LIST_PROVIDED);
        }

        List<T> updatedObjects = new ArrayList<>();
        objects.forEach(object -> {
            updatedObjects.add(this.saveOrUpdate(object));
        });

        return updatedObjects;
    }

    @Override
    public T beforeUpdate(T object) {
        return object;
    }

    @Override
    public T afterUpdate(T object) {
        return object;
    }

    @Override
    public List<T> afterFindAll(List<T> list) {
        return list;
    }

    @Override
    public T afterFindById(T object) {
        return object;
    }
}
