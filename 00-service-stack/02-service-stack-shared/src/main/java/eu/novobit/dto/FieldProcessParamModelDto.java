package eu.novobit.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * The type Field process param model dto.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class FieldProcessParamModelDto extends AbstractAuditableDto<Long> {

    private String processName;
    private String fieldName;
    private String description;
    private Boolean required;
    private Integer length;
    private String defaultValue;
    private Boolean readOnly;
    private String pattern;
}
