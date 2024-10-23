package eu.novobit.model;

import eu.novobit.converter.CamelCaseConverter;
import eu.novobit.model.base.AuditableEntity;
import eu.novobit.model.schema.SchemaColumnConstantName;
import eu.novobit.model.schema.SchemaConstantSize;
import eu.novobit.model.schema.SchemaFkConstantName;
import eu.novobit.model.schema.SchemaTableConstantName;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;

/**
 * The type Account details.
 */
@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = SchemaTableConstantName.T_ACCOUNT_DETAILS)
public class AccountDetails extends AuditableEntity<Long> {

    @Id
    @SequenceGenerator(name = "account_details_sequence_generator", sequenceName = "account_details_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "account_details_sequence_generator")
    @Column(name = SchemaColumnConstantName.C_ID, updatable = false, nullable = false)
    private Long id;

    @Convert(converter = CamelCaseConverter.class)
    @Column(name = SchemaColumnConstantName.C_FIRST_NAME, length = SchemaConstantSize.S_NAME, nullable = false)
    private String firstName;

    @Convert(converter = CamelCaseConverter.class)
    @Column(name = SchemaColumnConstantName.C_LAST_NAME, length = SchemaConstantSize.S_NAME, nullable = false)
    private String lastName;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL /* Cascade only for OneToMany*/)
    @JoinColumn(name = SchemaColumnConstantName.C_ACCOUNT_DETAILS_ID
            , foreignKey = @ForeignKey(name = SchemaFkConstantName.FK_ACCOUNT_DETAILS_REF_CONTACT))
    private List<AccountContact> contacts;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL /* Cascade only for OneToMany*/)
    @JoinColumn(name = SchemaColumnConstantName.C_ACCOUNT_DETAILS_ID
            , foreignKey = @ForeignKey(name = SchemaFkConstantName.FK_ACCOUNT_DETAILS_REF_ADDRESS))
    private AccountAddress address;

    @Column(name = SchemaColumnConstantName.C_COUNTRY_NAME, length = SchemaConstantSize.S_NAME)
    private String country;

    /**
     * Gets full name.
     *
     * @return the full name
     */
    public String getFullName() {
        return new StringBuilder()
                .append(this.getFirstName())
                .append(" ")
                .append(this.getLastName())
                .toString();
    }
}
