package eu.novobit.dto.data;

import eu.novobit.dto.AbstractAuditableDto;
import eu.novobit.dto.IImageUploadDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;


/**
 * The type Author dto.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class AuthorDto extends AbstractAuditableDto<Long> implements
        IImageUploadDto{

    private Long id;
    private String code;
    private String domain;
    private String firstname;
    private String lastname;
    private String email;
    private String phone;
    private String imagePath;


}
