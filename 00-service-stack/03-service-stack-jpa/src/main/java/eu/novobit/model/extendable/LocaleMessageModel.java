package eu.novobit.model.extendable;

import eu.novobit.model.IIdEntity;
import eu.novobit.model.schema.ComSchemaColumnConstantName;
import eu.novobit.model.schema.ComSchemaConstantSize;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * The type Locale message model.
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@MappedSuperclass
public class LocaleMessageModel implements IIdEntity<Long> {

    @Id
    @SequenceGenerator(name = "message_sequence_generator", sequenceName = "message_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "message_sequence_generator")
    private Long id;

    @Column(name = ComSchemaColumnConstantName.C_CODE, length = ComSchemaConstantSize.XXL_VALUE, nullable = false)
    private String code;
    @Column(name = ComSchemaColumnConstantName.C_LOCALE, length = ComSchemaConstantSize.LANG_CODE, nullable = false)
    private String locale;
    @Column(name = ComSchemaColumnConstantName.C_TEXT, length = ComSchemaConstantSize.XXL_VALUE, nullable = false)
    private String text;
}
