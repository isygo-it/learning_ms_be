package eu.novobit.model.extendable;

import eu.novobit.enumerations.IEnumRequest;
import eu.novobit.enumerations.IEnumStatus;
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
 * The type Api permission model.
 *
 * @param <T> the type parameter
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@MappedSuperclass
public abstract class ApiPermissionModel<T> extends AuditableEntity<T> {

    @Column(name = ComSchemaColumnConstantName.C_SERVICE, length = ComSchemaConstantSize.S_NAME, nullable = false)
    private String serviceName;

    @Column(name = ComSchemaColumnConstantName.C_OBJECT, length = ComSchemaConstantSize.S_NAME, nullable = false)
    private String object;

    @Column(name = ComSchemaColumnConstantName.C_METHOD, length = ComSchemaConstantSize.S_NAME, nullable = false)
    private String method;

    @Enumerated(EnumType.STRING)
    @Column(name = ComSchemaColumnConstantName.C_RQ_TYPE, length = IEnumRequest.STR_ENUM_SIZE, nullable = false)
    private IEnumRequest.Types rqType;

    @Column(name = ComSchemaColumnConstantName.C_PATH, length = ComSchemaConstantSize.L_DESCRIPTION, nullable = false)
    private String path;

    @Column(name = ComSchemaColumnConstantName.C_DESCRIPTION, length = ComSchemaConstantSize.DESCRIPTION)
    private String description;

    @Builder.Default
    @ColumnDefault("'ACTIVE'")
    @Enumerated(EnumType.STRING)
    @Column(name = ComSchemaColumnConstantName.C_STATUS, length = IEnumStatus.STR_ENUM_SIZE, nullable = false)
    private IEnumStatus.Types status = IEnumStatus.Types.ACTIVE;

    /**
     * Gets role.
     *
     * @return the role
     */
    public String getRole() {
        return new StringBuilder()
                .append(this.getServiceName()).append(".")
                .append(this.getRqType().action()).append(".")
                .append(this.getObject()).append(".")
                //.append(this.getMethod())
                .toString().toLowerCase();
    }
}
