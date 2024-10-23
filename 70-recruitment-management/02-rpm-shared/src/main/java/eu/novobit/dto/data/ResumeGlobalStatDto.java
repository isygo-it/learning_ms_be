package eu.novobit.dto.data;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;

/**
 * The type Resume global stat dto.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class ResumeGlobalStatDto implements Serializable {

    private Integer totalCount;
    // nbre ceated by
    private Integer uploadedByMeCount;
    private Integer confirmedCount; // user has really loggedIn
    // porcentage Object cpmletetion
    private Integer completedCount;
    // nbre d'interview global
    private Integer interviewedCount;
}
