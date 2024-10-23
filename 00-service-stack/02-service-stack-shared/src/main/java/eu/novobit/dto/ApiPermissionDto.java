package eu.novobit.dto;

import eu.novobit.enumerations.IEnumRequest;
import eu.novobit.enumerations.IEnumStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * The type Api permission dto.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class ApiPermissionDto extends AbstractAuditableDto<Long> {

    private String serviceName;
    private String object;
    private String method;
    private IEnumRequest.Types rqType;
    private String path;
    private String description;
    private IEnumStatus.Types status = IEnumStatus.Types.ACTIVE;
}
