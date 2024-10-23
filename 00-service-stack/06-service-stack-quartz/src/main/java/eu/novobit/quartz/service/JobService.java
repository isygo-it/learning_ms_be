package eu.novobit.quartz.service;

import org.quartz.JobExecutionContext;

/**
 * The interface Job service.
 */
public interface JobService {

    /**
     * Process.
     *
     * @param context the context
     */
    public void process(JobExecutionContext context);

    /**
     * Perform job.
     *
     * @param context the context
     */
    public void performJob(JobExecutionContext context);
}
