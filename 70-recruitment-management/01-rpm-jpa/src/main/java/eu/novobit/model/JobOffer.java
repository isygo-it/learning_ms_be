package eu.novobit.model;

import eu.novobit.constants.DomainConstants;
import eu.novobit.model.base.AuditableCancelableEntity;
import eu.novobit.model.schema.*;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import java.util.List;

/**
 * The type Job offer.
 */
@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = SchemaTableConstantName.T_JOB,
        uniqueConstraints = {
                @UniqueConstraint(name = SchemaUcConstantName.UC_JOB_OFFER_CODE, columnNames = {SchemaColumnConstantName.C_CODE})
        })
@SQLDelete(sql = "update " + SchemaTableConstantName.T_JOB + " set " + SchemaColumnConstantName.C_CHECK_CANCEL + "= true , " + ComSchemaColumnConstantName.C_CANCEL_DATE + " = current_timestamp WHERE id = ?")
@Where(clause = SchemaColumnConstantName.C_CHECK_CANCEL + "=false")
public class JobOffer extends AuditableCancelableEntity<Long> implements ISASEntity, ICodifiable {

    @Id
    @SequenceGenerator(name = "account_details_sequence_generator", sequenceName = "account_details_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "account_details_sequence_generator")
    @Column(name = SchemaColumnConstantName.C_ID, updatable = false, nullable = false)
    private Long id;

    @ColumnDefault("'" + DomainConstants.DEFAULT_DOMAIN_NAME + "'")
    @Column(name = SchemaColumnConstantName.C_DOMAIN, length = SchemaConstantSize.DOMAIN, updatable = false, nullable = false)
    private String domain;
    @Column(name = SchemaColumnConstantName.C_CODE, length = SchemaConstantSize.CODE, updatable = false, nullable = false)
    private String code;
    @Column(name = SchemaColumnConstantName.C_NAME, length = SchemaConstantSize.S_NAME, nullable = false)
    private String title;
    @Column(name = SchemaColumnConstantName.C_DEPARTMENT)
    private String department;
    @Column(name = SchemaColumnConstantName.C_INDUSTRY)
    private String industry;
    @Column(name = SchemaColumnConstantName.C_EMPLOYER_TYPE, length = SchemaConstantSize.L_VALUE)
    private String employerType;
    @Column(name = SchemaColumnConstantName.C_JOB_FUNCTION, length = SchemaConstantSize.L_VALUE)
    private String jobFunction;
    @Column(name = SchemaColumnConstantName.C_OWNER, length = SchemaConstantSize.CUSTOMER)
    private String owner;
    @Column(name = SchemaColumnConstantName.C_CUSTOMER)
    private String customer;
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL  /* CASCADE only for OneToOne*/)
    @JoinColumn(name = SchemaColumnConstantName.C_JOB_DETAILS, referencedColumnName = SchemaColumnConstantName.C_ID
            , foreignKey = @ForeignKey(name = SchemaFkConstantName.FK_JOB_REF_DETAILS))
    private JobDetails details;
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL /* Cascade only for OneToMany*/)
    @JoinColumn(name = SchemaColumnConstantName.C_JOB, referencedColumnName = SchemaColumnConstantName.C_CODE
            , foreignKey = @ForeignKey(name = SchemaFkConstantName.FK_SHARED_WITH_REF_JOB))
    private List<JobShareInfo> jobShareInfos;
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = SchemaColumnConstantName.C_JOB, referencedColumnName = SchemaColumnConstantName.C_ID
            , foreignKey = @ForeignKey(name = SchemaFkConstantName.FK_ADDITIONAL_FILE_REF_JOB))
    private List<JobLinkedFile> additionalFiles;
}
