package eu.novobit.converter;

import jakarta.persistence.AttributeConverter;
import org.apache.commons.text.CaseUtils;

/**
 * The type Camel case converter.
 */
public class CamelCaseConverter implements AttributeConverter<String, String> {

    @Override
    public String convertToDatabaseColumn(String var) {
        return CaseUtils.toCamelCase(var, true);
    }

    @Override
    public String convertToEntityAttribute(String var) {
        return var;
    }
}
