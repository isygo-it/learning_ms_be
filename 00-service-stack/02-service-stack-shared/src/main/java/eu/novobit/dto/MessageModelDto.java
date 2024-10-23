package eu.novobit.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * The type Message model dto.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class MessageModelDto extends AbstractAuditableDto<Long> {

    private String code;
    private String locale;
    private String text;
    private String forcedText;
}
