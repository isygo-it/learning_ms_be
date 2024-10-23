package eu.novobit.dto.data;


import eu.novobit.dto.AddressDto;
import eu.novobit.dto.DomainModelDto;
import eu.novobit.dto.IImageUploadDto;
import eu.novobit.model.ISASEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * The type Domain dto.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class DomainDto extends DomainModelDto implements ISASEntity, IImageUploadDto {

    private String domain;
    private String code;
    private String email;
    private String phone;
    private String lnk_facebook;
    private String lnk_linkedin;
    private String lnk_xing;
    private AddressDto address;
    private String imagePath;
}
