package eu.novobit.dto.data;

import eu.novobit.dto.AbstractAuditableDto;
import eu.novobit.dto.IImageUploadDto;
import eu.novobit.dto.TokenDto;
import eu.novobit.enumerations.IEnumBinaryStatus;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * The type Application dto.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class ApplicationDto extends AbstractAuditableDto<Long> implements IImageUploadDto {

    private String domain;
    private String code;

    @NotEmpty
    private String name;
    @NotEmpty
    private String title;
    private String description;
    @NotEmpty
    private String url;
    private Integer order;
    private String imagePath;

    @Builder.Default
    private IEnumBinaryStatus.Types adminStatus = IEnumBinaryStatus.Types.ENABLED;

    //App authorization token
    private TokenDto token;
}
