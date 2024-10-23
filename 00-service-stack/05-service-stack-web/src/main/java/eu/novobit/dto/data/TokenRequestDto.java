package eu.novobit.dto.data;


import eu.novobit.dto.AbstractAuditableDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.Map;

/**
 * The type Token request dto.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class TokenRequestDto extends AbstractAuditableDto<Long> {

    private String subject;
    private Map<String, Object> claims;
}
