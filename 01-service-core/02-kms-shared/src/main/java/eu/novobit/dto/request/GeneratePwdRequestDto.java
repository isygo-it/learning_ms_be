package eu.novobit.dto.request;


import eu.novobit.dto.AbstractAuditableDto;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * The type Generate pwd request dto.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class GeneratePwdRequestDto extends AbstractAuditableDto<Long> {

    @NotEmpty
    private String domain;
    private String domainUrl;
    @NotEmpty
    private String email;
    @NotEmpty
    private String userName;
    @NotEmpty
    private String fullName;
}
