package eu.novobit.model;

import eu.novobit.constants.DomainConstants;
import eu.novobit.enumerations.IEnumArticleType;
import eu.novobit.enumerations.IEnumSkillLevelType;
import eu.novobit.model.base.AuditableEntity;
import eu.novobit.model.schema.*;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.ColumnDefault;

import java.util.List;


/**
 * The type Article.
 */
@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = SchemaTableConstantName.T_ARTICLE,
        uniqueConstraints = {
                @UniqueConstraint(name = SchemaUcConstantName.UC_ARTICLE_CODE, columnNames = {SchemaColumnConstantName.C_CODE})
        })
public class Article extends AuditableEntity<Long> implements ISASEntity, ICodifiable, ITLEntity, IFileEntity {

    @Id
    @SequenceGenerator(name = "article_sequence_generator", sequenceName = "article_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "article_sequence_generator")
    @Column(name = SchemaColumnConstantName.C_ID, updatable = false, nullable = false)
    private Long id;

    @Column(name = SchemaColumnConstantName.C_CODE, length = SchemaConstantSize.CODE, updatable = false, nullable = false)
    private String code;

    @ColumnDefault("'" + DomainConstants.DEFAULT_DOMAIN_NAME + "'")
    @Column(name = SchemaColumnConstantName.C_DOMAIN, length = SchemaConstantSize.DOMAIN, updatable = false, nullable = false)
    private String domain;

    @Enumerated(EnumType.STRING)
    @Column(name = SchemaColumnConstantName.C_TYPE, length = IEnumArticleType.STR_ENUM_SIZE)
    private IEnumArticleType.Types type;


    @Column(name = SchemaColumnConstantName.C_NAME, length = SchemaConstantSize.S_NAME, nullable = false)
    private String name;

    @Column(name = SchemaColumnConstantName.C_TITLE, length = SchemaConstantSize.S_TITLE, nullable = false)
    private String title;

    @Column(name = SchemaColumnConstantName.C_DESCRIPTION, length = ComSchemaConstantSize.DESCRIPTION)
    private String description;

    @Column(name = SchemaColumnConstantName.C_ORIGINAL_FILE_NAME, length = ComSchemaConstantSize.FILE_NAME_SIZE, nullable = false)
    private String originalFileName;

    @Column(name = SchemaColumnConstantName.C_EXTENSION, length = ComSchemaConstantSize.EXTENSION_SIZE)
    private String extension;

    @ElementCollection
    @Column(name = SchemaColumnConstantName.C_TAG)
    private List<String> tags;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    @JoinColumn(name = SchemaColumnConstantName.C_AUTHOR_ID, referencedColumnName = SchemaColumnConstantName.C_ID, foreignKey = @ForeignKey(name = SchemaFkConstantName.C_AUTHOR_ID))
    private Author author;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    @JoinTable(name = SchemaTableConstantName.T_ARTICLE_TOPIC,
            joinColumns = @JoinColumn(name = SchemaColumnConstantName.C_ARTICLE_CODE, referencedColumnName = SchemaColumnConstantName.C_CODE),
            inverseJoinColumns = @JoinColumn(name = SchemaColumnConstantName.C_TOPIC_CODE, referencedColumnName = SchemaColumnConstantName.C_CODE))
    private List<Topic> topics;
}
