package eu.novobit.exception;
/**
 * @author novobit
 */


import eu.novobit.annotation.MsgLocale;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * The type User missing email exception.
 */
@ResponseStatus(HttpStatus.CONFLICT)
@MsgLocale("user.missing.email.exception")
public class UserMissingEmailException extends ManagedException {

    private static final long serialVersionUID = 1L;

    /**
     * Instantiates a new User missing email exception.
     *
     * @param message the message
     */
    public UserMissingEmailException(final String message) {
        super(message);
    }

}
