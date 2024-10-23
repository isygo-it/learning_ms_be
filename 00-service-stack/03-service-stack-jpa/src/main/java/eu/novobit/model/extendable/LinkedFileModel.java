package eu.novobit.model.extendable;

import eu.novobit.model.ICodifiable;
import eu.novobit.model.base.AuditableCancelableEntity;
import eu.novobit.model.schema.ComSchemaColumnConstantName;
import eu.novobit.model.schema.ComSchemaConstantSize;
import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.OrderBy;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * The type Linked file model.
 *
 * @param <T> the type parameter
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@MappedSuperclass
public abstract class LinkedFileModel<T> extends AuditableCancelableEntity<T> implements ICodifiable {

    @Column(name = ComSchemaColumnConstantName.C_CODE, length = ComSchemaConstantSize.CODE, updatable = false, nullable = false)
    private String code;

    @Column(name = ComSchemaColumnConstantName.C_ORIGINAL_FILE_NAME, length = ComSchemaConstantSize.FILE_NAME_SIZE, updatable = true, nullable = false)
    private String originalFileName;

    @Column(name = ComSchemaColumnConstantName.C_EXTENSION, length = ComSchemaConstantSize.EXTENSION_SIZE, updatable = false, nullable = true)
    private String extension;

    @Column(name = ComSchemaColumnConstantName.C_CRC_16, updatable = false, nullable = true)
    private Integer crc16;

    @Column(name = ComSchemaColumnConstantName.C_CRC_32, updatable = false, nullable = true)
    private Integer crc32;

    @Column(name = ComSchemaColumnConstantName.C_SIZE, updatable = false, nullable = false)
    private Long size;

    @Column(name = ComSchemaColumnConstantName.C_PATH, length = ComSchemaConstantSize.PATH, nullable = true)
    private String path;

    @OrderBy(ComSchemaColumnConstantName.C_VERSION + " ASC")
    @Column(name = ComSchemaColumnConstantName.C_VERSION, updatable = false, nullable = true)
    private Long version;

    @Column(name = ComSchemaColumnConstantName.C_MIMETYPE, updatable = false, nullable = true)
    private String mimetype;
}
