package eu.novobit.dto.data;

import eu.novobit.dto.AbstractAuditableDto;
import eu.novobit.enumerations.IEnumPrimeType;
import lombok.Data;

import java.util.Set;

/**
 * The type Prime dto.
 */
@Data

public class PrimeDto extends AbstractAuditableDto<Long> {

    private IEnumPrimeType.Types primeType;
    private Double annualMaxAmount;
    private Double annualMinAmount;
    private Integer annualFrequency;
    private Set<DueDateDto> dueDates;
}
