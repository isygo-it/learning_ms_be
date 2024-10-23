package eu.novobit.model;

import eu.novobit.constants.DomainConstants;
import eu.novobit.enumerations.IEnumAuth;
import eu.novobit.enumerations.IEnumCharSet;
import eu.novobit.model.base.AuditableEntity;
import eu.novobit.model.schema.*;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.ColumnDefault;

/**
 * The type Password config.
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = SchemaTableConstantName.T_PASSWORD_CONFIG
        , uniqueConstraints = {
        @UniqueConstraint(name = SchemaUcConstantName.UC_PASSWORD_CONFIG_DOMAIN,
                columnNames = {SchemaColumnConstantName.C_DOMAIN})
})
public class PasswordConfig extends AuditableEntity<Long> implements ISASEntity, ICodifiable {

    @Id
    @SequenceGenerator(name = "password_config_sequence_generator", sequenceName = "password_config_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "password_config_sequence_generator")
    @Column(name = SchemaColumnConstantName.C_ID, updatable = false, nullable = false)
    private Long id;

    @Column(name = SchemaColumnConstantName.C_CODE, length = SchemaConstantSize.CODE, updatable = false, nullable = false)
    private String code;
    @ColumnDefault("'" + DomainConstants.DEFAULT_DOMAIN_NAME + "'")
    @Column(name = SchemaColumnConstantName.C_DOMAIN, length = SchemaConstantSize.DOMAIN, updatable = false, nullable = false)
    private String domain;
    @Builder.Default
    @Column(name = SchemaColumnConstantName.C_TYPE, length = IEnumAuth.STR_ENUM_SIZE, updatable = false, nullable = false)
    private IEnumAuth.Types type = IEnumAuth.Types.PWD;
    @Column(name = SchemaColumnConstantName.C_PATTERN)
    private String pattern;
    @Builder.Default
    @ColumnDefault("'ALL'")
    @Enumerated(EnumType.STRING)
    @Column(name = ComSchemaColumnConstantName.C_CHAR_SET_TYPE, length = IEnumCharSet.STR_ENUM_SIZE, nullable = false)
    private IEnumCharSet.Types charSetType = IEnumCharSet.Types.ALL;
    @Column(name = SchemaColumnConstantName.C_INITIAL, length = SchemaConstantSize.PASS_WORD)
    private String initial;
    @Builder.Default
    @Column(name = SchemaColumnConstantName.C_PWD_MIN_LENGTH, nullable = false)
    private Integer minLenght = 8;
    @Builder.Default
    @Column(name = SchemaColumnConstantName.C_PWD_MAX_LENGTH, nullable = false)
    private Integer maxLenth = 32;
    @Builder.Default
    @Column(name = SchemaColumnConstantName.C_LIFE_TIME, nullable = false)
    private Integer lifeTime = 90;
}
