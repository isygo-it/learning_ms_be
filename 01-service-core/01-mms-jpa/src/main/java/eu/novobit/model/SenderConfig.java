package eu.novobit.model;

import eu.novobit.constants.DomainConstants;
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

/**
 * The type Sender config.
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = SchemaTableConstantName.T_MAIL_SENDER_CONFIG
        , uniqueConstraints = {
        @UniqueConstraint(name = SchemaUcConstantName.UC_SENDER_CONFIG_DOMAIN, columnNames = {SchemaColumnConstantName.C_DOMAIN})
})
public class SenderConfig extends AuditableEntity<Long> implements ISASEntity {

    @Id
    @SequenceGenerator(name = "sender_config_sequence_generator", sequenceName = "sender_config_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sender_config_sequence_generator")
    @Column(name = SchemaColumnConstantName.C_ID, updatable = false, nullable = false)
    private Long id;

    @ColumnDefault("'" + DomainConstants.DEFAULT_DOMAIN_NAME + "'")
    @Column(name = SchemaColumnConstantName.C_DOMAIN, length = SchemaConstantSize.DOMAIN, updatable = false, nullable = false)
    private String domain;
    @Column(name = SchemaColumnConstantName.C_HOST, nullable = false)
    private String host;
    @Column(name = SchemaColumnConstantName.C_PORT, nullable = false)
    private String port;
    @Column(name = SchemaColumnConstantName.C_USER_NAME, nullable = false)
    private String username;
    @Column(name = SchemaColumnConstantName.C_PASSWORD, nullable = false)
    private String password;
    @Column(name = SchemaColumnConstantName.C_TRANSPORT_PROTOCOL, nullable = false)
    private String transportProtocol;
    @Column(name = SchemaColumnConstantName.C_SMTP_AUTH)
    private String smtpAuth;
    @Builder.Default
    @ColumnDefault("'false'")
    @Column(name = SchemaColumnConstantName.C_SMTP_STARTTLS_ENABLE, nullable = false)
    private Boolean smtpStarttlsEnable = Boolean.FALSE;
    @Builder.Default
    @ColumnDefault("'false'")
    @Column(name = SchemaColumnConstantName.C_SMTP_STARTTLS_REQUIRED, nullable = false)
    private Boolean smtpStarttlsRequired = Boolean.FALSE;
    @Builder.Default
    @Column(name = SchemaColumnConstantName.C_DEBUG, nullable = false)
    private Boolean debug = Boolean.FALSE;
}
