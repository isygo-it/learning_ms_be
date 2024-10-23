package eu.novobit.repository;

import eu.novobit.model.MailMessage;

import java.util.List;

/**
 * The interface Mail message repository.
 */
public interface MailMessageRepository extends JpaPagingAndSortingRepository<MailMessage, Long> {
    /**
     * Find all by domain ignore case list.
     *
     * @param domain the domain
     * @return the list
     */
    List<MailMessage> findAllByDomainIgnoreCase(String domain);
}
