package eu.novobit.model;

import eu.novobit.model.base.AuditableEntity;
import eu.novobit.model.schema.SchemaColumnConstantName;
import eu.novobit.model.schema.SchemaTableConstantName;
import eu.novobit.model.schema.SchemaUcConstantName;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * The type Property.
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = SchemaTableConstantName.T_PROPERTY
        , uniqueConstraints = {
        @UniqueConstraint(name = SchemaUcConstantName.UC_ACCOUNT_PROPERTY, columnNames = {SchemaColumnConstantName.C_GUI_NAME
                , SchemaColumnConstantName.C_NAME, SchemaColumnConstantName.C_PROPS_ID})
})
public class Property extends AuditableEntity<Long> {

    @Id
    @SequenceGenerator(name = "account_props_sequence_generator", sequenceName = "account_props_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "account_props_sequence_generator")
    @Column(name = SchemaColumnConstantName.C_ID, updatable = false, nullable = false)
    private Long id;

    @Column(name = SchemaColumnConstantName.C_GUI_NAME)
    private String guiName;

    @Column(name = SchemaColumnConstantName.C_NAME)
    private String name;

    @Column(name = SchemaColumnConstantName.C_VALUE)
    private String value;
}
