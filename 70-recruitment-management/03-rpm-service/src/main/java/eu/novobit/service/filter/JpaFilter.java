package eu.novobit.service.filter;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * The type Jpa filter.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class JpaFilter {

    private String name;
    private String operator;
    private String value;
}
