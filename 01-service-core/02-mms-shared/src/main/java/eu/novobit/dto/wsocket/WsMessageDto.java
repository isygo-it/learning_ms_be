package eu.novobit.dto.wsocket;


import eu.novobit.dto.AbstractAuditableDto;
import eu.novobit.enumerations.IEnumWSMessage;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * The type Ws message dto.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class WsMessageDto extends AbstractAuditableDto<Long> {

    private IEnumWSMessage.Types type;
    private Long senderId; //userId
    private String senderName; //user fullName
    private String content; //text message
}
