package eu.novobit.dto;

import eu.novobit.enumerations.IEnumContact;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * The type Contact dto.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class ContactDto extends AbstractAuditableDto<Long> {

    private IEnumContact.Types type;
    private String value;
}
