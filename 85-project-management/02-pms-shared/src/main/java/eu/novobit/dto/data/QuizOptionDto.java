package eu.novobit.dto.data;

import eu.novobit.dto.AbstractAuditableDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.SuperBuilder;

/**
 * The type Quiz option dto.
 */
@Data
@AllArgsConstructor
@SuperBuilder
public class QuizOptionDto extends AbstractAuditableDto<Long> {

    private String option;
    private Boolean checked;
}
