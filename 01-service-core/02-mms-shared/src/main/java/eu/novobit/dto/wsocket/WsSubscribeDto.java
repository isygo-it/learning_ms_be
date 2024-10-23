package eu.novobit.dto.wsocket;

import eu.novobit.dto.AbstractAuditableDto;
import eu.novobit.enumerations.IEnumWSBroker;
import eu.novobit.enumerations.IEnumWSStatus;
import eu.novobit.enumerations.IEnumWsEndpoint;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * The type Ws subscribe dto.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder

public class WsSubscribeDto extends AbstractAuditableDto<Long> {

    /**
     * The Status.
     */
    @Builder.Default
    IEnumWSStatus.Types status = IEnumWSStatus.Types.DISCONNECTED;
    private String sessionId;
    private Long senderId;
    private Long groupId;
    private IEnumWsEndpoint.Types endPoint;
    private IEnumWSBroker.Types broker;
}