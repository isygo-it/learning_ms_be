package eu.novobit.dto.request;


import eu.novobit.dto.AbstractAuditableDto;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * The type Update account request dto.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class UpdateAccountRequestDto extends AbstractAuditableDto<Long> {

    @NotEmpty
    private String domain;
    @NotEmpty
    private String code;
    @NotEmpty
    private String email;
    @NotEmpty
    private String fullName;
}
