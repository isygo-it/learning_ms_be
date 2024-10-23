package eu.novobit.dto.data;

import eu.novobit.dto.AbstractAuditableDto;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;

/**
 * The type Post dto.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class PostDto extends AbstractAuditableDto<Long> {

    @NotEmpty
    private String domain;
    @NotEmpty
    private String accountCode;
    @NotEmpty
    private String title;
    private String talk;
    private List<CommentDto> comments;
    private List<String> usersAccountCode;
}
