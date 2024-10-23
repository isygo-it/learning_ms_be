package eu.novobit.dto.data;


import eu.novobit.dto.AbstractDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.SuperBuilder;

/**
 * The type Job stat dto.
 */
@Data
@AllArgsConstructor
@SuperBuilder
public class JobOfferStatDto extends AbstractDto<Long> {

    private Integer completion;
    private Integer applicationCount;
    private Integer selectedProfilesCount; // ????
    private Integer interviewedProfilesCount;
    private Integer rejectedProfilesCount;// ????
}
