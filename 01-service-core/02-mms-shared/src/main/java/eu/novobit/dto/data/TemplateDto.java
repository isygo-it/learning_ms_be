package eu.novobit.dto.data;

import eu.novobit.dto.AbstractAuditableDto;
import eu.novobit.dto.IFileUploadDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.web.multipart.MultipartFile;

/**
 * The type Template dto.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder

public class TemplateDto extends AbstractAuditableDto<Long> implements IFileUploadDto {

    private String domain;
    private String name;
    private String code;
    private String description;
    private String path;
    private MultipartFile file;
}
