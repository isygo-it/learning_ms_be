package eu.novobit.dto.data;


import eu.novobit.dto.AbstractDto;
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
public class CandidateDto extends AbstractDto<Long> {

    private String code;
    private String accountCode;
    @NotEmpty
    private String email;
    private String fullName;
    @Builder.Default
    private String origin = IEnumAccountOrigin.Types.SYS_ADMIN.name();
}
