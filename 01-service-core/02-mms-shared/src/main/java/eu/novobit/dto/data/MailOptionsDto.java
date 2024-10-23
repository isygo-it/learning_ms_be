package eu.novobit.dto.data;


import eu.novobit.dto.AbstractAuditableDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * The type Mail options dto.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class MailOptionsDto extends AbstractAuditableDto<Long> {

    /**
     * The Return delivered.
     */
    boolean returnDelivered;
    /**
     * The Return read.
     */
    boolean returnRead;
}
