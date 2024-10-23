package eu.novobit.model;

import eu.novobit.enumerations.IEnumContractType;
import eu.novobit.enumerations.IEnumTimeType;
import eu.novobit.enumerations.IEnumWorkMode;
import eu.novobit.model.base.AuditableEntity;
import eu.novobit.model.schema.SchemaColumnConstantName;
import eu.novobit.model.schema.SchemaConstantSize;
import eu.novobit.model.schema.SchemaTableConstantName;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * The type Contract info.
 */
@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = SchemaTableConstantName.T_JOB_CONTRACT_INFO)
public class ContractInfo extends AuditableEntity<Long> {

    @Id
    @SequenceGenerator(name = "resume_details_sequence_generator", sequenceName = "resume_details_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "resume_details_sequence_generator")
    @Column(name = SchemaColumnConstantName.C_ID, updatable = false, nullable = false)
    private Long id;

    @Column(name = SchemaColumnConstantName.C_LOCATION, length = SchemaConstantSize.XL_VALUE)
    private String location;
    @Column(name = SchemaColumnConstantName.C_SALARY_MIN)
    private Integer salaryMin;
    @Column(name = SchemaColumnConstantName.C_SALARY_MAX)
    private Integer salaryMax;
    @Column(name = SchemaColumnConstantName.C_CURRENCY, length = SchemaConstantSize.XL_VALUE)
    private String currency;
    @Enumerated(EnumType.STRING)
    @Column(name = SchemaColumnConstantName.C_WORKING_MODE, length = IEnumWorkMode.STR_ENUM_SIZE)
    private IEnumWorkMode.Types workingMode;
    @Enumerated(EnumType.STRING)
    @Column(name = SchemaColumnConstantName.C_CONTRACT_TYPE, length = IEnumContractType.STR_ENUM_SIZE)
    private IEnumContractType.Types contract;
    @Enumerated(EnumType.STRING)
    @Column(name = SchemaColumnConstantName.C_AVAILABILITY, length = IEnumTimeType.STR_ENUM_SIZE)
    private IEnumTimeType.Types availability;
}
