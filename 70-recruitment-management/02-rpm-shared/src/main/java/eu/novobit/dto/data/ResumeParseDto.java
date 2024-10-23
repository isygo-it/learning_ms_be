package eu.novobit.dto.data;

import eu.novobit.dto.AbstractAuditableDto;
import eu.novobit.dto.IFileUploadDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.web.multipart.MultipartFile;

/**
 * The type Resume parse dto.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class ResumeParseDto extends AbstractAuditableDto<Long> implements IFileUploadDto {

    private String domain;
    private String code;
    private MultipartFile file;
}
