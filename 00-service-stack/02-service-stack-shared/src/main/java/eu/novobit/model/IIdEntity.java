package eu.novobit.model;

import java.io.Serializable;

/**
 * The interface Id entity.
 *
 * @param <T> the type parameter
 */
public interface IIdEntity<T> extends Serializable {
    /**
     * Gets id.
     *
     * @return the id
     */
    public T getId();

    /**
     * Sets id.
     *
     * @param id the id
     */
    public void setId(T id);
}
