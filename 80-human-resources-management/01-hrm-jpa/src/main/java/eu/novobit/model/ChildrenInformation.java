package eu.novobit.model;

import eu.novobit.enumerations.IEnumEducationalLevel;
import eu.novobit.enumerations.IEnumGender;
import eu.novobit.model.schema.ComSchemaConstantSize;
import eu.novobit.model.schema.SchemaColumnConstantName;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Data;

import java.time.LocalDate;

/**
 * The type Children information.
 */
@Embeddable
@Data
public class ChildrenInformation {

    @Column(name = SchemaColumnConstantName.C_NAME, length = ComSchemaConstantSize.S_NAME)
    private String fullName;
    @Enumerated(EnumType.STRING)
    @Column(name = SchemaColumnConstantName.C_GENDER, length = IEnumGender.STR_ENUM_SIZE)
    private IEnumGender.Types gender;
    @Column(name = SchemaColumnConstantName.C_BIRTHDAY)
    private LocalDate birthDate;
    @Enumerated(EnumType.STRING)
    @Column(name = SchemaColumnConstantName.C_EDUCATION_LEVEL, length = IEnumEducationalLevel.STR_ENUM_SIZE)
    private IEnumEducationalLevel.Types educationalLevel;
}
