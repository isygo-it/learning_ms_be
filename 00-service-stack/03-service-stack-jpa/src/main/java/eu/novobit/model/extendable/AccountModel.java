package eu.novobit.model.extendable;

import eu.novobit.constants.DomainConstants;
import eu.novobit.enumerations.IEnumAccountOrigin;
import eu.novobit.enumerations.IEnumAccountSystemStatus;
import eu.novobit.enumerations.IEnumBinaryStatus;
import eu.novobit.model.ICodifiable;
import eu.novobit.model.ISASEntity;
import eu.novobit.model.base.AuditableCancelableEntity;
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

import javax.validation.constraints.Email;

/**
 * The type Account model.
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@MappedSuperclass
public abstract class AccountModel extends AuditableCancelableEntity<Long> implements ISASEntity, ICodifiable {

    //@Convert(converter = EncryptorHelper.class)
    @Column(name = ComSchemaColumnConstantName.C_CODE, length = ComSchemaConstantSize.CODE, updatable = false, nullable = false)
    private String code;
    @ColumnDefault("'" + DomainConstants.DEFAULT_DOMAIN_NAME + "'")
    @Column(name = ComSchemaColumnConstantName.C_DOMAIN, length = ComSchemaConstantSize.DOMAIN, updatable = false, nullable = false)
    private String domain;
    @Column(name = ComSchemaColumnConstantName.C_EMAIL, length = ComSchemaConstantSize.EMAIL, nullable = false)
    @Email(message = "email.should.be.valid")
    private String email;
    @Builder.Default
    @Enumerated(EnumType.STRING)
    @ColumnDefault("'ENABLED'")
    @Column(name = ComSchemaColumnConstantName.C_ADMIN_STATUS, length = IEnumBinaryStatus.STR_ENUM_SIZE, nullable = false)
    private IEnumBinaryStatus.Types adminStatus = IEnumBinaryStatus.Types.ENABLED;
    @Builder.Default
    @Enumerated(EnumType.STRING)
    @ColumnDefault("'IDLE'")
    @Column(name = ComSchemaColumnConstantName.C_SYSTEM_STATUS, length = IEnumAccountSystemStatus.STR_ENUM_SIZE, nullable = false)
    private IEnumAccountSystemStatus.Types systemStatus = IEnumAccountSystemStatus.Types.IDLE;
    @ColumnDefault("'SYS_ADMIN'")
    @Column(name = ComSchemaColumnConstantName.C_ORIGIN, nullable = false)
    private String origin = IEnumAccountOrigin.Types.SYS_ADMIN.name();
}
