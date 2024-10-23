package eu.novobit.dto.data;

import eu.novobit.dto.AbstractAuditableDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * The type Job share info dto.
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class JobShareInfoDto extends AbstractAuditableDto<Long> {

    private String sharedWith;
    private Integer rate;
    private String comment;
}
