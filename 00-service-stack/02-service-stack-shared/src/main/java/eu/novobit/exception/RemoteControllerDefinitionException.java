package eu.novobit.exception;

import eu.novobit.annotation.MsgLocale;


/**
 * The type Remote controller definition exception.
 */
@MsgLocale("remote.controller.not.defined")
public class RemoteControllerDefinitionException extends ManagedException {

    /**
     * Instantiates a new Remote controller definition exception.
     *
     * @param message the message
     */
    public RemoteControllerDefinitionException(String message) {
        super(message);
    }
}
