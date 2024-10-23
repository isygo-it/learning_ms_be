package eu.novobit.model;

import eu.novobit.model.base.AuditableEntity;
import eu.novobit.model.schema.*;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;

/**
 * The type Role info.
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = SchemaTableConstantName.T_ROLE_INFO
        , uniqueConstraints = {@UniqueConstraint(name = SchemaUcConstantName.UC_ROLE_INFO_CODE
        , columnNames = {SchemaColumnConstantName.C_CODE})
})
public class RoleInfo extends AuditableEntity<Long> implements ICodifiable {

    @Id
    @SequenceGenerator(name = "role_info_sequence_generator", sequenceName = "role_info_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "role_info_sequence_generator")
    private Long id;

    @Column(name = SchemaColumnConstantName.C_CODE, length = SchemaConstantSize.CODE, updatable = false, nullable = false)
    private String code;

    @Column(name = SchemaColumnConstantName.C_NAME, nullable = false)
    private String name;

    @Column(name = SchemaColumnConstantName.C_DESCRIPTION, length = ComSchemaConstantSize.DESCRIPTION)
    private String description;

    @OrderBy(ComSchemaColumnConstantName.C_RANK + " ASC")
    @ManyToMany(fetch = FetchType.LAZY /* NO CASCADE */)
    @JoinTable(name = SchemaTableConstantName.T_ASSO_ROLE_INFO_APPLICATION,
            joinColumns = @JoinColumn(name = SchemaColumnConstantName.C_ROLE_INFO_CODE, referencedColumnName = SchemaColumnConstantName.C_CODE),
            inverseJoinColumns = @JoinColumn(name = SchemaColumnConstantName.C_APPLICATION_CODE, referencedColumnName = SchemaColumnConstantName.C_CODE))
    private List<Application> allowedTools;

    @ManyToMany(fetch = FetchType.LAZY /* NO CASCADE */)
    @JoinTable(name = SchemaTableConstantName.T_ASSO_ROLE_PERMISSION,
            joinColumns = @JoinColumn(name = SchemaColumnConstantName.C_ROLE, referencedColumnName = SchemaColumnConstantName.C_CODE),
            inverseJoinColumns = @JoinColumn(name = SchemaColumnConstantName.C_PERMISSION, referencedColumnName = SchemaColumnConstantName.C_ID))
    private List<ApiPermission> permissions;
}
