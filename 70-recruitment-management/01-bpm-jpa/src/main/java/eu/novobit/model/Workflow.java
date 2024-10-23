package eu.novobit.model;

import eu.novobit.constants.DomainConstants;
import eu.novobit.enumerations.IEnumWorkflow;
import eu.novobit.enumerations.IEnumWorkflowCategory;
import eu.novobit.model.base.AuditableCancelableEntity;
import eu.novobit.model.schema.*;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import java.util.List;

/**
 * The type Workflow.
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = SchemaTableConstantName.T_WORKFLOW, uniqueConstraints = {
        @UniqueConstraint(name = SchemaUcConstantName.UC_WORKFLOW_CODE, columnNames = {SchemaColumnConstantName.C_CODE}),
})
@SQLDelete(sql = "update " + SchemaTableConstantName.T_WORKFLOW + " set " + SchemaColumnConstantName.C_CHECK_CANCEL + "= true , " + ComSchemaColumnConstantName.C_CANCEL_DATE + " = current_timestamp WHERE id = ?")
@Where(clause = SchemaColumnConstantName.C_CHECK_CANCEL + "=false")
public class Workflow extends AuditableCancelableEntity<Long> implements ISASEntity, ICodifiable {

    @Id
    @SequenceGenerator(name = "workflow_sequence_generator", sequenceName = "workflow_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "workflow_sequence_generator")
    @Column(name = SchemaColumnConstantName.C_ID, updatable = false, nullable = false)
    private Long id;

    @ColumnDefault("'" + DomainConstants.DEFAULT_DOMAIN_NAME + "'")
    @Column(name = SchemaColumnConstantName.C_DOMAIN, length = SchemaConstantSize.DOMAIN, updatable = false, nullable = false)
    private String domain;
    @Column(name = SchemaColumnConstantName.C_CODE, length = SchemaConstantSize.CODE, updatable = false, nullable = false)
    private String code;
    @Column(name = SchemaColumnConstantName.C_NAME, length = SchemaConstantSize.S_NAME, nullable = false)
    private String name;
    @Column(name = SchemaColumnConstantName.C_DESCRIPTION, length = ComSchemaConstantSize.DESCRIPTION)
    private String description;
    @Enumerated(EnumType.STRING)
    @Column(name = SchemaColumnConstantName.C_TYPE, length = IEnumWorkflow.STR_ENUM_SIZE, nullable = false)
    private IEnumWorkflow.Types type;
    @Enumerated(EnumType.STRING)
    @Column(name = SchemaColumnConstantName.C_CATEGORY, length = IEnumWorkflowCategory.STR_ENUM_SIZE, nullable = false)
    private IEnumWorkflowCategory.Types category;
    @Builder.Default
    @ColumnDefault("'false'")
    @Column(name = SchemaColumnConstantName.C_AUTO_STARTUP, nullable = false)
    private Boolean autoStartup = Boolean.FALSE;
    @OrderBy(SchemaColumnConstantName.C_SEQUENCE)
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true/* Cascade only for OneToMany*/)
    @JoinColumn(name = SchemaColumnConstantName.C_WORKFLOW, referencedColumnName = SchemaColumnConstantName.C_CODE
            , foreignKey = @ForeignKey(name = SchemaFkConstantName.FK_WORKFLOW_REF_STATE))
    private List<WorkflowState> workflowStates;
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true/* Cascade only for OneToMany*/)
    @JoinColumn(name = SchemaColumnConstantName.C_WORKFLOW, referencedColumnName = SchemaColumnConstantName.C_CODE
            , foreignKey = @ForeignKey(name = SchemaFkConstantName.FK_WORKFLOW_REF_TRANSITION))
    private List<WorkflowTransition> workflowTransitions;
}
