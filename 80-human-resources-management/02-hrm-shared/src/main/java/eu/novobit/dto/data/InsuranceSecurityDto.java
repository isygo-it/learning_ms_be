package eu.novobit.dto.data;

import eu.novobit.dto.AbstractAuditableDto;
import eu.novobit.dto.IImageUploadDto;
import eu.novobit.enumerations.IEnumInsuranceType;
import lombok.Data;

import java.time.LocalDate;

/**
 * The type Insurance security dto.
 */
@Data

public class InsuranceSecurityDto extends AbstractAuditableDto<Long> implements IImageUploadDto {

    private String cardNumber;
    private LocalDate issuedDate;
    private LocalDate expiredDate;
    private String issuedPlace;
    private IEnumInsuranceType.Types insuranceType;
    private String imagePath;
    private String domain;
    private Long employeeDetailsId;
    private String code;

}
