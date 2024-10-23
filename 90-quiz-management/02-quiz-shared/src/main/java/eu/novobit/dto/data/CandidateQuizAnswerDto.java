package eu.novobit.dto.data;

import eu.novobit.dto.AbstractAuditableDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * The type Candidate quiz answer dto.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class CandidateQuizAnswerDto extends AbstractAuditableDto<Long> {

    private Long question;
    private Long option;
    private String answerText;

    @Builder.Default
    private Double score = 0D;
}
