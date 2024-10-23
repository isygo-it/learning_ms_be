package eu.novobit.repository;


import eu.novobit.model.JobTemplate;

/**
 * The interface Job template repository.
 */
public interface JobTemplateRepository extends JpaPagingAndSortingSASRepository<JobTemplate, Long> {

    /**
     * Delete job template by job offer id long.
     *
     * @param jobId the job id
     * @return the long
     */
    Long deleteJobTemplateByJobOffer_Id(Long jobId);
}
