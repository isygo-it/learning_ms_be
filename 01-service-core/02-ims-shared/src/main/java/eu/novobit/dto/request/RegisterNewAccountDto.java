package eu.novobit.dto.request;

import eu.novobit.constants.DomainConstants;
import eu.novobit.dto.AbstractAuditableDto;
import eu.novobit.enumerations.IEnumAccountOrigin;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * The type Register new account dto.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class RegisterNewAccountDto extends AbstractAuditableDto<Long> {

    @Builder.Default
    private String domain = DomainConstants.DEFAULT_DOMAIN_NAME;
    @Builder.Default
    private String origin = IEnumAccountOrigin.Types.SYS_ADMIN.name();

    @NotEmpty
    private String firstName;
    @NotEmpty
    private String lastName;
    @NotEmpty
    private String email;
    @NotEmpty
    private String phoneNumber;
}
