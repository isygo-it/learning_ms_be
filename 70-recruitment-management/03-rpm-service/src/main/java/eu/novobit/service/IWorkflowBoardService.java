package eu.novobit.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import eu.novobit.com.rest.service.ICrudServiceMethods;
import eu.novobit.dto.AccountModelDto;
import eu.novobit.dto.BpmEventRequestDto;
import eu.novobit.dto.BpmEventResponseDto;
import eu.novobit.dto.data.JobApplicationInterviewEventRequestDto;
import eu.novobit.model.IBoardItem;
import eu.novobit.model.WorkflowBoard;
import eu.novobit.model.WorkflowState;

import java.util.List;

/**
 * The interface Workflow board service.
 */
public interface IWorkflowBoardService extends ICrudServiceMethods<WorkflowBoard> {

    /**
     * Gets states.
     *
     * @param wbCode the wb code
     * @return the states
     */
    List<WorkflowState> getStates(String wbCode);

    /**
     * Gets items.
     *
     * @param domain the domain
     * @param wbCode the wb code
     * @return the items
     * @throws ClassNotFoundException the class not found exception
     */
    List<IBoardItem> getItems(String domain, String wbCode) throws ClassNotFoundException;

    /**
     * Gets interview event.
     *
     * @param domain the domain
     * @param code   the code
     * @param id     the id
     * @return the interview event
     */
    JobApplicationInterviewEventRequestDto getInterviewEvent(String domain, String code, Long id);

    /**
     * Add interview event job application interview event request dto.
     *
     * @param domain    the domain
     * @param code      the code
     * @param eventType the event type
     * @param event     the event
     * @return the job application interview event request dto
     */
    JobApplicationInterviewEventRequestDto addInterviewEvent(String domain, String code, String eventType, JobApplicationInterviewEventRequestDto event);

    /**
     * Edit interview event job application interview event request dto.
     *
     * @param code      the code
     * @param eventType the event type
     * @param event     the event
     * @return the job application interview event request dto
     */
    JobApplicationInterviewEventRequestDto editInterviewEvent(String code, String eventType, JobApplicationInterviewEventRequestDto event);

    /**
     * Gets board watchers by workflow.
     *
     * @param workflowCode the workflow code
     * @param domain       the domain
     * @return the board watchers by workflow
     */
    List<String> getBoardWatchersByWorkflow(String workflowCode, String domain);

    /**
     * Gets item types.
     *
     * @return the item types
     */
    List<String> getItemTypes();

    /**
     * Perform event bpm event response dto.
     *
     * @param bpmEventRequest the bpm event request
     * @return the bpm event response dto
     * @throws ClassNotFoundException  the class not found exception
     * @throws JsonProcessingException the json processing exception
     */
    BpmEventResponseDto performEvent(BpmEventRequestDto bpmEventRequest) throws ClassNotFoundException, JsonProcessingException;

    /**
     * Gets candidate data.
     *
     * @param code the code
     * @return the candidate data
     */
    AccountModelDto getCandidateData(String code);
}
