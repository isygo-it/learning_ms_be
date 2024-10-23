package eu.novobit.model;


import eu.novobit.constants.DomainConstants;
import eu.novobit.enumerations.IEnumTemplateName;
import eu.novobit.model.base.AuditableEntity;
import eu.novobit.model.schema.*;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.ColumnDefault;

/**
 * The type Template.
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = SchemaTableConstantName.T_MAIL_TEMPLATE, uniqueConstraints = {@UniqueConstraint(name = SchemaUcConstantName.UC_TEMPLATE, columnNames = {SchemaColumnConstantName.C_DOMAIN, SchemaColumnConstantName.C_NAME})})

public class Template extends AuditableEntity<Long> implements ISASEntity, ICodifiable {

    @Id
    @SequenceGenerator(name = "template_sequence_generator", sequenceName = "template_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "template_sequence_generator")
    @Column(name = SchemaColumnConstantName.C_ID, updatable = false, nullable = false)
    private Long id;

    @Column(name = SchemaColumnConstantName.C_CODE, length = SchemaConstantSize.CODE, updatable = false, nullable = false)
    private String code;
    @ColumnDefault("'" + DomainConstants.DEFAULT_DOMAIN_NAME + "'")
    @Column(name = SchemaColumnConstantName.C_DOMAIN, length = SchemaConstantSize.DOMAIN, updatable = false, nullable = false)
    private String domain;
    @Enumerated(EnumType.STRING)
    @Column(name = SchemaColumnConstantName.C_NAME, length = IEnumTemplateName.STR_ENUM_SIZE, nullable = false)
    private IEnumTemplateName.Types name;
    @Column(name = SchemaColumnConstantName.C_DESCRIPTION, length = ComSchemaConstantSize.DESCRIPTION)
    private String description;
    @Column(name = SchemaColumnConstantName.C_PATH, nullable = false)
    private String path;
}
