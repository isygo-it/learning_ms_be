package eu.novobit.dto.data;

import eu.novobit.dto.AbstractAuditableDto;
import eu.novobit.dto.IImageUploadDto;
import lombok.Data;

import java.time.LocalDate;

/**
 * The type Cin dto.
 */
@Data

public class CinDto extends AbstractAuditableDto<Long> implements IImageUploadDto {

    private String cardNumber;
    private LocalDate issuedDate;
    private String issuedPlace;
    private String imagePath;
    private String domain;
    private Long employeeDetailsId;
    private String code;

}
