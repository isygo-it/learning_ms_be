package eu.novobit.model;

import eu.novobit.enumerations.IEnumPrimeType;
import eu.novobit.model.base.AuditableEntity;
import eu.novobit.model.schema.SchemaColumnConstantName;
import eu.novobit.model.schema.SchemaFkConstantName;
import eu.novobit.model.schema.SchemaTableConstantName;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.Set;

/**
 * The type Prime.
 */
@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = SchemaTableConstantName.T_CONTRACT_PRIME_INFOS)
public class Prime extends AuditableEntity<Long> {

    @Id
    @SequenceGenerator(name = "prime_sequence_generator", sequenceName = "prime_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "prime_sequence_generator")
    @Column(name = SchemaColumnConstantName.C_ID, updatable = false, nullable = false)
    private Long id;
    @Enumerated(EnumType.STRING)
    @Column(name = SchemaColumnConstantName.C_PRIME_TYPE, length = IEnumPrimeType.STR_ENUM_SIZE)
    private IEnumPrimeType.Types primeType;
    @Column(name = SchemaColumnConstantName.C_ANNUAL_MAX_AMOUNT)
    private Double annualMaxAmount;
    @Column(name = SchemaColumnConstantName.C_ANNUAL_MIN_AMOUNT)
    private Double annualMinAmount;
    @Column(name = SchemaColumnConstantName.C_ANNUAL_FREQUENCY)
    private Integer annualFrequency;
    @ElementCollection
    @CollectionTable(
            name = SchemaTableConstantName.T_DUE_DATE,
            joinColumns = @JoinColumn(name = SchemaColumnConstantName.C_ID, foreignKey = @ForeignKey(name = SchemaFkConstantName.FK_PRIME_REF_DUE_DATE))
    )
    private Set<DueDate> dueDates;

}
