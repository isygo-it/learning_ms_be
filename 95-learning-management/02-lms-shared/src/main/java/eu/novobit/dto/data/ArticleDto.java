package eu.novobit.dto.data;

import eu.novobit.dto.AbstractAuditableDto;
import eu.novobit.dto.IFileUploadDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.List;

/**
 * The type Article dto.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class ArticleDto extends AbstractAuditableDto<Long> implements IFileUploadDto {

    private String code;
    private String title;
    private String name;
    private String type;
    private String domain;
    private String description;
    private String originalFileName;
    private String extension;
    private Date createDate;
    private Date updateDate;
    private List<String> tags;
    private MultipartFile file;

    private Long authorID;
    private AuthorDto author;

    private Long[] topicIds;
    private List<TopicDto> topics;
}
