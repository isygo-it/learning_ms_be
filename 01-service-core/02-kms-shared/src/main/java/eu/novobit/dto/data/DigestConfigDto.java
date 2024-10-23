package eu.novobit.dto.data;

import eu.novobit.dto.AbstractAuditableDto;
import eu.novobit.enumerations.IEnumAlgoDigestConfig;
import eu.novobit.enumerations.IEnumSaltGenerator;
import eu.novobit.enumerations.IEnumStringOutputType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * The type Digest config dto.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class DigestConfigDto extends AbstractAuditableDto<Long> {

    private String domain;
    private String code;
    //DIGEST ALGORITHMS:   [MD2, MD5, SHA, SHA-256, SHA-384, SHA-512]
    private IEnumAlgoDigestConfig.Types algorithm;
    private Integer iterations;
    private Integer saltSizeBytes;
    private IEnumSaltGenerator.Types saltGenerator;
    private String providerClassName;
    private String providerName;
    private Boolean invertPositionOfSaltInMessageBeforeDigesting;
    private Boolean invertPositionOfPlainSaltInEncryptionResults;
    private Boolean useLenientSaltSizeCheck;
    private Integer poolSize;
    private Boolean unicodeNormalizationIgnored;
    private IEnumStringOutputType.Types stringOutputType;
    private String prefix;
    private String suffix;
}
