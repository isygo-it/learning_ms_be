package eu.novobit.dto.data;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;

/**
 * The type Job global stat dto.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class JobOfferGlobalStatDto implements Serializable {

    private Integer totalCount;
    private Integer activeCount; // deadline not reached
    private Integer confirmedCount; // nbre de position atteint
    private Integer expiredCount;
}
