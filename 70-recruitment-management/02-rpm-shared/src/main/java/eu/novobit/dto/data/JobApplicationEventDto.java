package eu.novobit.dto.data;

import eu.novobit.dto.AbstractAuditableDto;
import eu.novobit.enumerations.IEnumJobAppEventType;
import eu.novobit.enumerations.IEnumJobAppStatusType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;

/**
 * The type Job application event dto.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class JobApplicationEventDto extends AbstractAuditableDto<Long> {

    private IEnumJobAppEventType.Types type;
    @Builder.Default
    private IEnumJobAppStatusType.Types statusType = IEnumJobAppStatusType.Types.PLANNED;
    private String title;
    private String location;
    private String calendar;
    private String eventCode;
    private List<String> participants;
    private String comment;
}
