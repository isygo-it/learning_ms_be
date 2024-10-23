package eu.novobit.dto.data;

import eu.novobit.dto.AbstractAuditableDto;
import eu.novobit.dto.IImageUploadDto;
import lombok.Data;

import java.time.LocalDate;

/**
 * The type Passport dto.
 */
@Data

public class PassportDto extends AbstractAuditableDto<Long> implements IImageUploadDto {

    private String cardNumber;
    private LocalDate issuedDate;
    private LocalDate expiredDate;
    private String issuedPlace;
    private String imagePath;
    private String domain;
    private Long employeeDetailsId;
    private String code;

}
