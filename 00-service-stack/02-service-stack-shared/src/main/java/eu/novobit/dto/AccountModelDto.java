package eu.novobit.dto;


import eu.novobit.enumerations.IEnumAccountOrigin;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * The type Account model dto.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class AccountModelDto extends AbstractAuditableDto<Long> {

    private String code;
    @NotEmpty
    private String email;
    private String fullName;
    @Builder.Default
    private String origin = IEnumAccountOrigin.Types.SYS_ADMIN.name();
}
