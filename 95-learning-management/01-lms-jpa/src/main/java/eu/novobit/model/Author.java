package eu.novobit.model;

import eu.novobit.constants.DomainConstants;
import eu.novobit.model.base.AuditableEntity;
import eu.novobit.model.schema.*;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.ColumnDefault;


/**
 * The type Author.
 */
@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = SchemaTableConstantName.T_AUTHOR
        , uniqueConstraints = {
        @UniqueConstraint(name = SchemaUcConstantName.UC_AUTHOR_CODE, columnNames = {SchemaColumnConstantName.C_CODE})
})
public class Author extends AuditableEntity<Long> implements ISASEntity, IImageEntity, ICodifiable {

    @Id
    @SequenceGenerator(name = "article_sequence_generator", sequenceName = "article_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "article_sequence_generator")
    @Column(name = SchemaColumnConstantName.C_ID, updatable = false, nullable = false)
    private Long id;

    @ColumnDefault("'" + DomainConstants.DEFAULT_DOMAIN_NAME + "'")
    @Column(name = SchemaColumnConstantName.C_DOMAIN, length = SchemaConstantSize.DOMAIN, updatable = false, nullable = false)
    private String domain;


    @Column(name = SchemaColumnConstantName.C_CODE, length = SchemaConstantSize.CODE, updatable = false , nullable = false)
    private String code;

    @Column(name = SchemaColumnConstantName.C_FIRST_NAME, length = SchemaConstantSize.S_NAME, nullable = false)
    private String firstname;
    @Column(name = SchemaColumnConstantName.C_LAST_NAME, length = SchemaConstantSize.S_NAME, nullable = false)
    private String lastname;

    @Column(name = SchemaColumnConstantName.C_EMAIL, length = ComSchemaConstantSize.EMAIL)
    private String email;

    @Column(name = SchemaColumnConstantName.C_PHONE_NUMBER, length = ComSchemaConstantSize.PHONE_NUMBER)
    private String phone;
    @Column(name = SchemaColumnConstantName.C_PHOTO)
    private String imagePath;



}
