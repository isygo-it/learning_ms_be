package eu.novobit.dto.data;

import eu.novobit.dto.AbstractAuditableDto;
import eu.novobit.enumerations.IEnumAuth;
import eu.novobit.enumerations.IEnumCharSet;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;


/**
 * The type Password config dto.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class PasswordConfigDto extends AbstractAuditableDto<Long> {

    private String code;
    private String domain;
    private IEnumAuth.Types type;
    private String pattern;
    private IEnumCharSet.Types charSetType;
    private String initial;
    private Integer minLenght;
    private Integer maxLenth;
    private Integer lifeTime;
}
