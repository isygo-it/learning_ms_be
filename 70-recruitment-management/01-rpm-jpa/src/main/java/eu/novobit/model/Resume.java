package eu.novobit.model;

import eu.novobit.constants.DomainConstants;
import eu.novobit.converter.CamelCaseConverter;
import eu.novobit.listener.TimeLineListener;
import eu.novobit.model.base.AuditableCancelableEntity;
import eu.novobit.model.schema.*;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.validation.constraints.Email;
import java.time.LocalDate;
import java.util.List;

/**
 * The type Resume.
 */
@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = SchemaTableConstantName.T_RESUME,
        uniqueConstraints = {
                @UniqueConstraint(name = SchemaUcConstantName.UC_RESUME_CODE, columnNames = {SchemaColumnConstantName.C_CODE}),
                @UniqueConstraint(name = SchemaUcConstantName.UC_RESUME_EMAIL, columnNames = {SchemaColumnConstantName.C_EMAIL}),
                @UniqueConstraint(name = SchemaUcConstantName.UC_RESUME_PHONE, columnNames = {SchemaColumnConstantName.C_PHONE_NUMBER})
        })
@EntityListeners(TimeLineListener.class)
@SQLDelete(sql = "update " + SchemaTableConstantName.T_RESUME + " set " + SchemaColumnConstantName.C_CHECK_CANCEL + "= true , " + ComSchemaColumnConstantName.C_CANCEL_DATE + " = current_timestamp WHERE id = ?")
@Where(clause = SchemaColumnConstantName.C_CHECK_CANCEL + "=false")
public class Resume extends AuditableCancelableEntity<Long> implements ISASEntity, ICodifiable, ITLEntity, IFileEntity, IImageEntity {

    @Id
    @SequenceGenerator(name = "resume_sequence_generator", sequenceName = "resume_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "resume_sequence_generator")
    @Column(name = SchemaColumnConstantName.C_ID, updatable = false, nullable = false)
    private Long id;

    @ColumnDefault("'" + DomainConstants.DEFAULT_DOMAIN_NAME + "'")
    @Column(name = SchemaColumnConstantName.C_DOMAIN, length = SchemaConstantSize.DOMAIN, updatable = false, nullable = false)
    private String domain;
    @Column(name = SchemaColumnConstantName.C_CODE, length = SchemaConstantSize.CODE, updatable = false, nullable = false)
    private String code;
    @Convert(converter = CamelCaseConverter.class)
    @Column(name = SchemaColumnConstantName.C_FIRST_NAME, length = SchemaConstantSize.S_NAME, nullable = false)
    private String firstName;
    @Convert(converter = CamelCaseConverter.class)
    @Column(name = SchemaColumnConstantName.C_LAST_NAME, length = SchemaConstantSize.S_NAME, nullable = false)
    private String lastName;
    @Column(name = SchemaColumnConstantName.C_NATIONALITY, length = SchemaConstantSize.NATIONALITY)
    private String nationality;
    @Column(name = SchemaColumnConstantName.C_BIRTHDAY)
    private LocalDate birthDate;
    @Column(name = SchemaColumnConstantName.C_EMAIL, length = SchemaConstantSize.EMAIL, nullable = false)
    @Email(message = "email.should.be.valid")
    private String email;
    @Column(name = SchemaColumnConstantName.C_PHONE_NUMBER, length = SchemaConstantSize.PHONE_NUMBER, nullable = false)
    private String phone;
    @Column(name = SchemaColumnConstantName.C_PHOTO)
    private String imagePath;
    @Column(name = SchemaColumnConstantName.C_SOURCE)
    private String source;
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL  /* CASCADE only for OneToOne*/)
    @JoinColumn(name = SchemaColumnConstantName.C_ADDRESS, referencedColumnName = SchemaColumnConstantName.C_ID
            , foreignKey = @ForeignKey(name = SchemaFkConstantName.FK_RESUME_REF_ADDRESS))
    private ResumeAddress address;
    @Column(name = SchemaColumnConstantName.C_ORIGINAL_FILE_NAME, length = ComSchemaConstantSize.FILE_NAME_SIZE, nullable = false)
    private String originalFileName;
    @Column(name = SchemaColumnConstantName.C_EXTENSION, length = ComSchemaConstantSize.EXTENSION_SIZE)
    private String extension;
    @Column(name = SchemaColumnConstantName.C_TITLE, length = ComSchemaConstantSize.S_NAME)
    private String title;
    @Column(name = SchemaColumnConstantName.C_PRESENTATION, length = ComSchemaConstantSize.XXL_DESCRIPTION)
    private String presentation;
    @Builder.Default
    @ColumnDefault("'true'")
    @Column(name = SchemaColumnConstantName.C_IS_LINKED_USER)
    private Boolean isLinkedToUser = Boolean.TRUE;
    @ElementCollection
    @CollectionTable(name = SchemaTableConstantName.T_RESUME_TAGS
            , joinColumns = @JoinColumn(name = SchemaColumnConstantName.C_RESUME,
            referencedColumnName = SchemaColumnConstantName.C_CODE,
            foreignKey = @ForeignKey(name = SchemaFkConstantName.FK_TAGS_REF_RESUME)))
    @Column(name = SchemaColumnConstantName.C_TAG)
    private List<String> tags;
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL  /* CASCADE only for OneToOne*/)
    @JoinColumn(name = SchemaColumnConstantName.C_RESUME_DTAILS, referencedColumnName = SchemaColumnConstantName.C_ID
            , foreignKey = @ForeignKey(name = SchemaFkConstantName.FK_RESUME_REF_DETAILS))
    private ResumeDetails details;
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL /* Cascade only for OneToMany*/)
    @JoinColumn(name = SchemaColumnConstantName.C_RESUME, referencedColumnName = SchemaColumnConstantName.C_CODE
            , foreignKey = @ForeignKey(name = SchemaFkConstantName.FK_SHARED_WITH_REF_RESUME))
    private List<ResumeShareInfo> resumeShareInfos;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = SchemaColumnConstantName.C_RESUME, referencedColumnName = SchemaColumnConstantName.C_ID
            , foreignKey = @ForeignKey(name = SchemaFkConstantName.FK_ADDITIONAL_FILE_REF_RESUME))
    private List<ResumeLinkedFile> additionalFiles;

    /**
     * Gets full name.
     *
     * @return the full name
     */
    public String getFullName() {
        return new StringBuilder()
                .append(this.getFirstName())
                .append(" ")
                .append(this.getLastName())
                .toString();
    }
}
