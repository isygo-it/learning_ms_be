package eu.novobit.model;

import eu.novobit.constants.DomainConstants;
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
 * The type Passport.
 */
@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = SchemaTableConstantName.T_EMPLOYEE_PASSPORT)
public class Passport extends AuditableEntity<Long> implements IImageEntity, ISASEntity, ICodifiable {

    @Id
    @SequenceGenerator(name = "passport_sequence_generator", sequenceName = "passport_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "cin_sequence_generator")
    @Column(name = SchemaColumnConstantName.C_ID, updatable = false, nullable = false)
    private Long id;
    @Column(name = SchemaColumnConstantName.C_PASSPORT_NUMBER, length = ComSchemaConstantSize.PASSEPORT)
    private String cardNumber;
    @Column(name = SchemaColumnConstantName.C_ISSUED_DATE)
    private LocalDate issuedDate;
    @Column(name = SchemaColumnConstantName.C_EXPIRED_DATE)
    private LocalDate expiredDate;
    @Column(name = SchemaColumnConstantName.C_CITY_OF_DELIVERY, length = SchemaConstantSize.ISSUED_PLACE)
    private String issuedPlace;
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
