package eu.novobit.model;

import eu.novobit.model.extendable.DomainModel;
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
 * The type Kms domain.
 */
@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = SchemaTableConstantName.T_DOMAIN
        , uniqueConstraints = {@UniqueConstraint(name = SchemaUcConstantName.UC_DOMAIN,
        columnNames = {SchemaColumnConstantName.C_NAME})
})
public class KmsDomain extends DomainModel<Long> {

    @Id
    @SequenceGenerator(name = "domain_sequence_generator", sequenceName = "domain_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "domain_sequence_generator")
    @Column(name = ComSchemaColumnConstantName.C_ID, updatable = false, nullable = false)
    private Long id;

    /*    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL *//* CASCADE only for OneToOne*//*)
    @JoinColumn(name = SchemaColumnConstantName.C_PEB_CONFG, referencedColumnName = SchemaColumnConstantName.C_CODE
            , foreignKey = @ForeignKey(name = SchemaFkConstantName.FK_DOMAIN_REF_PEB_CONFIG))
    private PEBConfig pebConfig;*/

    /*    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL *//* CASCADE only for OneToOne*//*)
    @JoinColumn(name = SchemaColumnConstantName.C_DIGESTER_CONFIG, referencedColumnName = SchemaColumnConstantName.C_CODE
            , foreignKey = @ForeignKey(name = SchemaFkConstantName.FK_DOMAIN_REF_DIGESTER_CONFIG))
    private DigestConfig digestConfig;*/

    /*    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL *//* CASCADE only for OneToOne*//*)
    @JoinColumn(name = SchemaColumnConstantName.C_TOKEN_CONFIG, referencedColumnName = SchemaColumnConstantName.C_CODE
            , foreignKey = @ForeignKey(name = SchemaFkConstantName.FK_DOMAIN_REF_TOKEN_CONFIG))
    private TokenConfig tokenConfig;*/

    /*    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL *//* CASCADE only for OneToOne*//*)
    @JoinColumn(name = SchemaColumnConstantName.C_PASSWORD_CONFIG, referencedColumnName = SchemaColumnConstantName.C_CODE
            , foreignKey = @ForeignKey(name = SchemaFkConstantName.FK_DOMAIN_REF_PASSWORD_CONFIG))
    private PasswordConfig passwordConfig;*/

    /*    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL *//* Cascade only for OneToMany*//*)
    @JoinColumn(name = SchemaColumnConstantName.C_DOMAIN, referencedColumnName = SchemaColumnConstantName.C_NAME
            , foreignKey = @ForeignKey(name = SchemaFkConstantName.FK_ACCOUNT_REF_DOMAIN))
    private List<Account> accounts;*/
}
