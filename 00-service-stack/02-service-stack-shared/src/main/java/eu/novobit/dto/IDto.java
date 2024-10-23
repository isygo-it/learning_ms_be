package eu.novobit.dto;


import eu.novobit.model.IIdEntity;

/**
 * The interface Dto.
 *
 * @param <T> the type parameter
 */
public interface IDto<T> extends IIdEntity<T> {

    /**
     * Gets section name.
     *
     * @return the section name
     */
    public String getSectionName();

    /**
     * Is empty boolean.
     *
     * @return the boolean
     */
    public boolean isEmpty();
}
