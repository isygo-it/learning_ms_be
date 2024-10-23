package eu.novobit.exception;

import eu.novobit.annotation.MsgLocale;


/**
 * The type Role not defined exception.
 */
@MsgLocale("role.not.defined.exception")
public class RoleNotDefinedException extends ManagedException {

    /**
     * Instantiates a new Role not defined exception.
     *
     * @param message the message
     */
    public RoleNotDefinedException(String message) {
        super(message);
    }
}
