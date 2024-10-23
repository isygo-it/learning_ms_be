package eu.novobit.dto;

import eu.novobit.enumerations.IEnumBinaryStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * The type Customer model dto.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class CustomerModelDto extends AbstractAuditableDto<Long> implements IImageUploadDto {

    private String name;
    private String description;
    private String url;
    private String email;
    private String phoneNumber;
    private String imagePath;
    private String domain;
    @Builder.Default
    private IEnumBinaryStatus.Types adminStatus = IEnumBinaryStatus.Types.ENABLED;
}
