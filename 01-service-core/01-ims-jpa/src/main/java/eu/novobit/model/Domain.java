package eu.novobit.model;

import eu.novobit.model.extendable.DomainModel;
import eu.novobit.model.schema.*;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;


/**
 * The type Domain.
 */
@Data
@SuperBuilder
@NoArgsConstructor
@Entity
@Table(name = SchemaTableConstantName.T_DOMAIN
        , uniqueConstraints = {
        @UniqueConstraint(name = SchemaUcConstantName.UC_DOMAIN, columnNames = {SchemaColumnConstantName.C_NAME})
})
@SQLDelete(sql = "update " + SchemaTableConstantName.T_DOMAIN + " set " + SchemaColumnConstantName.C_CHECK_CANCEL + "= true , " + ComSchemaColumnConstantName.C_CANCEL_DATE + " = current_timestamp WHERE id = ?")
@Where(clause = SchemaColumnConstantName.C_CHECK_CANCEL + "=false")
public class Domain extends DomainModel<Long> implements ISASEntity, IImageEntity, ICodifiable {

    @Id
    @SequenceGenerator(name = "domain_sequence_generator", sequenceName = "domain_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "domain_sequence_generator")
    @Column(name = ComSchemaColumnConstantName.C_ID, updatable = false, nullable = false)
    private Long id;

    @Column(name = SchemaColumnConstantName.C_DOMAIN)
    private String domain;

    @Column(name = SchemaColumnConstantName.C_CODE, length = ComSchemaConstantSize.CODE, updatable = false, nullable = false)
    private String code;

    @Column(name = SchemaColumnConstantName.C_LNK_FACEBOOK)
    private String lnk_facebook;
    @Column(name = SchemaColumnConstantName.C_LNK_LINKEDIN)
    private String lnk_linkedin;
    @Column(name = SchemaColumnConstantName.C_LNK_XING)
    private String lnk_xing;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL /* Cascade only for OneToMany*/)
    @JoinColumn(name = SchemaColumnConstantName.C_ADDRESS_ID
            , foreignKey = @ForeignKey(name = SchemaFkConstantName.FK_DOMAIN_REF_ADDRESS))
    private DomainAddress address;

    @Column(name = ComSchemaColumnConstantName.C_LOGO)
    private String imagePath;
}
