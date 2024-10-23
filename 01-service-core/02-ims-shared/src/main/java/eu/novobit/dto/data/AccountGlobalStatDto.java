package eu.novobit.dto.data;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;

/**
 * The type Account global stat dto.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class AccountGlobalStatDto implements Serializable {

    private Integer totalCount;
    private Integer activeCount;
    private Integer confirmedCount; // user has really loggedIn
}
