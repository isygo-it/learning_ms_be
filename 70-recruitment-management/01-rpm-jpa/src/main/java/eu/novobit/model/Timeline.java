package eu.novobit.model;

import eu.novobit.constants.DomainConstants;
import eu.novobit.enumerations.IEnumActionEvent;
import eu.novobit.model.base.AuditableEntity;
import eu.novobit.model.schema.ComSchemaColumnConstantName;
import eu.novobit.model.schema.SchemaColumnConstantName;
import eu.novobit.model.schema.SchemaConstantSize;
import eu.novobit.model.schema.SchemaTableConstantName;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.ColumnDefault;

/**
 * The type Timeline.
 */
@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = SchemaTableConstantName.T_TIMELINE)
public class Timeline extends AuditableEntity<Long> implements ISASEntity, ICodifiable {

    @Id
    @SequenceGenerator(name = "timeline_sequence_generator", sequenceName = "timeline_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "account_details_sequence_generator")
    @Column(name = SchemaColumnConstantName.C_ID, updatable = false, nullable = false)
    private Long id;

    @ColumnDefault("'" + DomainConstants.DEFAULT_DOMAIN_NAME + "'")
    @Column(name = SchemaColumnConstantName.C_DOMAIN, length = SchemaConstantSize.DOMAIN, updatable = false, nullable = false)
    private String domain;
    @Column(name = SchemaColumnConstantName.C_CODE, length = SchemaConstantSize.CODE, updatable = false, nullable = false)
    private String code;
    @Column(name = SchemaColumnConstantName.C_PARENT_CODE, length = SchemaConstantSize.PARENT_CODE, updatable = false)
    private String parentCode;
    @Column(name = SchemaColumnConstantName.C_OBJECT, length = SchemaConstantSize.OBJECT, updatable = false, nullable = false)
    private String object;
    @Enumerated(EnumType.STRING)
    @Column(name = ComSchemaColumnConstantName.C_ACTION_CODE, length = IEnumActionEvent.STR_ENUM_SIZE, updatable = false, nullable = false)
    private IEnumActionEvent.Types action;
}
