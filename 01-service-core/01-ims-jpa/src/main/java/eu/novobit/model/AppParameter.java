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
 * The type App parameter.
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = SchemaTableConstantName.T_APP_PARAMETER
        , uniqueConstraints = {
        @UniqueConstraint(
                name = SchemaUcConstantName.UC_APP_PARAM_DOMAIN_NAME,
                columnNames = {
                        SchemaColumnConstantName.C_DOMAIN, SchemaColumnConstantName.C_NAME
                })
})
public class AppParameter extends AuditableEntity<Long> implements ISASEntity {

    @Id
    @SequenceGenerator(name = "app_parameter_sequence_generator", sequenceName = "app_parameter_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "account_sequence_generator")
    @Column(name = SchemaColumnConstantName.C_ID, updatable = false, nullable = false)
    private Long id;

    @ColumnDefault("'" + DomainConstants.DEFAULT_DOMAIN_NAME + "'")
    @Column(name = SchemaColumnConstantName.C_DOMAIN, length = SchemaConstantSize.DOMAIN, updatable = false, nullable = false)
    private String domain;
    @Column(name = SchemaColumnConstantName.C_NAME, length = SchemaConstantSize.S_NAME, nullable = false)
    private String name;
    @Column(name = SchemaColumnConstantName.C_VALUE, length = SchemaConstantSize.XXL_VALUE, nullable = false)
    private String value;
    @Column(name = SchemaColumnConstantName.C_DESCRIPTION, length = SchemaConstantSize.DESCRIPTION)
    private String description;
}
