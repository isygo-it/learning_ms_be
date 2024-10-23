package eu.novobit.annotation;

import eu.novobit.com.rest.service.ICrudBasicsService;
import eu.novobit.exception.handler.ExceptionHandler;
import eu.novobit.mapper.EntityMapper;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * The interface Ctrl def.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
public @interface CtrlDef {
    /**
     * Handler class.
     *
     * @return the class
     */
    Class<? extends ExceptionHandler> handler(); // Data Exception Handler class

    /**
     * Mapper class.
     *
     * @return the class
     */
    Class<? extends EntityMapper> mapper(); // entity mapper class

    /**
     * Service class.
     *
     * @return the class
     */
    Class<? extends ICrudBasicsService> service(); // eu.novobit.eu.novobit.repository eu.novobit.service class
}
