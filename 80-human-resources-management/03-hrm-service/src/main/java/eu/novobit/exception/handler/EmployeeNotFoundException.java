package eu.novobit.exception.handler;


import eu.novobit.annotation.MsgLocale;
import eu.novobit.exception.ManagedException;


/**
 * The type employee not found exception.
 */
@MsgLocale("employee.not.found.exception")
public class EmployeeNotFoundException extends ManagedException {

    /**
     * Instantiates a new employee not found exception.
     *
     * @param s the s
     */
    public EmployeeNotFoundException(String s) {
    }
}
