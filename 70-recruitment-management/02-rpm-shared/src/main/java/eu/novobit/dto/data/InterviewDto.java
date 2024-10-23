package eu.novobit.dto.data;


import eu.novobit.dto.AbstractAuditableDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;

/**
 * The type Interview dto.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class InterviewDto extends AbstractAuditableDto<Long> {

    private List<InterviewSkillsDto> skills;
    private String quizCode;
    private JobApplicationEventDto jobApplicationEvent;

}
