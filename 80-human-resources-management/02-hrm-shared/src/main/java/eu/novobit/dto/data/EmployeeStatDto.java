package eu.novobit.dto.data;


import eu.novobit.dto.AbstractDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;

/**
 * The type Employee stat dto.
 */
@Data
@AllArgsConstructor
@SuperBuilder
public class EmployeeStatDto extends AbstractDto<Long> {
    private Integer contractCount;
    private LocalDate activeContractEndDate;
    private LocalDate activeContractAnniversaryDate;
    private LocalDate nextBonnusDate;
}
