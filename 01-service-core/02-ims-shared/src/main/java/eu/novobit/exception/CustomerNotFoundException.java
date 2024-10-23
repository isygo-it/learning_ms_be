package eu.novobit.exception;


import eu.novobit.annotation.MsgLocale;


/**
 * The type Customer not found exception.
 */
@MsgLocale("customer.not.found.exception")
public class CustomerNotFoundException extends ManagedException {

    /**
     * Instantiates a new Customer not found exception.
     *
     * @param s the s
     */
    public CustomerNotFoundException(String s) {
        super(s);
    }
}
