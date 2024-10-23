package eu.novobit.model.extendable;

import eu.novobit.model.IIdEntity;
import eu.novobit.model.ISASEntity;
import eu.novobit.model.schema.ComSchemaColumnConstantName;
import eu.novobit.model.schema.ComSchemaConstantSize;
import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

/**
 * The type Next code model.
 *
 * @param <T> the type parameter
 */
@Data
@ToString
@NoArgsConstructor
@SuperBuilder
@MappedSuperclass
public abstract class NextCodeModel<T> implements IIdEntity<T>, ISASEntity {

    @Column(name = ComSchemaColumnConstantName.C_DOMAIN, nullable = false)
    private String domain;
    @Column(name = ComSchemaColumnConstantName.C_ENTITY, nullable = false)
    private String entity;
    @Column(name = ComSchemaColumnConstantName.C_ATTRIBUTE, nullable = false)
    private String attribute;
    @Column(name = ComSchemaColumnConstantName.C_PREFIX, length = ComSchemaConstantSize.CODE)
    private String prefix;
    @Column(name = ComSchemaColumnConstantName.C_SUFFIX, length = ComSchemaConstantSize.CODE)
    private String suffix;
    @Builder.Default
    @Column(name = ComSchemaColumnConstantName.C_VALUE, nullable = false)
    private Long value = 0L;
    @Builder.Default
    @Column(name = ComSchemaColumnConstantName.C_VALUE_LENGTH, nullable = false)
    private Long valueLength = 6L;
    @Builder.Default
    @Column(name = ComSchemaColumnConstantName.C_INCREMENT, nullable = false)
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
     * Next code next code model.
     *
     * @return the next code model
     */
    public NextCodeModel nextCode() {
        value += (increment != null ? increment : 1);
        return this;
    }
}
