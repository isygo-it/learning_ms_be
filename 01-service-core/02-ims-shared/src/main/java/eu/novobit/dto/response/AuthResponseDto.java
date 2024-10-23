package eu.novobit.dto.response;


import eu.novobit.dto.AbstractAuditableDto;
import eu.novobit.dto.SystemInfoDto;
import eu.novobit.dto.data.ThemeDto;
import eu.novobit.enumerations.IEnumWebToken;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * The type Auth response dto.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class AuthResponseDto extends AbstractAuditableDto<Long> {

    @NotNull
    private IEnumWebToken.Types tokenType;
    @NotEmpty
    private String accessToken;
    @NotEmpty
    private String refreshToken;
    @NotEmpty
    private String authorityToken;

    private UserDataResponseDto userDataResponseDto;
    private SystemInfoDto systemInfo;
    private ThemeDto theme;
}
