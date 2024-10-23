package eu.novobit.model;

import eu.novobit.enumerations.IEnumLanguageLevelType;
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
 * The type Resume language.
 */
@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = SchemaTableConstantName.T_RESUME_LANGUAGE)
public class ResumeLanguage extends AuditableEntity<Long> {

    @Id
    @SequenceGenerator(name = "language_sequence_generator", sequenceName = "language_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "language_sequence_generator")
    @Column(name = SchemaColumnConstantName.C_ID, updatable = false, nullable = false)
    private Long id;

    @Column(name = SchemaColumnConstantName.C_NAME, length = ComSchemaConstantSize.S_NAME)
    private String name;

    @Builder.Default
    @ColumnDefault("'BEGINNER'")
    @Enumerated(EnumType.STRING)
    @Column(name = SchemaColumnConstantName.C_LEVEL, length = IEnumLanguageLevelType.STR_ENUM_SIZE, nullable = false)
    private IEnumLanguageLevelType.Types level = IEnumLanguageLevelType.Types.BEGINNER;

}
