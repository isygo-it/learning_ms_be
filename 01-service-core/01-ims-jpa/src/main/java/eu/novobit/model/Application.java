package eu.novobit.model;

import eu.novobit.constants.DomainConstants;
import eu.novobit.enumerations.IEnumBinaryStatus;
import eu.novobit.model.base.AuditableEntity;
import eu.novobit.model.schema.*;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.ColumnDefault;

import java.util.List;

/**
 * The type Application.
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = SchemaTableConstantName.T_APPLICATION, uniqueConstraints = {
        @UniqueConstraint(name = SchemaUcConstantName.UC_APPLICATION_CODE, columnNames = {SchemaColumnConstantName.C_CODE}),
        @UniqueConstraint(name = SchemaUcConstantName.UC_NAME, columnNames = {SchemaColumnConstantName.C_NAME})
})
public class Application extends AuditableEntity<Long> implements ISASEntity, ICodifiable, IImageEntity {

    @Id
    @SequenceGenerator(name = "application_sequence_generator", sequenceName = "application_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "application_sequence_generator")
    @Column(name = SchemaColumnConstantName.C_ID, updatable = false, nullable = false)
    private Long id;

    @ColumnDefault("'" + DomainConstants.DEFAULT_DOMAIN_NAME + "'")
    @Column(name = SchemaColumnConstantName.C_DOMAIN, length = SchemaConstantSize.DOMAIN, updatable = false, nullable = false)
    private String domain;
    @Column(name = SchemaColumnConstantName.C_NAME, length = SchemaConstantSize.S_NAME, nullable = false)
    private String name;
    @Column(name = SchemaColumnConstantName.C_TITLE, length = SchemaConstantSize.S_NAME, nullable = false)
    private String title;
    @Column(name = SchemaColumnConstantName.C_CODE, length = SchemaConstantSize.CODE, updatable = false, nullable = false)
    private String code;
    @Column(name = SchemaColumnConstantName.C_DESCRIPTION, length = ComSchemaConstantSize.DESCRIPTION)
    private String description;
    @Column(name = SchemaColumnConstantName.C_PHOTO)
    private String imagePath;
    @Column(name = SchemaColumnConstantName.C_URL, nullable = false)
    private String url;
    @Column(name = SchemaColumnConstantName.C_RANK)
    private Integer order;
    @Builder.Default
    @ColumnDefault("'ENABLED'")
    @Enumerated(EnumType.STRING)
    @Column(name = ComSchemaColumnConstantName.C_ADMIN_STATUS, length = IEnumBinaryStatus.STR_ENUM_SIZE, nullable = false)
    private IEnumBinaryStatus.Types adminStatus = IEnumBinaryStatus.Types.ENABLED;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL /* Cascade only for OneToMany*/)
    @JoinColumn(name = SchemaColumnConstantName.C_APPLICATION_CODE, referencedColumnName = SchemaColumnConstantName.C_CODE
            , foreignKey = @ForeignKey(name = SchemaFkConstantName.FK_APPLICATION_REF_SHORTCUT))
    private List<ApplicationShortcut> shortcuts;
}
