package eu.novobit.dto.data;


import eu.novobit.dto.AbstractAuditableDto;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * The type V calendar dto.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class VCalendarDto extends AbstractAuditableDto<Long> {

    @NotEmpty
    private String domain;
    @NotEmpty
    private String name;

    private String icsPath;
}
