package eu.novobit.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * The type Request context dto.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class RequestContextDto extends AbstractDto<Long> {

    private String senderDomain;
    private String senderUser;
    private Boolean isAdmin;
    private String logApp;
}
