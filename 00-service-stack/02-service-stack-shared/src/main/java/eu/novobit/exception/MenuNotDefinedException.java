package eu.novobit.exception;

import eu.novobit.annotation.MsgLocale;


/**
 * The type Menu not defined exception.
 */
@MsgLocale("menu.not.defined.exception")
public class MenuNotDefinedException extends ManagedException {

    /**
     * Instantiates a new Menu not defined exception.
     *
     * @param message the message
     */
    public MenuNotDefinedException(String message) {
        super(message);
    }
}
