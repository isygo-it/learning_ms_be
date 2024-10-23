package eu.novobit.model;

import eu.novobit.constants.DomainConstants;
import eu.novobit.enumerations.IEnumInsuranceType;
import eu.novobit.model.base.AuditableEntity;
import eu.novobit.model.schema.ComSchemaConstantSize;
import eu.novobit.model.schema.SchemaColumnConstantName;
import eu.novobit.model.schema.SchemaConstantSize;
import eu.novobit.model.schema.SchemaTableConstantName;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.ColumnDefault;

import java.time.LocalDate;

/**
 * The type Insurance security.
 */
@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = SchemaTableConstantName.T_EMPLOYEE_INSURANCE)
public class InsuranceSecurity extends AuditableEntity<Long> implements IImageEntity, ISASEntity, ICodifiable {

    @Id
    @SequenceGenerator(name = "insurance_sequence_generator", sequenceName = "insurance_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "insurance_sequence_generator")
    @Column(name = SchemaColumnConstantName.C_ID, updatable = false, nullable = false)
    private Long id;
    @Column(name = SchemaColumnConstantName.C_SECURITY_NUMBER, length = ComSchemaConstantSize.S_NUMBER)
    private String cardNumber;
    @Column(name = SchemaColumnConstantName.C_ISSUED_DATE)
    private LocalDate issuedDate;
    @Column(name = SchemaColumnConstantName.C_EXPIRED_DATE)
    private LocalDate expiredDate;
    @Column(name = SchemaColumnConstantName.C_CITY_OF_DELIVERY, length = SchemaConstantSize.ISSUED_PLACE)
    private String issuedPlace;
    @Enumerated(EnumType.STRING)
    @Column(name = SchemaColumnConstantName.C_INSURANCE_TYPE, length = IEnumInsuranceType.STR_ENUM_SIZE)
    private IEnumInsuranceType.Types insuranceType;
    @Column(name = SchemaColumnConstantName.C_PHOTO)
    private String imagePath;
    @ColumnDefault("'" + DomainConstants.DEFAULT_DOMAIN_NAME + "'")
    @Column(name = SchemaColumnConstantName.C_DOMAIN, length = SchemaConstantSize.DOMAIN, updatable = false, nullable = false)
    private String domain;
    @Column(name = SchemaColumnConstantName.C_CODE, length = SchemaConstantSize.CODE, updatable = false, nullable = false)
    private String code;
    @Column(name = SchemaColumnConstantName.C_EMPLOYEE_DETAILS_ID, updatable = false)
    private Long employeeDetailsId;
}
