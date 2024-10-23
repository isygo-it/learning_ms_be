package eu.novobit.model;

import eu.novobit.constants.DomainConstants;
import eu.novobit.enumerations.IEnumSkillLevelType;
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
 * The type Quiz.
 */
@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = SchemaTableConstantName.T_QUIZ
        , uniqueConstraints = {
        @UniqueConstraint(name = SchemaUcConstantName.UC_QUIZ_CODE, columnNames = {SchemaColumnConstantName.C_CODE})
})
@SQLDelete(sql = "update " + SchemaTableConstantName.T_QUIZ + " set " + SchemaColumnConstantName.C_CHECK_CANCEL + "= true , " + ComSchemaColumnConstantName.C_CANCEL_DATE + " = current_timestamp WHERE id = ?")
@Where(clause = SchemaColumnConstantName.C_CHECK_CANCEL + "=false")
public class Quiz extends AuditableCancelableEntity<Long> implements ISASEntity, ICodifiable {

    @Id
    @SequenceGenerator(name = "quiz_sequence_generator", sequenceName = "quiz_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "quiz_sequence_generator")
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

    @Column(name = SchemaColumnConstantName.C_CATEGORY, length = SchemaConstantSize.CATEGORY, nullable = false)
    private String category;

    @Builder.Default
    @ColumnDefault("'BEGINNER'")
    @Enumerated(EnumType.STRING)
    @Column(name = SchemaColumnConstantName.C_LEVEL, length = IEnumSkillLevelType.STR_ENUM_SIZE, nullable = false)
    private IEnumSkillLevelType.Types level = IEnumSkillLevelType.Types.BEGINNER;

    @OrderBy(ComSchemaColumnConstantName.C_RANK + " ASC")
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL /* Cascade only for OneToMany*/)
    @JoinColumn(name = SchemaColumnConstantName.C_QUIZ, referencedColumnName = SchemaColumnConstantName.C_CODE
            , foreignKey = @ForeignKey(name = SchemaFkConstantName.FK_SECTION_REF_QUIZ))
    private List<QuizSection> sections;

    @ElementCollection
    @CollectionTable(name = SchemaTableConstantName.T_QUIZ_TAGS
            , joinColumns = @JoinColumn(name = SchemaColumnConstantName.C_QUIZ,
            referencedColumnName = SchemaColumnConstantName.C_CODE,
            foreignKey = @ForeignKey(name = SchemaFkConstantName.FK_TAGS_REF_QUIZ)))
    @Column(name = SchemaColumnConstantName.C_TAG)
    private List<String> tags;
}
