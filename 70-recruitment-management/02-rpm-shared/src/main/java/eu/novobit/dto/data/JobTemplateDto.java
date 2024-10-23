package eu.novobit.dto.data;


import eu.novobit.dto.AbstractAuditableDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * The type Job template dto.
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class JobTemplateDto extends AbstractAuditableDto<Long> {

    private String domain;
    private String title;
    private JobOfferDto jobOffer;

}
