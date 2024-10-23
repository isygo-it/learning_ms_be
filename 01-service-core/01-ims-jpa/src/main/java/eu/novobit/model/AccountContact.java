package eu.novobit.model;

import eu.novobit.model.extendable.ContactModel;
import eu.novobit.model.schema.ComSchemaColumnConstantName;
import eu.novobit.model.schema.SchemaTableConstantName;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * The type Account contact.
 */
@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = SchemaTableConstantName.T_ACCOUNT_CONTACT)
public class AccountContact extends ContactModel<Long> {

    @Id
    @SequenceGenerator(name = "account_contact_sequence_generator", sequenceName = "account_contact_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "account_contact_sequence_generator")
    @Column(name = ComSchemaColumnConstantName.C_ID, updatable = false, nullable = false)
    private Long id;
}
