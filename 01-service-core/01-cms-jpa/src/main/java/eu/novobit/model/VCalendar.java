package eu.novobit.model;

import eu.novobit.constants.DomainConstants;
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

/**
 * The type V calendar.
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = SchemaTableConstantName.T_CALENDAR, uniqueConstraints = {
        @UniqueConstraint(name = SchemaUcConstantName.UC_CALENDAR_DOMAIN_NAME
                , columnNames = {SchemaColumnConstantName.C_DOMAIN, SchemaColumnConstantName.C_NAME})
})
@SQLDelete(sql = "update " + SchemaTableConstantName.T_CALENDAR + " set " + SchemaColumnConstantName.C_CHECK_CANCEL + "= true , " + ComSchemaColumnConstantName.C_CANCEL_DATE + " = current_timestamp WHERE id = ?")
@Where(clause = SchemaColumnConstantName.C_CHECK_CANCEL + "=false")
public class VCalendar extends AuditableCancelableEntity<Long> implements ISASEntity {

    @Id
    @SequenceGenerator(name = "calendar_sequence_generator", sequenceName = "calendar_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "calendar_sequence_generator")
    @Column(name = SchemaColumnConstantName.C_ID, updatable = false, nullable = false)
    private Long id;

    @ColumnDefault("'" + DomainConstants.DEFAULT_DOMAIN_NAME + "'")
    @Column(name = SchemaColumnConstantName.C_DOMAIN, length = SchemaConstantSize.DOMAIN, updatable = false, nullable = false)
    private String domain;

    @Column(name = SchemaColumnConstantName.C_NAME, nullable = false)
    private String name;

    @Column(name = ComSchemaColumnConstantName.C_DESCRIPTION, length = ComSchemaConstantSize.DESCRIPTION)
    private String description;

    @Column(name = SchemaColumnConstantName.C_PATH, nullable = false)
    private String icsPath;
}
