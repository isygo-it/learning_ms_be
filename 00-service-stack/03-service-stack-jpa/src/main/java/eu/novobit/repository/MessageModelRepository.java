package eu.novobit.repository;

import eu.novobit.annotation.IgnoreRepository;
import eu.novobit.model.IIdEntity;
import eu.novobit.model.extendable.LocaleMessageModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * The interface Message model repository.
 *
 * @param <T>  the type parameter
 * @param <ID> the type parameter
 */
@IgnoreRepository
public interface MessageModelRepository<T extends IIdEntity, ID> extends JpaRepository<T, ID> {

    /**
     * Find by code and locale optional.
     *
     * @param code   the code
     * @param locale the locale
     * @return the optional
     */
    Optional<LocaleMessageModel> findByCodeAndLocale(String code, String locale);
}
