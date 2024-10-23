package eu.novobit.service.impl;

import eu.novobit.com.camel.repository.ICamelRepository;
import eu.novobit.dto.data.JobApplicationDto;
import eu.novobit.dto.data.TimelineDto;
import eu.novobit.enumerations.IEnumActionEvent;
import eu.novobit.model.ITLEntity;
import eu.novobit.model.JobApplication;
import eu.novobit.model.Resume;
import eu.novobit.service.ITimeLineListenerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


/**
 * The type Time line listener service.
 */
@Component
public class TimeLineListenerService implements ITimeLineListenerService {
    @Autowired
    private ICamelRepository camelRepository;

    @Override
    public void performPostPersistTL(ITLEntity entity) {
        handleEntityEvent(entity, IEnumActionEvent.Types.PERSIST);
    }

    @Override
    public void performPostRemoveTL(ITLEntity entity) {
        handleEntityEvent(entity, IEnumActionEvent.Types.REMOVE);
    }

    @Override
    public void performPostUpdateTL(ITLEntity entity) {
        handleEntityEvent(entity, IEnumActionEvent.Types.UPDATE);
    }

    private void handleEntityEvent(ITLEntity entity, IEnumActionEvent.Types actionType) {
        if (entity instanceof Resume) {
            handleResumeEventAsync((Resume) entity, actionType);
        } else if (entity instanceof JobApplication) {
            handleJobApplicationEventAsync((JobApplication) entity, actionType);
        }
    }

    private void handleResumeEventAsync(Resume resume, IEnumActionEvent.Types actionType) {
        camelRepository.asyncSendBody(ICamelRepository.timeline_queue,
                TimelineDto.builder()
                        .domain(resume.getDomain())
                        .code(resume.getCode())
                        .action(actionType)
                        .object(resume.getClass().getCanonicalName())
                        .parentCode(resume.getCode())
                        .build()
        );
    }

    private void handleJobApplicationEventAsync(JobApplication jobApplication, IEnumActionEvent.Types actionType) {
        camelRepository.asyncSendBody(ICamelRepository.timeline_queue,
                JobApplicationDto.builder()
                        .domain(jobApplication.getDomain())
                        .code(jobApplication.getCode())
                        .build()
        );
    }
}

