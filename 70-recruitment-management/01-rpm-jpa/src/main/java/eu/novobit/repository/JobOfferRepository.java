package eu.novobit.repository;

import eu.novobit.model.JobOffer;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * The interface Job offer repository.
 */
@Repository
public interface JobOfferRepository extends JpaPagingAndSortingSASCodifiableRepository<JobOffer, Long> {


    /**
     * Find job offers not assigned to resume list.
     *
     * @param resumeCode the resume code
     * @return the list
     */
    @Query(name = "JobOffer.findJobOffersNotAssignedToResume", nativeQuery = true)
    List<JobOffer> findJobOffersNotAssignedToResume(@Param("resumeCode") String resumeCode);

    /**
     * Find by check cancel false list.
     *
     * @return the list
     */
    List<JobOffer> findByCheckCancelFalse();

    @Query(name = "JobOffer.countActiveJobs", nativeQuery = true)
    long countByDomainAndDeadLine(@Param("domain") String domain);

    @Query(name = "JobOffer.countExpiredJobs", nativeQuery = true)
    long countByDomainAndExpiredDeadLine(@Param("domain") String domain);

    long countByDomainAndCancelDateNull(String domain);

}
