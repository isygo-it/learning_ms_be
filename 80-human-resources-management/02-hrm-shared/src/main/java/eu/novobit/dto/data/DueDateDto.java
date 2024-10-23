package eu.novobit.dto.data;

import lombok.Data;

import java.time.LocalDate;

/**
 * The type Due date dto.
 */
@Data

public class DueDateDto {


    private Double amount;
    private LocalDate paymentDate;
    private Boolean isPayed;
}
