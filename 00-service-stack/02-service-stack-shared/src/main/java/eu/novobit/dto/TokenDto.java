package eu.novobit.dto;


import eu.novobit.enumerations.IEnumWebToken;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.Date;

/**
 * The type Token dto.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class TokenDto extends AbstractAuditableDto<Long> {

    @NotNull
    private IEnumWebToken.Types type;
    @NotEmpty
    private String token;
    @NotNull
    private Date expiryDate;
}
