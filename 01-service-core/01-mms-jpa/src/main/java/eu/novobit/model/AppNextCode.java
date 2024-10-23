package eu.novobit.model;

import eu.novobit.model.extendable.NextCodeModel;
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
 * The type App next code.
 */
@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = SchemaTableConstantName.T_APP_NEXT_CODE
        , uniqueConstraints = {@UniqueConstraint(name = SchemaUcConstantName.UC_NEXT_CODE_ENTITY
        , columnNames = {SchemaColumnConstantName.C_ENTITY, SchemaColumnConstantName.C_ATTRIBUTE, SchemaColumnConstantName.C_DOMAIN})})
public class AppNextCode extends NextCodeModel<Long> {

    @Id
    @SequenceGenerator(name = "next_code_sequence_generator", sequenceName = "next_code_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "next_code_sequence_generator")
    @Column(name = ComSchemaColumnConstantName.C_ID, updatable = false, nullable = false)
    private Long id;
}
