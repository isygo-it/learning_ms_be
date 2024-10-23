package eu.novobit.dto.data;


import eu.novobit.dto.AbstractAuditableDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * The type Asso account dto.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class AssoAccountDto extends AbstractAuditableDto<Long> {

    private String code;
    private String origin;
}
