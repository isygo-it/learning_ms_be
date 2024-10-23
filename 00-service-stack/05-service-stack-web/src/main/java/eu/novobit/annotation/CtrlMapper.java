package eu.novobit.annotation;

import eu.novobit.mapper.EntityMapper;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * The interface Ctrl mapper.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
public @interface CtrlMapper {
    /**
     * Value class.
     *
     * @return the class
     */
    Class<? extends EntityMapper> value(); // entity mapper class
}
