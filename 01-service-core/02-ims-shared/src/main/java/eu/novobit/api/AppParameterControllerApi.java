package eu.novobit.api;

import eu.novobit.com.rest.api.IMappedCrudApi;
import eu.novobit.constants.JwtContants;
import eu.novobit.constants.RestApiContants;
import eu.novobit.dto.AbstractDto;
import eu.novobit.dto.RequestContextDto;
import eu.novobit.dto.data.AppParameterDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * The interface App parameter controller api.
 */
public interface AppParameterControllerApi extends IMappedCrudApi<AppParameterDto, AppParameterDto> {

    /**
     * Gets value by domain and name.
     *
     * @param requestContext the request context
     * @param domain         the domain
     * @param name           the name
     * @param allowDefault   the allow default
     * @return the value by domain and name
     */
    @Operation(summary = "getValueByDomainAndName Api",
            description = "getValueByDomainAndName")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Api executed successfully",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = AbstractDto.class))})
    })
    @GetMapping(path = "/value/byDomainAndName")
    ResponseEntity<String> getValueByDomainAndName(@RequestAttribute(value = JwtContants.JWT_USER_CONTEXT, required = false) RequestContextDto requestContext,
                                                   @RequestParam(name = RestApiContants.DOMAIN_NAME) String domain,
                                                   @RequestParam(name = RestApiContants.name) String name,
                                                   @RequestParam(name = RestApiContants.ALLOW_DEFAULT) Boolean allowDefault,
                                                   @RequestParam(name = RestApiContants.DEFAULT_VALUE) String defaultValue);
}
