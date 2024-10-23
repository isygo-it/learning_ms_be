package eu.novobit.annotation;

import eu.novobit.com.rest.service.ICrudCodifiableService;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * The interface Ctrl service.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
public @interface CtrlService {
    /**
     * Value class.
     *
     * @return the class
     */
    Class<? extends ICrudCodifiableService> value(); // eu.novobit.eu.novobit.repository eu.novobit.service class
}
