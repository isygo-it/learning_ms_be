package eu.novobit.model.extendable;

import eu.novobit.enumerations.IEnumBinaryStatus;
import eu.novobit.model.ICodifiable;
import eu.novobit.model.IImageEntity;
import eu.novobit.model.ISASEntity;
import eu.novobit.model.base.AuditableEntity;
import eu.novobit.model.schema.ComSchemaColumnConstantName;
import eu.novobit.model.schema.ComSchemaConstantSize;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.MappedSuperclass;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.ColumnDefault;

/**
 * The type Customer model.
 *
 * @param <T> the type parameter
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@MappedSuperclass
public abstract class CustomerModel<T> extends AuditableEntity<T> implements ISASEntity, ICodifiable, IImageEntity {

    @Column(name = ComSchemaColumnConstantName.C_CODE, length = ComSchemaConstantSize.CODE, updatable = false, nullable = false)
    private String code;

    @Column(name = ComSchemaColumnConstantName.C_DOMAIN)
    private String domain;

    @Column(name = ComSchemaColumnConstantName.C_NAME)
    private String name;

    @Column(name = ComSchemaColumnConstantName.C_DESCRIPTION, length = ComSchemaConstantSize.DESCRIPTION)
    private String description;

    @Column(name = ComSchemaColumnConstantName.C_URL)
    private String url;

    @Column(name = ComSchemaColumnConstantName.C_EMAIL)
    private String email;

    @Column(name = ComSchemaColumnConstantName.C_PHONE_NUMBER)
    private String phoneNumber;

    @Column(name = ComSchemaColumnConstantName.C_LOGO)
    private String imagePath;

    @Builder.Default
    @ColumnDefault("'ENABLED'")
    @Enumerated(EnumType.STRING)
    @Column(name = ComSchemaColumnConstantName.C_ADMIN_STATUS, length = IEnumBinaryStatus.STR_ENUM_SIZE, nullable = false)
    private IEnumBinaryStatus.Types adminStatus = IEnumBinaryStatus.Types.ENABLED;
}
