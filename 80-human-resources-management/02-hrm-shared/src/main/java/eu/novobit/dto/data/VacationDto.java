package eu.novobit.dto.data;

import eu.novobit.dto.AbstractAuditableDto;
import eu.novobit.enumerations.IEnumAbsenceType;
import eu.novobit.enumerations.IEnumAbseneceStatus;
import lombok.Data;

/**
 * The type Vacation dto.
 */
@Data

public class VacationDto extends AbstractAuditableDto<Long> {

    private String startDate;
    private String endDate;
    private Double leaveTaken;
    private Double recoveryLeaveTaken;
    private IEnumAbsenceType.Types absence;
    private IEnumAbseneceStatus.Types status;
    private String comment;
}
