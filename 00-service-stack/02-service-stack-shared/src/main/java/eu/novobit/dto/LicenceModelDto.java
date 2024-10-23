package eu.novobit.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.Date;

/**
 * The type Licence model dto.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class LicenceModelDto extends AbstractAuditableDto<Long> {

    private String domain;
    private String provider;
    private String type;
    private String user;
    private Date expiryDate;
    private Integer crc16;
    private Integer crc32;
}
