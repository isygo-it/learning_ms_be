package eu.novobit.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * The type Bpm event request dto.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class BpmEventRequestDto extends AbstractAuditableDto<Long> {

    private BoardItemDto item;
    private String wbCode;
    private String fromState; //code
    private String toState; // code
}
