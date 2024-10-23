package eu.novobit.dto.data;

import eu.novobit.dto.AbstractAuditableDto;
import eu.novobit.enumerations.IEnumCivility;
import eu.novobit.enumerations.IEnumGender;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.lang.Nullable;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;


/**
 * The type Employee details dto.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class EmployeeDetailsDto extends AbstractAuditableDto<Long> {

    private String placeofBirth;
    private LocalDate birthDate;
    private List<CinDto> cin;
    private String location;
    private String reportTo;
    private List<PassportDto> passport;
    private List<InsuranceSecurityDto> securities;
    private String securite;
    private String homeAddress;
    private IEnumGender.Types gender;
    private IEnumCivility.Types civility;
    @Nullable
    private FamilyInformationDto familyInformation;
    @Nullable
    private Set<EmergencyContactDto> emergencyContact;
    @Nullable
    private Set<EmployeeLanguagesDto> languages;

}
