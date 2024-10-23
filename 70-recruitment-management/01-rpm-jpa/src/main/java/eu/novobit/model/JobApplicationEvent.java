package eu.novobit.model;

import eu.novobit.enumerations.IEnumJobAppEventType;
import eu.novobit.enumerations.IEnumJobAppStatusType;
import eu.novobit.model.base.AuditableEntity;
import eu.novobit.model.schema.ComSchemaConstantSize;
import eu.novobit.model.schema.SchemaColumnConstantName;
import eu.novobit.model.schema.SchemaFkConstantName;
import eu.novobit.model.schema.SchemaTableConstantName;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.ColumnDefault;

import java.util.List;

/**
 * The type Job application event.
 */
@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = SchemaTableConstantName.T_JOB_APPLICATION_EVENT)
public class JobApplicationEvent extends AuditableEntity<Long> implements IBoardEvent<IEnumJobAppEventType.Types> {

    @Id
    @SequenceGenerator(name = "interview_event_sequence_generator", sequenceName = "interview_event_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "interview_event_sequence_generator")
    @Column(name = SchemaColumnConstantName.C_ID, updatable = false, nullable = false)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = SchemaColumnConstantName.C_JOB_APPLICATION_EVENT_TYPE, length = IEnumJobAppEventType.STR_ENUM_SIZE)
    private IEnumJobAppEventType.Types type;

    @Builder.Default
    @Enumerated(EnumType.STRING)
    @ColumnDefault("'PLANNED'")
    @Column(name = SchemaColumnConstantName.C_JOB_APPLICATION_Event_STATUS_TYPE, length = IEnumJobAppStatusType.STR_ENUM_SIZE)
    private IEnumJobAppStatusType.Types statusType = IEnumJobAppStatusType.Types.PLANNED;
    @Column(name = SchemaColumnConstantName.C_CALENDAR, nullable = false)
    private String calendar;

    @Column(name = SchemaColumnConstantName.C_EVENT, nullable = false)
    private String eventCode;

    @Column(name = SchemaColumnConstantName.C_TITLE)
    private String title;

    @Column(name = SchemaColumnConstantName.C_LOCATION)
    private String location;

    @ElementCollection
    @CollectionTable(name = SchemaTableConstantName.T_PARTICIPANTS,
            joinColumns = @JoinColumn(name = SchemaColumnConstantName.C_JOB_APPLICATION,
                    referencedColumnName = SchemaColumnConstantName.C_ID,
                    foreignKey = @ForeignKey(name = SchemaFkConstantName.FK_PARTICIPANT_REF_EVENT)))
    @Column(name = SchemaColumnConstantName.C_PARTICIPANTS)
    private List<String> participants;

    @Column(name = SchemaColumnConstantName.C_COMMENT, length = ComSchemaConstantSize.COMMENT)
    private String comment;
}
