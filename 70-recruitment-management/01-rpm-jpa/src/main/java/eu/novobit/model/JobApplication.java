package eu.novobit.model;

import eu.novobit.constants.DomainConstants;
import eu.novobit.enumerations.IEnumBinaryStatus;
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

import java.util.ArrayList;
import java.util.List;

/**
 * The type Job application.
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = SchemaTableConstantName.T_JOB_APPLICATION, uniqueConstraints = {
        @UniqueConstraint(name = SchemaUcConstantName.UC_APPLICATION_CODE, columnNames = {SchemaColumnConstantName.C_CODE}),
        @UniqueConstraint(name = SchemaUcConstantName.UC_APP_RESUME_JOB, columnNames = {SchemaColumnConstantName.C_RESUME, SchemaColumnConstantName.C_JOB})
})
@SQLDelete(sql = "update " + SchemaTableConstantName.T_JOB_APPLICATION + " set " + SchemaColumnConstantName.C_CHECK_CANCEL + "= true , " + ComSchemaColumnConstantName.C_CANCEL_DATE + " = current_timestamp WHERE id = ?")
@Where(clause = SchemaColumnConstantName.C_CHECK_CANCEL + "=false")
public class JobApplication extends AuditableCancelableEntity<Long> implements ISASEntity, ICodifiable, IStatable<String>, IBoardItem<String> {

    @Id
    @SequenceGenerator(name = "job_appli_sequence_generator", sequenceName = "job_appli_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "job_appli_sequence_generator")
    @Column(name = SchemaColumnConstantName.C_ID, updatable = false, nullable = false)
    private Long id;

    @ColumnDefault("'" + DomainConstants.DEFAULT_DOMAIN_NAME + "'")
    @Column(name = SchemaColumnConstantName.C_DOMAIN, length = SchemaConstantSize.DOMAIN, updatable = false, nullable = false)
    private String domain;

    @Column(name = SchemaColumnConstantName.C_CODE, length = SchemaConstantSize.CODE, updatable = false, nullable = false)
    private String code;

    @Column(name = SchemaColumnConstantName.C_STATE)
    private String state;

    @ManyToOne(fetch = FetchType.LAZY/* NO CASCADE */)
    @JoinColumn(name = SchemaColumnConstantName.C_RESUME, referencedColumnName = SchemaColumnConstantName.C_CODE
            , foreignKey = @ForeignKey(name = SchemaFkConstantName.FK_JOB_APP_REF_RESUME))
    private Resume resume;
    @Builder.Default
    @Enumerated(EnumType.STRING)
    @ColumnDefault("'ENABLED'")
    @Column(name = SchemaColumnConstantName.C_JOB_APPLICATION_STATUS, length = IEnumBinaryStatus.STR_ENUM_SIZE)
    private IEnumBinaryStatus.Types binaryStatusType = IEnumBinaryStatus.Types.ENABLED;
    ;
    @ManyToOne(fetch = FetchType.LAZY /* NO CASCADE */)
    @JoinColumn(name = SchemaColumnConstantName.C_JOB, referencedColumnName = SchemaColumnConstantName.C_CODE
            , foreignKey = @ForeignKey(name = SchemaFkConstantName.FK_JOB_APP_REF_JOB))
    private JobOffer jobOffer;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL /* Cascade only for OneToMany*/)
    @JoinColumn(name = SchemaColumnConstantName.C_JOB_APPLICATION, referencedColumnName = SchemaColumnConstantName.C_ID
            , foreignKey = @ForeignKey(name = SchemaFkConstantName.FK_JOB_APP_REF_JOB_APP_EVENT))
    private List<JobApplicationEvent> jobApplicationEvents;


    @Override
    public String getItemName() {
        return (this.getResume() != null && this.getJobOffer() != null) ?
                new StringBuilder(this.getResume().getFullName()).append(" Applied for ")
                        .append(this.getJobOffer().getTitle()).toString()
                : "Missing Job application details";
    }

    @Override
    public String getImagePath() {
        return resume.getImagePath();
    }

    @Override
    public List<IBoardEvent> getEvents() {
        return new ArrayList<>(jobApplicationEvents);
    }

    @Override
    public String getItemImage() {
        return resume != null ? resume.getId().toString() : null;
    }
}
