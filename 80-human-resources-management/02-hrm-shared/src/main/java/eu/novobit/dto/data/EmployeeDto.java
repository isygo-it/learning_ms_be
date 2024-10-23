package eu.novobit.dto.data;

import eu.novobit.dto.IFileUploadDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * The type Employee dto.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class EmployeeDto extends MinEmployeeDto implements IFileUploadDto {

    private String phone;
    private AddressDto address;
    private EmployeeDetailsDto details;
    private List<ContractDto> contracts;
    private Boolean isLinkedToUser;
    private MultipartFile file;
    private List<EmployeeLinkedFileDto> additionalFiles;
}
