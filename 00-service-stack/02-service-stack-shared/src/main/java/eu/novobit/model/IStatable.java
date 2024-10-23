package eu.novobit.model;

/**
 * The interface Statable.
 *
 * @param <S> the type parameter
 */
public interface IStatable<S> {

    /**
     * Gets state.
     *
     * @return the state
     */
    public S getState();

    /**
     * Sets state.
     *
     * @param state the state
     */
    public void setState(S state);
}

