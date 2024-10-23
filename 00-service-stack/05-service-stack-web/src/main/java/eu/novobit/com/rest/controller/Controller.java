package eu.novobit.com.rest.controller;

import eu.novobit.exception.BeanNotFoundException;
import eu.novobit.exception.ExceptionHandlerNotDefinedException;
import eu.novobit.exception.handler.ExceptionHandler;
import org.springframework.http.ResponseEntity;

/**
 * The interface Controller.
 */
public interface Controller {

    /**
     * Exception handler exception handler.
     *
     * @return the exception handler
     * @throws BeanNotFoundException               the bean not found exception
     * @throws ExceptionHandlerNotDefinedException the exception handler not defined exception
     */
    ExceptionHandler exceptionHandler() throws BeanNotFoundException, ExceptionHandlerNotDefinedException;

    /**
     * Gets back exception response.
     *
     * @param e the e
     * @return the back exception response
     */
    ResponseEntity getBackExceptionResponse(Throwable e);
}
