package eu.novobit.dto.data;

import eu.novobit.dto.AbstractAuditableDto;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class CommentDto extends AbstractAuditableDto<Long> {

    @NotEmpty
    private String accountCode;
    @NotEmpty
    private String comment;
    @NotNull
    private Long postId;
    private List<String> usersAccountCode;

}
