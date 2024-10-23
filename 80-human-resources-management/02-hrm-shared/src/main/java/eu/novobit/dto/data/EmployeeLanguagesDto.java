package eu.novobit.dto.data;

import eu.novobit.enumerations.IEnumLanguageLevelType;
import lombok.Data;
import org.springframework.lang.Nullable;

/**
 * The type Employee languages dto.
 */
@Data

public class EmployeeLanguagesDto {

    private String languageName;
    @Nullable
    private IEnumLanguageLevelType.Types level;
}


