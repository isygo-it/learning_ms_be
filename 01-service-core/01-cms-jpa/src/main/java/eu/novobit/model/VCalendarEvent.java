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

import java.util.Date;

/**
 * The type V calendar event.
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = SchemaTableConstantName.T_EVENT, uniqueConstraints = {
        @UniqueConstraint(name = SchemaUcConstantName.UC_EVENT_DOMAIN_CALENDAR_CODE,
                columnNames = {SchemaColumnConstantName.C_DOMAIN, SchemaColumnConstantName.C_CALENDAR, SchemaColumnConstantName.C_CODE})})
@SQLDelete(sql = "update " + SchemaTableConstantName.T_EVENT + " set " + SchemaColumnConstantName.C_CHECK_CANCEL + "= true , " + ComSchemaColumnConstantName.C_CANCEL_DATE + " = current_timestamp WHERE id = ?")
@Where(clause = SchemaColumnConstantName.C_CHECK_CANCEL + "=false")
public class VCalendarEvent extends AuditableCancelableEntity<Long> implements ISASEntity, ICodifiable {

    @Id
    @SequenceGenerator(name = "event_sequence_generator", sequenceName = "event_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "event_sequence_generator")
    @Column(name = SchemaColumnConstantName.C_ID, updatable = false, nullable = false)
    private Long id;

    @ColumnDefault("'" + DomainConstants.DEFAULT_DOMAIN_NAME + "'")
    @Column(name = SchemaColumnConstantName.C_DOMAIN, length = SchemaConstantSize.DOMAIN, updatable = false, nullable = false)
    private String domain;

    @Column(name = SchemaColumnConstantName.C_CALENDAR, updatable = false, nullable = false)
    private String calendar;

    @Column(name = SchemaColumnConstantName.C_CODE, length = SchemaConstantSize.CODE, updatable = false, nullable = false)
    private String code;

    @Column(name = SchemaColumnConstantName.C_TITLE, updatable = false, nullable = false)
    private String title;

    @Column(name = SchemaColumnConstantName.C_NAME, length = SchemaConstantSize.S_NAME, nullable = false)
    private String name;

    @Column(name = ComSchemaColumnConstantName.C_DESCRIPTION, length = ComSchemaConstantSize.DESCRIPTION)
    private String description;

    @Column(name = SchemaColumnConstantName.C_TYPE, nullable = false)
    private String type;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = SchemaColumnConstantName.C_START_DATE, nullable = false)
    private Date startDate;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = SchemaColumnConstantName.C_END_DATE, nullable = false)
    private Date endDate;

    @Column(name = SchemaColumnConstantName.C_COMMENT, length = ComSchemaConstantSize.COMMENT)
    private String comment;
}
