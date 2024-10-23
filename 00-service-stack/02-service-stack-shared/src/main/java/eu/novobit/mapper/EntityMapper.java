package eu.novobit.mapper;


import eu.novobit.dto.IDto;
import eu.novobit.model.IIdEntity;

import java.util.List;
import java.util.Set;

/**
 * The interface Entity mapper.
 *
 * @param <T> the type parameter
 * @param <D> the type parameter
 */
public interface EntityMapper<T extends IIdEntity, D extends IDto> {

    /**
     * Dto to entity t.
     *
     * @param object the object
     * @return the t
     */
    public T dtoToEntity(D object);

    /**
     * Entity to dto d.
     *
     * @param object the object
     * @return the d
     */
    public D entityToDto(T object);

    /**
     * List dto to entity list.
     *
     * @param list the list
     * @return the list
     */
    public List<T> listDtoToEntity(List<D> list);

    /**
     * List dto to entity set.
     *
     * @param list the list
     * @return the set
     */
    public Set<T> listDtoToEntity(Set<D> list);

    /**
     * List entity to dto list.
     *
     * @param list the list
     * @return the list
     */
    public List<D> listEntityToDto(List<T> list);

    /**
     * List entity to dto set.
     *
     * @param list the list
     * @return the set
     */
    public Set<D> listEntityToDto(Set<T> list);

}
