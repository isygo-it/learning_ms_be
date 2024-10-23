package eu.novobit.model;

import eu.novobit.model.base.AuditableEntity;
import eu.novobit.model.schema.ComSchemaConstantSize;
import eu.novobit.model.schema.SchemaColumnConstantName;
import eu.novobit.model.schema.SchemaFkConstantName;
import eu.novobit.model.schema.SchemaTableConstantName;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * The type Asso account resume.
 */
@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = SchemaTableConstantName.T_ASSO_ACCOUNT_RESUME)
public class AssoAccountResume extends AuditableEntity<Long> {

    @Id
    @SequenceGenerator(name = "account_resume_sequence_generator", sequenceName = "account_resume_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "account_resume_sequence_generator")
    @Column(name = SchemaColumnConstantName.C_ID, updatable = false, nullable = false)
    private Long id;

    @Column(name = SchemaColumnConstantName.C_ACCOUNT_CODE, length = ComSchemaConstantSize.CODE, nullable = false)
    private String accountCode;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL /* CASCADE only for OneToOne*/)
    @JoinColumn(name = SchemaColumnConstantName.C_RESUME, referencedColumnName = SchemaColumnConstantName.C_CODE
            , foreignKey = @ForeignKey(name = SchemaFkConstantName.FK_ACCOUNT_REF_RESUME))
    private Resume resume;
}
