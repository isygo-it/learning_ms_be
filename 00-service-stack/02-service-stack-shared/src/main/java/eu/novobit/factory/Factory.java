package eu.novobit.factory;

/**
 * The interface Factory.
 *
 * @param <T> the type parameter
 */
public interface Factory<T> {
    /**
     * Instance t.
     *
     * @return the t
     */
    public T instance();

    /**
     * New instance t.
     *
     * @return the t
     */
    public T newInstance();
}
