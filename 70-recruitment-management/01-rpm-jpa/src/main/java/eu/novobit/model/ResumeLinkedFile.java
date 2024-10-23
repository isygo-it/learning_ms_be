package eu.novobit.model;

import eu.novobit.model.extendable.LinkedFileModel;
import eu.novobit.model.schema.SchemaColumnConstantName;
import eu.novobit.model.schema.SchemaTableConstantName;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * The type Resume linked file.
 */
@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = SchemaTableConstantName.T_RESUME_LINKED_FILE)
public class ResumeLinkedFile extends LinkedFileModel<Long> {

    @Id
    @SequenceGenerator(name = "resume_additional_file_sequence_generator", sequenceName = "resume_additional_file_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "resume_additional_file_sequence_generator")
    @Column(name = SchemaColumnConstantName.C_ID, updatable = false, nullable = false)
    private Long id;
}
