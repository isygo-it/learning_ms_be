package eu.novobit.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;

/**
 * The type Address model dto.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class AddressModelDto extends AbstractAuditableDto<Long> {

    private String country;
    private String state;
    private String city;
    private String street;
    private String zipCode;
    private String additionalInfo;
    private Double latitude;
    private Double longitude;
    private List<String> compAddress;

    /**
     * Format string.
     *
     * @return the string
     */
    public String format() {
        return new StringBuilder(additionalInfo).append("-").append(street).append("\n")
                .append(city).append("-").append(state).append("_n")
                .append(zipCode).append("-").append(country)
                .toString();
    }
}
