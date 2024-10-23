package eu.novobit.model;

import eu.novobit.model.extendable.AnnexModel;
import eu.novobit.model.schema.ComSchemaColumnConstantName;
import eu.novobit.model.schema.SchemaColumnConstantName;
import eu.novobit.model.schema.SchemaTableConstantName;
import eu.novobit.model.schema.SchemaUcConstantName;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * The type Annex.
 */
@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = SchemaTableConstantName.T_ANNEX, uniqueConstraints = {
        @UniqueConstraint(name = SchemaUcConstantName.UC_CODETABLE_VALUE_AND_LANGUAGE, columnNames = {SchemaColumnConstantName.C_CODE, SchemaColumnConstantName.C_VALUE,
                SchemaColumnConstantName.C_LANGUAGE_CODE})})
public class Annex extends AnnexModel<Long> {

    @Id
    @SequenceGenerator(name = "annex_sequence_generator", sequenceName = "annex_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "annex_sequence_generator")
    @Column(name = ComSchemaColumnConstantName.C_ID, updatable = false, nullable = false)
    private Long id;
}
