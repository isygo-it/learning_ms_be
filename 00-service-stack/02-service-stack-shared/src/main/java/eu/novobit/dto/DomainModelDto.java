package eu.novobit.dto;

import eu.novobit.enumerations.IEnumBinaryStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * The type Domain model dto.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class DomainModelDto extends AbstractAuditableDto<Long> {

    private String name;
    private String description;
    private String url;
    @Builder.Default
    private IEnumBinaryStatus.Types adminStatus = IEnumBinaryStatus.Types.ENABLED;
}
