package eu.novobit.annotation;

import org.springframework.data.jpa.repository.JpaRepository;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * The interface Srv repo.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
public @interface SrvRepo {
    /**
     * Value class.
     *
     * @return the class
     */
    Class<? extends JpaRepository> value();
}
