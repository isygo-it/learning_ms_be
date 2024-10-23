package eu.novobit.dto.data;

import eu.novobit.dto.AbstractAuditableDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.Date;

/**
 * The type Connection tracking dto.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class ConnectionTrackingDto extends AbstractAuditableDto<Long> {

    private String browser;
    private Date loginDate;
    private String device;
    private String logApp;
    private String ipAddress;
}
