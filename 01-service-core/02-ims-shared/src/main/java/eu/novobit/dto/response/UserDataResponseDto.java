package eu.novobit.dto.response;

import eu.novobit.dto.data.ApplicationDto;
import eu.novobit.enumerations.IEnumLanguage;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;

/**
 * The type User data response dto.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class UserDataResponseDto {

    private Long id;
    private String role;
    private String email;
    private String firstName;
    private String lastName;
    private String userName;
    private List<ApplicationDto> applications;
    private String domainImagePath;
    private IEnumLanguage.Types language;
    private long domainId;
}
