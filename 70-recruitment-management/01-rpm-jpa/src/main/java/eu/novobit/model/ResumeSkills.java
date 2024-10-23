package eu.novobit.model;

import eu.novobit.enumerations.IEnumSkillLevelType;
import eu.novobit.model.base.AuditableEntity;
import eu.novobit.model.schema.ComSchemaConstantSize;
import eu.novobit.model.schema.SchemaColumnConstantName;
import eu.novobit.model.schema.SchemaTableConstantName;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.ColumnDefault;

/**
 * The type Resume skills.
 */
@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = SchemaTableConstantName.T_RESUME_SKILLS)
public class ResumeSkills extends AuditableEntity<Long> {

    @Id
    @SequenceGenerator(name = "skills_sequence_generator", sequenceName = "skills_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "skills_sequence_generator")
    @Column(name = SchemaColumnConstantName.C_ID, updatable = false, nullable = false)
    private Long id;

    @Column(name = SchemaColumnConstantName.C_NAME, length = ComSchemaConstantSize.S_NAME)
    private String name;

    @Builder.Default
    @ColumnDefault("'BEGINNER'")
    @Enumerated(EnumType.STRING)
    @Column(name = SchemaColumnConstantName.C_LEVEL, length = IEnumSkillLevelType.STR_ENUM_SIZE, nullable = false)
    private IEnumSkillLevelType.Types level = IEnumSkillLevelType.Types.BEGINNER;

    @Builder.Default
    @ColumnDefault("'0'")
    @Column(name = SchemaColumnConstantName.C_SCORE)
    private Double score = 0D;
}
