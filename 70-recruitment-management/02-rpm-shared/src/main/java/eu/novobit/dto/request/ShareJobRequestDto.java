package eu.novobit.dto.request;

import eu.novobit.dto.AccountModelDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;

/**
 * The type Share job request dto.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class ShareJobRequestDto {
    private String jobOwner;
    private List<AccountModelDto> accountsCode;
}
