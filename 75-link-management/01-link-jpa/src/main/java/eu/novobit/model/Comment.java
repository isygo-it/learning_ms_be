package eu.novobit.model;

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

import java.util.List;


/**
 * The type Comment.
 */
@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = SchemaTableConstantName.T_POST_COMMENT)
public class Comment extends AuditableEntity<Long> {

    @Id
    @SequenceGenerator(name = "comment_sequence_generator", sequenceName = "comment_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "comment_sequence_generator")
    @Column(name = SchemaColumnConstantName.C_ID, updatable = false, nullable = false)
    private Long id;

    @Column(name = SchemaColumnConstantName.C_ACCOUNT, length = SchemaConstantSize.CODE, updatable = false, nullable = false)
    private String accountCode;

    @Column(name = SchemaColumnConstantName.C_COMMENT, length = SchemaConstantSize.XXL_DESCRIPTION, nullable = false)
    private String comment;

    @Column(name = SchemaColumnConstantName.C_POST_ID, updatable = false, nullable = false)
    private Long postId;

    @ElementCollection
    @CollectionTable(name = SchemaTableConstantName.T_COMMENT_LIKED
            , joinColumns = @JoinColumn(name = SchemaColumnConstantName.C_COMMENT_ID,
            referencedColumnName = SchemaColumnConstantName.C_ID,
            foreignKey = @ForeignKey(name = SchemaFkConstantName.FK_COMMENT_REF_ACCOUNT)))
    @Column(name = SchemaColumnConstantName.C_ACCOUNT_CODE)
    private List<String> usersAccountCode;

}
