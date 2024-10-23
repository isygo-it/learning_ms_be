package eu.novobit.dto;


import com.fasterxml.jackson.annotation.JsonIgnore;
import eu.novobit.helper.BeanHelper;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.Collection;

/**
 * The type Abstract dto.
 *
 * @param <T> the type parameter
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public abstract class AbstractDto<T extends Serializable> implements IDto<T> {

    private T id;

    @JsonIgnore
    private String sectionName;

    public String getSectionName() {
        if (!StringUtils.hasText(this.sectionName)) {
            return this.getClass().getSimpleName().replace("Dto", "");
        } else {
            return this.sectionName;
        }
    }

    @JsonIgnore
    @Override
    public boolean isEmpty() {
        for (Field field : this.getClass().getDeclaredFields()) {
            Object fieldValue = BeanHelper.callGetter(this, field.getName());
            if (fieldValue != null) {
                if (IDto.class.isAssignableFrom(field.getType())) {
                    if (!((IDto) fieldValue).isEmpty()) {
                        return false;
                    }
                } else if (Collection.class.isAssignableFrom(field.getType())) {
                    if (!CollectionUtils.isEmpty((Collection) fieldValue)) {
                        return false;
                    }
                } else {
                    return false;
                }
            }
        }
        return true;
    }
}
