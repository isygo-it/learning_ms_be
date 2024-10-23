package eu.novobit.model;

import eu.novobit.constants.DomainConstants;
import eu.novobit.enumerations.IEnumAppToken;
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
 * The type Token config.
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = SchemaTableConstantName.T_TOKEN_CONFIG
        , uniqueConstraints = {
        @UniqueConstraint(name = SchemaUcConstantName.UC_TOKEN_CONFIG_DOMAIN_TYPE,
                columnNames = {SchemaColumnConstantName.C_DOMAIN, SchemaColumnConstantName.C_TOKEN_TYPE})
})
public class TokenConfig extends AuditableEntity<Long> implements ISASEntity, ICodifiable {

    @Id
    @SequenceGenerator(name = "token_config_sequence_generator", sequenceName = "token_config_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "token_config_sequence_generator")
    @Column(name = SchemaColumnConstantName.C_ID, updatable = false, nullable = false)
    private Long id;

    @Column(name = SchemaColumnConstantName.C_CODE, length = SchemaConstantSize.CODE, updatable = false, nullable = false)
    private String code;

    @ColumnDefault("'" + DomainConstants.DEFAULT_DOMAIN_NAME + "'")
    @Column(name = SchemaColumnConstantName.C_DOMAIN, length = SchemaConstantSize.DOMAIN, updatable = false, nullable = false)
    private String domain;

    @Column(name = SchemaColumnConstantName.C_ISSUER, updatable = false, nullable = false)
    private String issuer;

    @Builder.Default
    @ColumnDefault("'ACCESS'")
    @Enumerated(EnumType.STRING)
    @Column(name = SchemaColumnConstantName.C_TOKEN_TYPE, length = IEnumAppToken.STR_ENUM_SIZE, nullable = false)
    private IEnumAppToken.Types tokenType = IEnumAppToken.Types.ACCESS;

    @Column(name = SchemaColumnConstantName.C_AUDIENCE, updatable = false, nullable = false)
    private String audience;

    @Builder.Default
    @Column(name = ComSchemaColumnConstantName.C_SIGNATURE_ALGORITHM, length = IEnumCharSet.STR_ENUM_SIZE, nullable = false)
    private String signatureAlgorithm = "RS256";

    @Column(name = SchemaColumnConstantName.C_SECRET_KEY, nullable = false)
    private String secretKey = "sEcReTkEy";

    @Builder.Default
    @Column(name = SchemaColumnConstantName.C_LIFE_TIME_MS, nullable = false)
    private Integer lifeTimeInMs = 14400000;
}
