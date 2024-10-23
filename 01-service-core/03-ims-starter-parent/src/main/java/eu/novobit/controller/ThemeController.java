package eu.novobit.controller;

import eu.novobit.annotation.CtrlDef;
import eu.novobit.com.rest.controller.MappedCrudController;
import eu.novobit.constants.JwtContants;
import eu.novobit.dto.AbstractDto;
import eu.novobit.dto.RequestContextDto;
import eu.novobit.dto.data.ThemeDto;
import eu.novobit.exception.handler.ImsExceptionHandler;
import eu.novobit.mapper.ThemeMapper;
import eu.novobit.model.Theme;
import eu.novobit.service.IThemeService;
import eu.novobit.service.impl.ThemeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * The type Theme controller.
 */
@Slf4j
@Validated
@RestController
@RequestMapping(path = "/api/private/theme")
@CtrlDef(handler = ImsExceptionHandler.class, mapper = ThemeMapper.class, service = ThemeService.class)
public class ThemeController extends MappedCrudController<Theme, ThemeDto, ThemeDto, ThemeService> {

    @Autowired
    private IThemeService themeService;
    @Autowired
    private ThemeMapper themeMapper;

    /**
     * Find theme by account code and domain code response entity.
     *
     * @param requestContext the request context
     * @param domainCode     the domain code
     * @param accountCode    the account code
     * @return the response entity
     */
    @Operation(summary = "findThemeByAccountCodeAndDomainCode Api",
            description = "findThemeByAccountCodeAndDomainCode")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Api executed successfully",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = AbstractDto.class))})
    })
    @GetMapping(path = "/find/{domainCode}/{accountCode}")
    public ResponseEntity<ThemeDto> findThemeByAccountCodeAndDomainCode(@RequestAttribute(value = JwtContants.JWT_USER_CONTEXT, required = false) RequestContextDto requestContext,
                                                                        @PathVariable String domainCode,
                                                                        @PathVariable String accountCode) {
        try {
            Theme theme = themeService.findThemeByAccountCodeAndDomainCode(accountCode, domainCode);

            if (theme != null) {
                ThemeDto themeDto = themeMapper.entityToDto(theme);
                return new ResponseEntity<>(themeDto, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
        } catch (Throwable e) {
            log.error("<Error>: api exception : {} ", e);
            return getBackExceptionResponse(e);
        }
    }

    /**
     * Update theme response entity.
     *
     * @param requestContext the request context
     * @param theme          the theme
     * @return the response entity
     */
    @Operation(summary = "updateTheme Api",
            description = "updateTheme")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Api executed successfully",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = AbstractDto.class))})
    })
    @PutMapping
    public ResponseEntity<ThemeDto> updateTheme(@RequestAttribute(value = JwtContants.JWT_USER_CONTEXT, required = false) RequestContextDto requestContext,
                                                @Valid @RequestBody ThemeDto theme) {
        try {
            Theme themeResult = themeService.updateTheme(themeMapper.dtoToEntity(theme));
            return new ResponseEntity<>(themeMapper.entityToDto(themeResult), HttpStatus.OK);
        } catch (Throwable e) {
            log.error("<Error>: api exception : {} ", e);
            return getBackExceptionResponse(e);
        }
    }
}

