package eu.novobit.annotation;

import eu.novobit.exception.handler.ExceptionHandler;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * The interface Ctrl handler.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
public @interface CtrlHandler {
    /**
     * Value class.
     *
     * @return the class
     */
    Class<? extends ExceptionHandler> value(); // Data Exception Handler class
}
