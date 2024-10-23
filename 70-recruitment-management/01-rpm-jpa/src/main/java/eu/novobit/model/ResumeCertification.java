package eu.novobit.model;

import eu.novobit.model.base.AuditableEntity;
import eu.novobit.model.schema.ComSchemaConstantSize;
import eu.novobit.model.schema.SchemaColumnConstantName;
import eu.novobit.model.schema.SchemaTableConstantName;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.Date;

/**
 * The type Resume certification.
 */
@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = SchemaTableConstantName.T_RESUME_CERTIFICATION)
public class ResumeCertification extends AuditableEntity<Long> {

    @Id
    @SequenceGenerator(name = "resume_certif_sequence_generator", sequenceName = "resume_certif_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "resume_certif_sequence_generator")
    @Column(name = SchemaColumnConstantName.C_ID, updatable = false, nullable = false)
    private Long id;

    @Column(name = SchemaColumnConstantName.C_NAME, length = ComSchemaConstantSize.S_NAME)
    private String name;
    @Column(name = SchemaColumnConstantName.C_LINK, length = ComSchemaConstantSize.LINK)
    private String link;
    @Column(name = SchemaColumnConstantName.C_DATE_OBTAINED)
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateOfObtained;
}
