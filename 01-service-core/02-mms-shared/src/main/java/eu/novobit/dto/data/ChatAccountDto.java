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
 * The type Chat account dto.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder

public class ChatAccountDto extends AbstractAuditableDto<Long> {

    private Long receiverId;
    private Long SenderId;
    private String fromFullName;
    private String lastMessage;
    private Date date;
    private Boolean read = Boolean.FALSE;
    @Builder.Default
    private IEnumWSStatus.Types status = IEnumWSStatus.Types.DISCONNECTED;
}
