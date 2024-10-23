package eu.novobit.dto.data;

import eu.novobit.dto.AbstractAuditableDto;
import eu.novobit.enumerations.IEnumAlgoPEBConfig;
import eu.novobit.enumerations.IEnumIvGenerator;
import eu.novobit.enumerations.IEnumSaltGenerator;
import eu.novobit.enumerations.IEnumStringOutputType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * The type Peb config dto.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class PEBConfigDto extends AbstractAuditableDto<Long> {

    private String domain;
    private String code;
    //PBE ALGORITHMS:   [PBEWITHMD5ANDDES, PBEWITHMD5ANDTRIPLEDES, PBEWITHSHA1ANDDESEDE, PBEWITHSHA1ANDRC2_40]
    private IEnumAlgoPEBConfig.Types algorithm;
    //private String password;
    private Integer keyObtentionIterations;
    private IEnumSaltGenerator.Types saltGenerator;
    private IEnumIvGenerator.Types ivGenerator;
    private String providerClassName;
    private String providerName;
    private Integer poolSize;
    private IEnumStringOutputType.Types stringOutputType;
}
