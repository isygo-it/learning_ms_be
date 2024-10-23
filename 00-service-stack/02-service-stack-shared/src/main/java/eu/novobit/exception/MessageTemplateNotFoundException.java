package eu.novobit.exception;

import eu.novobit.annotation.MsgLocale;


/**
 * The type Message template not found exception.
 */
@MsgLocale("message.template.not.found.exception")
public class MessageTemplateNotFoundException extends ManagedException {

    /**
     * Instantiates a new Message template not found exception.
     *
     * @param message the message
     */
    public MessageTemplateNotFoundException(String message) {
        super(message);
    }

    /**
     * Instantiates a new Message template not found exception.
     *
     * @param throwable the throwable
     */
    public MessageTemplateNotFoundException(Throwable throwable) {
        super(throwable);
    }

    /**
     * Instantiates a new Message template not found exception.
     *
     * @param message the message
     * @param cause   the cause
     */
    public MessageTemplateNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
