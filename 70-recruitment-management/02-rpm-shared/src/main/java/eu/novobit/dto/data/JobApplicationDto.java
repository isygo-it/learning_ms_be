package eu.novobit.dto.data;

import eu.novobit.dto.AbstractAuditableDto;
import eu.novobit.enumerations.IEnumBinaryStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;

/**
 * The type Job application dto.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class JobApplicationDto extends AbstractAuditableDto<Long> {

    private String domain;
    private String code;
    private IdCodeDto resume;
    private IdCodeDto jobOffer;
    private List<JobApplicationEventDto> jobApplicationEvents;
    @Builder.Default
    private IEnumBinaryStatus.Types binaryStatusType = IEnumBinaryStatus.Types.ENABLED;

}
