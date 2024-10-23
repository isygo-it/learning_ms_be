package eu.novobit.dto.data;

import eu.novobit.dto.AbstractAuditableDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.Date;

/**
 * The type Chat message dto.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder

public class ChatMessageDto extends AbstractAuditableDto<Long> {

    private Long receiverId;
    private Long senderId;
    private String senderName;
    private String message;
    private Date date;
    private Boolean read = Boolean.FALSE;
}
