package eu.novobit.dto.data;


import eu.novobit.constants.AccountTypeConstants;
import eu.novobit.dto.AccountModelDto;
import eu.novobit.dto.IImageUploadDto;
import eu.novobit.enumerations.IEnumAccountSystemStatus;
import eu.novobit.enumerations.IEnumAuth;
import eu.novobit.enumerations.IEnumBinaryStatus;
import eu.novobit.enumerations.IEnumLanguage;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;

/**
 * The type Account dto.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class AccountDto extends AccountModelDto implements IImageUploadDto {

    @NotEmpty
    private String domain;

    private IEnumLanguage.Types language;
    @Builder.Default
    private IEnumBinaryStatus.Types adminStatus = IEnumBinaryStatus.Types.ENABLED;
    @Builder.Default
    private IEnumAccountSystemStatus.Types systemStatus = IEnumAccountSystemStatus.Types.IDLE;
    private AccountDetailsDto accountDetails;
    private List<RoleInfoDto> roleInfo;
    @Builder.Default
    private String functionRole = AccountTypeConstants.DOMAIN_USER;
    private String imagePath;
    private String phoneNumber;
    private Boolean isAdmin;
    private IEnumAuth.Types authType;
    private List<ConnectionTrackingDto> connectionTrackings;
    @Builder.Default
    private String accountType = AccountTypeConstants.DOMAIN_USER;
}
