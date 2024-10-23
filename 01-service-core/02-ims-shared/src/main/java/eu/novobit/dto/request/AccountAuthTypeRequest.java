package eu.novobit.dto.request;

import eu.novobit.dto.AbstractAuditableDto;
import eu.novobit.enumerations.IEnumAuth;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * The type Switch auth type request.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class AccountAuthTypeRequest extends AbstractAuditableDto<Long> {

    @NotEmpty
    private String domain;
    @NotEmpty
    private String userName;

    private IEnumAuth.Types authType;
}
