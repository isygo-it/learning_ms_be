package eu.novobit.dto.data;

import eu.novobit.dto.AbstractAuditableDto;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;


/**
 * The type Property dto.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class PropertyDto extends AbstractAuditableDto<Long> {

    private String guiName;
    @NotEmpty
    private String name;

    private String value;

}

