package eu.novobit.dto.data;

import eu.novobit.dto.AbstractAuditableDto;
import eu.novobit.dto.AddressDto;
import eu.novobit.dto.ContactDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;

/**
 * The type Account details dto.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class AccountDetailsDto extends AbstractAuditableDto<Long> {

    private String firstName;
    private String lastName;
    private String country;
    private List<ContactDto> contacts;
    private AddressDto address;
}
