package eu.novobit.model;

import eu.novobit.constants.DomainConstants;
import eu.novobit.model.base.AuditableEntity;
import eu.novobit.model.schema.SchemaColumnConstantName;
import eu.novobit.model.schema.SchemaConstantSize;
import eu.novobit.model.schema.SchemaTableConstantName;
import eu.novobit.model.schema.SchemaUcConstantName;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.ColumnDefault;

/**
 * The type Random key.
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = SchemaTableConstantName.T_RANDOM_KEY
        , uniqueConstraints = {
        @UniqueConstraint(name = SchemaUcConstantName.UC_RANDOM_KEY_DOMAIN,
                columnNames = {SchemaColumnConstantName.C_DOMAIN, SchemaColumnConstantName.C_NAME})
})
public class RandomKey extends AuditableEntity<Long> implements ISASEntity {

    @Id
    @SequenceGenerator(name = "random_key_sequence_generator", sequenceName = "random_key_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "random_key_sequence_generator")
    @Column(name = SchemaColumnConstantName.C_ID, updatable = false, nullable = false)
    private Long id;

    @ColumnDefault("'" + DomainConstants.DEFAULT_DOMAIN_NAME + "'")
    @Column(name = SchemaColumnConstantName.C_DOMAIN, length = SchemaConstantSize.DOMAIN, updatable = false, nullable = false)
    private String domain;

    @Column(name = SchemaColumnConstantName.C_NAME, updatable = false, nullable = false)
    private String name;

    @Column(name = SchemaColumnConstantName.C_VALUE, nullable = false)
    private String value;
}
