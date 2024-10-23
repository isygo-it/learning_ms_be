package eu.novobit.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * The type Linked file dto.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class LinkedFileDto extends AbstractAuditableDto<Long> implements IFileUploadDto {

    private String domain;
    private String path;
    private List<String> tags;
    private List<String> categoryNames;
    private MultipartFile file;
}
