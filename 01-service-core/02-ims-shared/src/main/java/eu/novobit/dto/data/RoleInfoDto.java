package eu.novobit.dto.data;

import com.fasterxml.jackson.annotation.JsonIgnore;
import eu.novobit.dto.AbstractAuditableDto;
import eu.novobit.dto.ApiPermissionDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;

/**
 * The type Role info dto.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class RoleInfoDto extends AbstractAuditableDto<Long> {

    private String code;
    private String name;
    private String description;
    @Builder.Default
    private Integer numberOfUsers = 0;
    private List<ApplicationDto> allowedTools;
    @JsonIgnore
    private List<ApiPermissionDto> permissions;
    private List<RolePermissionDto> rolePermission;
}
