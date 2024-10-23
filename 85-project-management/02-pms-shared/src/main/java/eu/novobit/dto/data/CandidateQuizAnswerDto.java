package eu.novobit.dto.data;

import eu.novobit.dto.AbstractAuditableDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.SuperBuilder;

/**
 * The type Candidate quiz answer dto.
 */
@Data
@AllArgsConstructor
@SuperBuilder
public class CandidateQuizAnswerDto extends AbstractAuditableDto<Long> {

    private Long question;
    private Long option;
    private String answerText;
}
