package eu.novobit.model;

import eu.novobit.enumerations.IEnumInterviewSkillType;
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
 * The type Interview skills.
 */
@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = SchemaTableConstantName.T_INTERVIEW_SKILLS)
public class InterviewSkills extends AuditableEntity<Long> {

    @Id
    @SequenceGenerator(name = "itw_skills_sequence_generator", sequenceName = "itw_skills_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "itw_skills_sequence_generator")
    @Column(name = SchemaColumnConstantName.C_ID, updatable = false, nullable = false)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = SchemaColumnConstantName.C_SKILL_TYPE, length = IEnumSkillLevelType.STR_ENUM_SIZE)
    private IEnumInterviewSkillType.Types type;

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
