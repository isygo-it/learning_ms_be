package eu.novobit.dto.wsocket;


import eu.novobit.dto.AbstractAuditableDto;
import eu.novobit.enumerations.IEnumWSBroker;
import eu.novobit.enumerations.IEnumWsEndpoint;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * The type Ws message wrapper dto.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class WsMessageWrapperDto extends AbstractAuditableDto<Long> {

    private IEnumWsEndpoint.Types endPoint;
    private String freeEndoint; //used only if type is FREE
    private IEnumWSBroker.Types broker;
    private Long senderId; //userId
    private WsMessageDto message;
}
