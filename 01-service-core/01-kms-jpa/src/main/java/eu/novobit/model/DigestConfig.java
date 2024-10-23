package eu.novobit.model;

import eu.novobit.constants.DomainConstants;
import eu.novobit.enumerations.IEnumAlgoDigestConfig;
import eu.novobit.enumerations.IEnumSaltGenerator;
import eu.novobit.enumerations.IEnumStringOutputType;
import eu.novobit.model.base.AuditableEntity;
import eu.novobit.model.schema.SchemaColumnConstantName;
import eu.novobit.model.schema.SchemaConstantSize;
import eu.novobit.model.schema.SchemaTableConstantName;
import eu.novobit.model.schema.SchemaUcConstantName;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.validator.constraints.Length;

/**
 * The type Digest config.
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = SchemaTableConstantName.T_DIGESTER_CONFIG
        , uniqueConstraints = {
        @UniqueConstraint(name = SchemaUcConstantName.UC_DIGESTER_CONFIG_DOMAIN,
                columnNames = {SchemaColumnConstantName.C_DOMAIN})
})
public class DigestConfig extends AuditableEntity<Long> implements ISASEntity, ICodifiable {

    @Id
    @SequenceGenerator(name = "digester_config_sequence_generator", sequenceName = "digester_config_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "digester_config_sequence_generator")
    @Column(name = SchemaColumnConstantName.C_ID, updatable = false, nullable = false)
    private Long id;

    @Length(max = SchemaConstantSize.CODE)
    @Column(name = SchemaColumnConstantName.C_CODE, length = SchemaConstantSize.CODE, updatable = false, nullable = false)
    private String code;

    @ColumnDefault("'" + DomainConstants.DEFAULT_DOMAIN_NAME + "'")
    @Column(name = SchemaColumnConstantName.C_DOMAIN, length = SchemaConstantSize.DOMAIN, updatable = false, nullable = false)
    private String domain;

    //DIGEST ALGORITHMS:   [MD2, MD5, SHA, SHA-256, SHA-384, SHA-512]
    @Enumerated(EnumType.STRING)
    @Column(name = SchemaColumnConstantName.C_ALGORITHM, length = IEnumAlgoDigestConfig.STR_ENUM_SIZE, nullable = false)
    private IEnumAlgoDigestConfig.Types algorithm;

    @Column(name = SchemaColumnConstantName.C_ITERATIONS)
    private Integer iterations;

    @Column(name = SchemaColumnConstantName.C_SALT_SIZE_BYTES)
    private Integer saltSizeBytes;

    @Enumerated(EnumType.STRING)
    @Column(name = SchemaColumnConstantName.C_SALT_GENERATOR, length = IEnumSaltGenerator.STR_ENUM_SIZE, nullable = false)
    private IEnumSaltGenerator.Types saltGenerator;

    @Column(name = SchemaColumnConstantName.C_PROVIDER_CLASS_NAME)
    private String providerClassName;

    @Column(name = SchemaColumnConstantName.C_PROVIDER_NAME)
    private String providerName;

    @Builder.Default
    @ColumnDefault("'false'")
    @Column(name = SchemaColumnConstantName.C_INV_POS_SALT_MESS_BEFORE_DIG, nullable = false)
    private Boolean invertPositionOfSaltInMessageBeforeDigesting = Boolean.FALSE;

    @Builder.Default
    @ColumnDefault("'false'")
    @Column(name = SchemaColumnConstantName.C_INV_POS_PLAIN_SALT_IN_ENC_RES, nullable = false)
    private Boolean invertPositionOfPlainSaltInEncryptionResults = Boolean.FALSE;

    @Builder.Default
    @Column(name = SchemaColumnConstantName.C_USE_LENIENT_SALT_SIZE_CHECK, nullable = false)
    private Boolean useLenientSaltSizeCheck = Boolean.FALSE;

    @Column(name = SchemaColumnConstantName.C_POOL_SIZE)
    private Integer poolSize;

    @Builder.Default
    @ColumnDefault("'false'")
    @Column(name = SchemaColumnConstantName.C_UNICODE_NORMALIZATION_IGNORED, nullable = false)
    private Boolean unicodeNormalizationIgnored = Boolean.FALSE;

    @Enumerated(EnumType.STRING)
    @Column(name = SchemaColumnConstantName.C_STRING_OUTPUT_TYPE, length = IEnumStringOutputType.STR_ENUM_SIZE, nullable = false)
    private IEnumStringOutputType.Types stringOutputType = null;

    @Column(name = SchemaColumnConstantName.C_PREFIX)
    private String prefix = null;

    @Column(name = SchemaColumnConstantName.C_SUFFIX)
    private String suffix = null;
}
