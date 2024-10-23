package eu.novobit.model;

import eu.novobit.constants.DomainConstants;
import eu.novobit.enumerations.IEnumContractType;
import eu.novobit.enumerations.IEnumTimeType;
import eu.novobit.enumerations.IEnumWorkMode;
import eu.novobit.model.base.AuditableCancelableEntity;
import eu.novobit.model.schema.*;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import java.time.LocalDate;
import java.util.List;

/**
 * The type Contract.
 */
@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = SchemaTableConstantName.T_CONTRACT, uniqueConstraints = {
        @UniqueConstraint(name = SchemaUcConstantName.UC_CONTRACT_CODE, columnNames = {SchemaColumnConstantName.C_CODE})
})
@SQLDelete(sql = "update " + SchemaTableConstantName.T_CONTRACT + " set " + SchemaColumnConstantName.C_CHECK_CANCEL + "= true , " + ComSchemaColumnConstantName.C_CANCEL_DATE + " = current_timestamp WHERE id = ?")
@Where(clause = SchemaColumnConstantName.C_CHECK_CANCEL + "=false")
public class Contract extends AuditableCancelableEntity<Long> implements ISASEntity, ICodifiable, IFileEntity {

    @Id
    @SequenceGenerator(name = "contract_sequence_generator", sequenceName = "contract_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "contract_sequence_generator")
    @Column(name = SchemaColumnConstantName.C_ID, updatable = false, nullable = false)
    private Long id;
    @ColumnDefault("'" + DomainConstants.DEFAULT_DOMAIN_NAME + "'")
    @Column(name = SchemaColumnConstantName.C_DOMAIN, length = SchemaConstantSize.DOMAIN, updatable = false, nullable = false)
    private String domain;
    @Column(name = SchemaColumnConstantName.C_CODE, length = SchemaConstantSize.CODE, updatable = false, nullable = false)
    private String code;
    @Column(name = SchemaColumnConstantName.C_EMPLOYEE_ID, updatable = false)
    private Long employee;
    @Column(name = SchemaColumnConstantName.C_ORIGINAL_FILE_NAME, length = ComSchemaConstantSize.FILE_NAME_SIZE)
    private String originalFileName;
    @Column(name = SchemaColumnConstantName.C_EXTENSION, length = ComSchemaConstantSize.EXTENSION_SIZE)
    private String extension;
    @ElementCollection
    private List<String> tags;
    @Column(name = SchemaColumnConstantName.C_LOCATION, length = SchemaConstantSize.XL_VALUE)
    private String location;
    @Enumerated(EnumType.STRING)
    @Column(name = SchemaColumnConstantName.C_WORKING_MODE, length = IEnumWorkMode.STR_ENUM_SIZE)
    private IEnumWorkMode.Types workingMode;
    @Enumerated(EnumType.STRING)
    @Column(name = SchemaColumnConstantName.C_CONTRACT_TYPE, length = IEnumContractType.STR_ENUM_SIZE)
    private IEnumContractType.Types contract;
    @Enumerated(EnumType.STRING)
    @Column(name = SchemaColumnConstantName.C_AVAILABILITY, length = IEnumTimeType.STR_ENUM_SIZE)
    private IEnumTimeType.Types availability;
    @Column(name = SchemaColumnConstantName.C_PROBATIONARY_PERIOD)
    private Integer probationaryPeriod;
    @Column(name = SchemaColumnConstantName.C_START_DATE)
    private LocalDate startDate;
    @Column(name = SchemaColumnConstantName.C_END_DATE)
    private LocalDate endDate;
    @Column(name = SchemaColumnConstantName.C_VACATION_BALANCE)
    private Integer vacationBalance;
    @Column(name = SchemaColumnConstantName.C_IS_RENEWABLE)
    private Boolean isRenewable;
    @Column(name = SchemaColumnConstantName.C_IS_LOCKED)
    private Boolean isLocked;
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = SchemaColumnConstantName.C_SALARY_INFO, referencedColumnName = SchemaColumnConstantName.C_ID
            , foreignKey = @ForeignKey(name = SchemaFkConstantName.FK_CONTRACT_REF_SALARY_INFO))
    private SalaryInformation salaryInformation;
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = SchemaColumnConstantName.C_HOLIDAY_INFO, referencedColumnName = SchemaColumnConstantName.C_ID
            , foreignKey = @ForeignKey(name = SchemaFkConstantName.FK_CONTRACT_REF_HOLIDAY_INFO))
    private HolidayInformation holidayInformation;

}
