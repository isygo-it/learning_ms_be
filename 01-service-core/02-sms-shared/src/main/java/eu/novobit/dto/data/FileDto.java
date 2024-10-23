package eu.novobit.dto.data;

import eu.novobit.dto.IFileUploadDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.web.multipart.MultipartFile;

/**
 * The type File dto.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class FileDto implements IFileUploadDto {

    /**
     * The Domain.
     */
    String domain;
    /**
     * The Bucket name.
     */
    String bucketName;
    /**
     * The Tags.
     */
    String tags;
    /**
     * The File.
     */
    MultipartFile file;
}
