package eu.novobit.dto.data;

import eu.novobit.dto.AbstractAuditableDto;
import eu.novobit.enumerations.IEnumJobAppEventType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.Date;
import java.util.List;

/**
 * The type Job application interview event request dto.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class JobApplicationInterviewEventRequestDto extends AbstractAuditableDto<Long> {

    private String domain;
    private String title;
    private IEnumJobAppEventType.Types type;
    private Date startDateTime;
    private Date endDateTime;
    private String location;
    private List<String> participants;
    private List<InterviewSkillsDto> skills;
    private String quizCode;
    private String comment;
    private CandidateDto candidate;
}
