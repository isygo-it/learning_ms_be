package eu.novobit.annotation;

import eu.novobit.com.rest.api.ILinkedFileApi;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * The interface Lnk file service.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
public @interface LnkFileService {

    /**
     * Value class.
     *
     * @return the class
     */
    Class<? extends ILinkedFileApi> value();
}
