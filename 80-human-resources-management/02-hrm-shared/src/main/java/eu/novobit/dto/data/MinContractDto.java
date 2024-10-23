package eu.novobit.dto.data;

import eu.novobit.dto.AbstractAuditableDto;
import eu.novobit.dto.IFileUploadDto;
import eu.novobit.enumerations.IEnumContractType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.List;

/**
 * The type Min contract dto.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class MinContractDto extends AbstractAuditableDto<Long> implements IFileUploadDto {

    private String code;
    private String domain;
    private IEnumContractType.Types contract;
    private LocalDate startDate;
    private LocalDate endDate;
    private Long employee;
    private Boolean isLocked;
    private String originalFileName;
    private String extension;
    private MultipartFile file;
    private List<String> tags;
}
