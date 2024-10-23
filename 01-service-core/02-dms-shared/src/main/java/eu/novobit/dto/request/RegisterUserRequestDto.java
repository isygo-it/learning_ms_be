package eu.novobit.dto.request;


import eu.novobit.dto.AbstractAuditableDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * The type Register user request dto.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class RegisterUserRequestDto extends AbstractAuditableDto<Long> {

    private String email;
}
