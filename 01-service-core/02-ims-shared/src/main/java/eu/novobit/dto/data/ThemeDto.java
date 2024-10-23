package eu.novobit.dto.data;


import eu.novobit.dto.AbstractAuditableDto;
import eu.novobit.enumerations.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * The type Theme dto.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class ThemeDto extends AbstractAuditableDto<Long> {

    private String domainCode;
    private String accountCode;
    private String themeColor;
    private IEnumSkin.Types skin;
    private IEnumMode.Types mode;
    private IEnumContentWidth.Types contentWidth;
    private IEnumAppBarFooterType.Types appBar;
    private IEnumAppBarFooterType.Types footer;
    private IEnumMenuLayout.Types layout;
    private IEnumNavToggle.Types verticalNavToggleType;
    private IEnumDirection.Types direction;
    private Boolean appBarBlur;
    private Boolean navCollapsed;

}
