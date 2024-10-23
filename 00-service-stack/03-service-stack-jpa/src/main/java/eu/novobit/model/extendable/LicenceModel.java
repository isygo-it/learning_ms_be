package eu.novobit.model.extendable;

import eu.novobit.model.base.AuditableEntity;
import eu.novobit.model.schema.ComSchemaColumnConstantName;
import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.Date;

/**
 * The type Licence model.
 *
 * @param <T> the type parameter
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@MappedSuperclass
public abstract class LicenceModel<T> extends AuditableEntity<T> {

    @Column(name = ComSchemaColumnConstantName.C_PROVIDER)
    private String provider;

    @Column(name = ComSchemaColumnConstantName.C_TYPE, nullable = false)
    private String type;

    @Column(name = ComSchemaColumnConstantName.C_DOMAIN)
    private String domain;

    @Column(name = ComSchemaColumnConstantName.C_USER)
    private String user;

    @Temporal(value = TemporalType.TIMESTAMP)
    @Column(name = ComSchemaColumnConstantName.C_EXPIRY_DATE, updatable = false)
    private Date expiryDate;

    @Column(name = ComSchemaColumnConstantName.C_CRC16, updatable = false)
    private Integer crc16;

    @Column(name = ComSchemaColumnConstantName.C_CRC32, updatable = false)
    private Integer crc32;
}
