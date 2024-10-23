package eu.novobit.dto.data;

import eu.novobit.constants.AccountTypeConstants;
import eu.novobit.dto.AbstractAuditableDto;
import eu.novobit.dto.IImageUploadDto;
import eu.novobit.enumerations.IEnumBinaryStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * The type Min employee dto.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class MinEmployeeDto extends AbstractAuditableDto<Long> implements IImageUploadDto {

    private String domain;
    private String code;
    private String firstName;
    private String lastName;
    private String email;
    private String imagePath;
    private Integer numberActiveContracts;
    @Builder.Default
    private String functionRole = AccountTypeConstants.DOMAIN_USER;
    @Builder.Default
    private IEnumBinaryStatus.Types employeeStatus = IEnumBinaryStatus.Types.ENABLED;
}
