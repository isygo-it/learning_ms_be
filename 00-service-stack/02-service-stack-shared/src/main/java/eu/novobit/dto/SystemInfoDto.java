package eu.novobit.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * The type System info dto.
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class SystemInfoDto extends AbstractAuditableDto<Long> {

    private String name;
    private String version;
}
