package eu.novobit.model;

import eu.novobit.constants.DomainConstants;
import eu.novobit.model.base.AuditableEntity;
import eu.novobit.model.schema.SchemaColumnConstantName;
import eu.novobit.model.schema.SchemaConstantSize;
import eu.novobit.model.schema.SchemaFkConstantName;
import eu.novobit.model.schema.SchemaTableConstantName;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.ColumnDefault;

import java.util.List;

/**
 * The type Mail message.
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = SchemaTableConstantName.T_MAIL_MESSAGE)
public class MailMessage extends AuditableEntity<Long> implements ISASEntity {

    @Id
    @SequenceGenerator(name = "mail_sequence_generator", sequenceName = "mail_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "mail_sequence_generator")
    @Column(name = SchemaColumnConstantName.C_ID, updatable = false, nullable = false)
    private Long id;

    @ColumnDefault("'" + DomainConstants.DEFAULT_DOMAIN_NAME + "'")
    @Column(name = SchemaColumnConstantName.C_DOMAIN, length = SchemaConstantSize.DOMAIN, updatable = false, nullable = false)
    private String domain;

    @Column(name = SchemaColumnConstantName.C_SUBJECT, nullable = false)
    private String subject;

    @Lob
    @Column(name = SchemaColumnConstantName.C_BODY, nullable = false)
    private String body;

    @Column(name = SchemaColumnConstantName.C_TO_ADDRESS, nullable = false)
    private String toAddr;

    @Column(name = SchemaColumnConstantName.C_CC_ADDRESS)
    private String ccAddr;

    @Column(name = SchemaColumnConstantName.C_BCC_ADDRESS)
    private String bccAddr;

    @ElementCollection
    @CollectionTable(name = SchemaTableConstantName.T_MAIL_ATTACHMENT,
            joinColumns = @JoinColumn(name = SchemaColumnConstantName.C_MAIL,
                    referencedColumnName = SchemaColumnConstantName.C_ID,
                    foreignKey = @ForeignKey(name = SchemaFkConstantName.FK_ATTACHMENT_REF_MAIL)))
    @Column(name = SchemaColumnConstantName.C_ATTACHMENT)
    private List<String> attachments;

    @Builder.Default
    @ColumnDefault("'false'")
    @Column(name = SchemaColumnConstantName.C_SENT, nullable = false)
    private Boolean sent = Boolean.FALSE;
}
