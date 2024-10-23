package eu.novobit.dto;

import eu.novobit.enumerations.IEnumLanguage;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * The type Annex model dto.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class AnnexModelDto extends AbstractAuditableDto<Long> {

    private String tableCode;
    private IEnumLanguage.Types language;
    private String value;
    private String description;
    private String reference;
    private Integer annexOrder;
}
