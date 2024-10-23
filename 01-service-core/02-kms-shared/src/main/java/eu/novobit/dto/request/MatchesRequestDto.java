package eu.novobit.dto.request;


import eu.novobit.dto.AbstractAuditableDto;
import eu.novobit.enumerations.IEnumAuth;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * The type Matches request dto.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class MatchesRequestDto extends AbstractAuditableDto<Long> {

    @NotEmpty
    private String domain;
    @NotEmpty
    private String userName;
    @NotEmpty
    private String password;
    @NotNull
    private IEnumAuth.Types authType;
}