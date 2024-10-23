package eu.novobit.exception;


import eu.novobit.annotation.MsgLocale;


/**
 * The type Board item not found exception.
 */
@MsgLocale("boardItem.not.found.exception")
public class BoardItemNotFoundException extends ManagedException {

    /**
     * Instantiates a new Board item not found exception.
     *
     * @param s the s
     */
    public BoardItemNotFoundException(String s) {
    }
}
