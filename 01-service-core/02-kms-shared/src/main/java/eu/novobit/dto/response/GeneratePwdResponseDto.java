package eu.novobit.dto.response;


import eu.novobit.dto.AbstractAuditableDto;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * The type Generate pwd response dto.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class GeneratePwdResponseDto extends AbstractAuditableDto<Long> {

    @NotEmpty
    private String password;
}
