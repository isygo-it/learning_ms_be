package eu.novobit.dto.data;


import eu.novobit.dto.AddressDto;
import eu.novobit.dto.CustomerModelDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * The type Customer dto.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class CustomerDto extends CustomerModelDto {

    private AddressDto address;
    private String accountCode;
}
