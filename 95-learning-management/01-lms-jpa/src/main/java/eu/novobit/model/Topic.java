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
 * The type Topic.
 */
@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = SchemaTableConstantName.T_TOPIC
        , uniqueConstraints = {
        @UniqueConstraint(name = SchemaUcConstantName.UC_TOPIC_CODE, columnNames = {SchemaColumnConstantName.C_CODE})
})
@SQLDelete(sql = "update " + SchemaTableConstantName.T_TOPIC + " set " + SchemaColumnConstantName.C_CHECK_CANCEL + "= true , " + ComSchemaColumnConstantName.C_CANCEL_DATE + " = current_timestamp WHERE id = ?")
@Where(clause = SchemaColumnConstantName.C_CHECK_CANCEL + "=false")
public class Topic extends AuditableCancelableEntity<Long> implements ISASEntity, IImageEntity, ICodifiable {

    @Id
    @SequenceGenerator(name = "topic_sequence_generator", sequenceName = "topic_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "topic_sequence_generator")
    @Column(name = SchemaColumnConstantName.C_ID, updatable = false, nullable = false)
    private Long id;

    @Column(name = SchemaColumnConstantName.C_CODE, length = SchemaConstantSize.CODE, updatable = false, nullable = false)
    private String code;

    @ColumnDefault("'" + DomainConstants.DEFAULT_DOMAIN_NAME + "'")
    @Column(name = SchemaColumnConstantName.C_DOMAIN, length = SchemaConstantSize.DOMAIN, updatable = false, nullable = false)
    private String domain;

    @Column(name = SchemaColumnConstantName.C_NAME, length = SchemaConstantSize.S_NAME, nullable = false)
    private String name;

    @Column(name = SchemaColumnConstantName.C_DESCRIPTION, length = ComSchemaConstantSize.DESCRIPTION)
    private String description;

    @Column(name = SchemaColumnConstantName.C_PHOTO)
    private String imagePath;


}
