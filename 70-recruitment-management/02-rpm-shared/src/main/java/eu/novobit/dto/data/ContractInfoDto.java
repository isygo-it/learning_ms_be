package eu.novobit.dto.data;

import eu.novobit.dto.AbstractAuditableDto;
import eu.novobit.enumerations.IEnumContractType;
import eu.novobit.enumerations.IEnumTimeType;
import eu.novobit.enumerations.IEnumWorkMode;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * The type Contract info dto.
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class ContractInfoDto extends AbstractAuditableDto<Long> {

    private String location;
    private Integer salaryMin;
    private Integer salaryMax;
    private IEnumWorkMode.Types workingMode;
    private IEnumContractType.Types contract;
    private IEnumTimeType.Types availability;
    private String currency;

}


