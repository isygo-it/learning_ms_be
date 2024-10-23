package eu.novobit.repository;

import eu.novobit.model.JobApplication;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * The interface Job application repository.
 */
public interface JobApplicationRepository extends JpaPagingAndSortingSASCodifiableRepository<JobApplication, Long> {
    /**
     * Cancel job application long.
     *
     * @param resumeId the resume id
     * @return the long
     */
    @Modifying
    @Query(name = "JobApplication.cancel", nativeQuery = true)
    Long cancelJobApplication(@Param("resumeId") Long resumeId);

    @Query(name = "JobApplication.countNumberApplicationsByJob", nativeQuery = true)
    Long countJobsByNumberOfApplications(@Param("code") String code, @Param("domain") String domain);

    @Query(name = "JobApplication.countOngoingGlobalJobApplication", nativeQuery = true)
    Long countOngoingGlobalJobApplication(@Param("domain") String domain);

    @Query(name = "JobApplication.countOnGointApplicationsByResume", nativeQuery = true)
    Long countOnGoingJobApplicationByResume(@Param("domain") String domain, @Param("code") String code);

    Long countByResumeCodeAndDomain(String code, String domain);

}
