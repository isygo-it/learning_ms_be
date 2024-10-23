package eu.novobit.exception;


/**
 * The interface Email validator.
 */
public interface EmailValidator {

    /**
     * Validate boolean.
     *
     * @param email the email
     * @return the boolean
     */
    public boolean validate(String email);

}
