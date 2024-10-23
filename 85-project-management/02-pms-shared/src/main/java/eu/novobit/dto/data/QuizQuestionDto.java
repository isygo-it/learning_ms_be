package eu.novobit.dto.data;

import eu.novobit.dto.AbstractAuditableDto;
import eu.novobit.enumerations.IEnumQuestionType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.SuperBuilder;

import java.util.List;

/**
 * The type Quiz question dto.
 */
@Data
@AllArgsConstructor
@SuperBuilder
public class QuizQuestionDto extends AbstractAuditableDto<Long> {

    private String question;
    private IEnumQuestionType.Types type;
    private Integer order;
    private List<QuizOptionDto> options;
    private String textAnswer;
}
