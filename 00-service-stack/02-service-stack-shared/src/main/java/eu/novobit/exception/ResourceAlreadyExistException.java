package eu.novobit.exception;
/**
 * @author novobit
 */

import eu.novobit.annotation.MsgLocale;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * The type Resource already exist exception.
 */
@ResponseStatus(HttpStatus.CONFLICT)
@MsgLocale("ressource.already.exist.exception")
public class ResourceAlreadyExistException extends ManagedException {

    /**
     * Instantiates a new Resource already exist exception.
     *
     * @param s the s
     */
    public ResourceAlreadyExistException(String s) {
    }
}
