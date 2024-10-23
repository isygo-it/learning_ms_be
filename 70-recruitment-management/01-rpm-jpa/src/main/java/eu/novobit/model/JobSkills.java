package eu.novobit.model;

import eu.novobit.enumerations.IEnumSkillLevelType;
import eu.novobit.enumerations.IEnumSkillType;
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
 * The type Job skills.
 */
@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = SchemaTableConstantName.T_JOB_SKILLS)
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = SchemaColumnConstantName.C_TYPE, discriminatorType = DiscriminatorType.STRING)
public class JobSkills extends AuditableEntity<Long> {

    @Id
    @SequenceGenerator(name = "skills_sequence_generator", sequenceName = "skills_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "skills_sequence_generator")
    @Column(name = SchemaColumnConstantName.C_ID, updatable = false, nullable = false)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = SchemaColumnConstantName.C_TYPE, length = IEnumSkillType.STR_ENUM_SIZE, insertable = false, updatable = false, nullable = false)
    private IEnumSkillType.Types type;
    @Column(name = SchemaColumnConstantName.C_NAME, length = ComSchemaConstantSize.S_NAME)
    private String name;

    @Builder.Default
    @ColumnDefault("'BEGINNER'")
    @Enumerated(EnumType.STRING)
    @Column(name = SchemaColumnConstantName.C_LEVEL, length = IEnumSkillLevelType.STR_ENUM_SIZE, nullable = false)
    private IEnumSkillLevelType.Types level = IEnumSkillLevelType.Types.BEGINNER;
    @Builder.Default
    @ColumnDefault("'true'")
    @Column(name = ComSchemaConstantSize.C_IS_MANDATORY)
    private Boolean isMandatory = Boolean.TRUE;
}
