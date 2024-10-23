package eu.novobit.model;

import eu.novobit.enumerations.IEnumQuestionType;
import eu.novobit.model.base.AuditableEntity;
import eu.novobit.model.schema.*;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.ColumnDefault;

import java.util.List;

/**
 * The type Quiz question.
 */
@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = SchemaTableConstantName.T_QUIZ_QUESTION)
public class QuizQuestion extends AuditableEntity<Long> {

    @Id
    @SequenceGenerator(name = "quiz_question_sequence_generator", sequenceName = "quiz_question_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "quiz_question_sequence_generator")
    @Column(name = SchemaColumnConstantName.C_ID, updatable = false, nullable = false)
    private Long id;

    @Column(name = SchemaColumnConstantName.C_QUIZ_QUESTION, length = SchemaConstantSize.S_QUESTION, nullable = false)
    private String question;

    @Builder.Default
    @ColumnDefault("'MCQ'")
    @Enumerated(EnumType.STRING)
    @Column(name = ComSchemaColumnConstantName.C_TYPE, length = IEnumQuestionType.STR_ENUM_SIZE, nullable = false)
    private IEnumQuestionType.Types type = IEnumQuestionType.Types.MCQ;

    @Column(name = SchemaColumnConstantName.C_RANK)
    private Integer order;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL /* Cascade only for OneToMany*/)
    @JoinColumn(name = SchemaColumnConstantName.C_QUIZ_QUESTION, referencedColumnName = SchemaColumnConstantName.C_ID
            , foreignKey = @ForeignKey(name = SchemaFkConstantName.FK_SECTION_REF_QUIZ))
    private List<QuizOption> options;

    @Column(name = SchemaColumnConstantName.C_TEXT_ANSWER, length = ComSchemaConstantSize.XXL_DESCRIPTION)
    private String textAnswer;
}
