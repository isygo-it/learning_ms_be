package eu.novobit.dto.data;


import eu.novobit.dto.AbstractDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.SuperBuilder;

/**
 * The type Resume stat dto.
 */
@Data
@AllArgsConstructor
@SuperBuilder
public class ResumeStatDto extends AbstractDto<Long> {
    // Pourcentage completion
    private Integer completion;
    // nbre de test passe
    private Integer realizedTestsCount;
    // nbre de job application
    private Integer applicationsCount;
    // nbre d'application active
    private Integer ongoingApplicationsCount;
    // nbre d'event de type
    private Integer interviewCount;
}
