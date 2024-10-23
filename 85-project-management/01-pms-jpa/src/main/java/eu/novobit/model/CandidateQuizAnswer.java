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

/**
 * The type Candidate quiz answer.
 */
@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = SchemaTableConstantName.T_CANDIDATE_QUIZ_ANSWER)
public class CandidateQuizAnswer extends AuditableEntity<Long> {

    @Id
    @SequenceGenerator(name = "cand_quiz_answer_sequence_generator", sequenceName = "cand_quiz_answer_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "cand_quiz_answer_sequence_generator")
    @Column(name = SchemaColumnConstantName.C_ID, updatable = false, nullable = false)
    private Long id;

    @Column(name = SchemaColumnConstantName.C_QUIZ_QUESTION, nullable = false)
    private Long question;

    @Column(name = SchemaColumnConstantName.C_QUIZ_OPTION)
    private Long option;

    @Column(name = SchemaColumnConstantName.C_TEXT_ANSWER, length = ComSchemaConstantSize.XXL_DESCRIPTION)
    private String answerText;
}
