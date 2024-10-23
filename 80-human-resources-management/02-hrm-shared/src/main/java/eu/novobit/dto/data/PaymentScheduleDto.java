package eu.novobit.dto.data;

import eu.novobit.dto.AbstractAuditableDto;
import lombok.Data;

import java.time.LocalDate;
import java.util.Date;

/**
 * The type Prime dto.
 */
@Data

public class PaymentScheduleDto extends AbstractAuditableDto<Long> {

    private Boolean isSubmited;
    private Date submitDate;
    private LocalDate dueDate;
    private Double paymentGrossAmount;
    private Double paymentNetAmount;


}
