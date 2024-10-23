package eu.novobit.dto.data;


import eu.novobit.dto.AbstractAuditableDto;
import eu.novobit.enumerations.IEnumWSStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.Date;

/**
 * The type Min account dto.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class MinAccountDto extends AbstractAuditableDto<Long> {

    private String code;
    private String email;
    private String fullName;
    private String imagePath;
    @Builder.Default
    private IEnumWSStatus.Types status = IEnumWSStatus.Types.DISCONNECTED;
    private Date lastConnectionDate;
}
