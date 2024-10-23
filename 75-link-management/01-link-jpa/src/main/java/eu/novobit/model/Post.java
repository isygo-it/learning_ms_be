package eu.novobit.model;

import eu.novobit.constants.DomainConstants;
import eu.novobit.model.base.AuditableEntity;
import eu.novobit.model.schema.SchemaColumnConstantName;
import eu.novobit.model.schema.SchemaConstantSize;
import eu.novobit.model.schema.SchemaFkConstantName;
import eu.novobit.model.schema.SchemaTableConstantName;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.ColumnDefault;

import java.util.List;

/**
 * The type Post.
 */
@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = SchemaTableConstantName.T_POST)
public class Post extends AuditableEntity<Long> implements ISASEntity {

    @Id
    @SequenceGenerator(name = "post_sequence_generator", sequenceName = "post_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "post_sequence_generator")
    @Column(name = SchemaColumnConstantName.C_ID, updatable = false, nullable = false)
    @OrderBy(SchemaColumnConstantName.C_CREATE_DATE + " DESC")

    private Long id;

    @ColumnDefault("'" + DomainConstants.DEFAULT_DOMAIN_NAME + "'")
    @Column(name = SchemaColumnConstantName.C_DOMAIN, length = SchemaConstantSize.DOMAIN, updatable = false, nullable = false)
    private String domain;

    @Column(name = SchemaColumnConstantName.C_ACCOUNT, length = SchemaConstantSize.CODE, updatable = false, nullable = false)
    private String accountCode;

    @Column(name = SchemaColumnConstantName.C_TITLE, length = SchemaConstantSize.S_TITLE, nullable = false)
    private String title;

    @Column(name = SchemaColumnConstantName.C_TALK, length = SchemaConstantSize.XXL_DESCRIPTION, nullable = false)
    private String talk;

    @OrderBy(SchemaColumnConstantName.C_CREATE_DATE + " DESC")
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = SchemaColumnConstantName.C_POST_ID, referencedColumnName = SchemaColumnConstantName.C_ID
            , foreignKey = @ForeignKey(name = SchemaFkConstantName.FK_COMMENT_REF_POST))
    private List<Comment> comments;

    @ElementCollection
    @CollectionTable(name = SchemaTableConstantName.T_POST_LIKED
            , joinColumns = @JoinColumn(name = SchemaColumnConstantName.C_POST_ID,
            referencedColumnName = SchemaColumnConstantName.C_ID,
            foreignKey = @ForeignKey(name = SchemaFkConstantName.FK_POST_REF_ACCOUNT)))
    @Column(name = SchemaColumnConstantName.C_ACCOUNT_CODE)
    private List<String> usersAccountCode;

}
