package eu.novobit.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * The type Next code model dto.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class NextCodeModelDto extends AbstractAuditableDto<Long> {

    private String domain;
    private String entity;
    private String attribute;
    private String prefix;
    private String suffix;
    @Builder.Default
    private Long value = 0L;
    @Builder.Default
    private Long valueLength = 6L;
    @Builder.Default
    private Integer increment = 1;

    /**
     * Gets code.
     *
     * @return the code
     */
    public String getCode() {
        return ((prefix != null ? prefix.trim() : "")
                + String.format("%1$" + (valueLength != null ? valueLength : 6L) + "s", (value != null ? value : 0L))
                + (suffix != null ? suffix.trim() : ""))
                .replace(" ", "0");
    }

    /**
     * Next code next code model dto.
     *
     * @return the next code model dto
     */
    public NextCodeModelDto nextCode() {
        value += (increment != null ? increment : 1);
        return this;
    }
}
