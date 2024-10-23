package eu.novobit.dto.data;

import eu.novobit.dto.AbstractAuditableDto;
import eu.novobit.enumerations.IEnumActionEvent;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * The type Timeline dto.
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class TimelineDto extends AbstractAuditableDto<Long> {

    private String domain;
    private IEnumActionEvent.Types action;
    private String code;
    private String object;
    private String parentCode;
}

