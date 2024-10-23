package eu.novobit.dto.data;

import eu.novobit.dto.AbstractAuditableDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * The type Employee linked file dto.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class EmployeeLinkedFileDto extends AbstractAuditableDto<Long> {
    private String originalFileName;
    private Long size;
}
