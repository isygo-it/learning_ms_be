package eu.novobit.model;

import eu.novobit.model.schema.SchemaTableConstantName;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * The type Job soft skills.
 */
@Data
@SuperBuilder
@NoArgsConstructor
@Entity
@Table(name = SchemaTableConstantName.T_JOB_SKILLS)
@DiscriminatorValue("SOFT")
public class JobSoftSkills extends JobSkills {

}
