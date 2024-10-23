package eu.novobit.dto.data;

import eu.novobit.enumerations.IEnumEducationalLevel;
import eu.novobit.enumerations.IEnumGender;
import lombok.Data;

import java.time.LocalDate;

/**
 * The type Children information dto.
 */
@Data

public class ChildrenInformationDto {
    private IEnumGender.Types gender;
    private LocalDate birthDate;
    private String fullName;
    private IEnumEducationalLevel.Types educationalLevel;
}
