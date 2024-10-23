package eu.novobit.dto.data;

import eu.novobit.dto.AbstractAuditableDto;
import eu.novobit.enumerations.IEnumSkillLevelType;
import eu.novobit.enumerations.IEnumSkillType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * The type Job skills dto.
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class JobSkillsDto extends AbstractAuditableDto<Long> {
    private Long id;
    private IEnumSkillType.Types type;
    private String name;
    private IEnumSkillLevelType.Types level;
    private Boolean isMandatory;
}
