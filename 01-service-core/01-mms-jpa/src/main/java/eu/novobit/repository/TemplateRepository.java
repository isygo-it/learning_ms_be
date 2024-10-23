package eu.novobit.repository;

import eu.novobit.enumerations.IEnumTemplateName;
import eu.novobit.model.Template;

import java.util.Optional;


/**
 * The interface Template repository.
 */
public interface TemplateRepository extends JpaPagingAndSortingSASCodifiableRepository<Template, Long> {

    /**
     * Find by domain ignore case and name optional.
     *
     * @param domain the domain
     * @param name   the name
     * @return the optional
     */
    Optional<Template> findByDomainIgnoreCaseAndName(String domain, IEnumTemplateName.Types name);
}
