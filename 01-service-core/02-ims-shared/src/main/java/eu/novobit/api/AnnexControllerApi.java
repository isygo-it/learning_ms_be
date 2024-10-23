package eu.novobit.api;

import eu.novobit.com.rest.api.IMappedCrudApi;
import eu.novobit.constants.JwtContants;
import eu.novobit.constants.RestApiContants;
import eu.novobit.dto.AbstractDto;
import eu.novobit.dto.RequestContextDto;
import eu.novobit.dto.data.AnnexDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestAttribute;

import java.util.List;

/**
 * The interface Annex controller api.
 */
public interface AnnexControllerApi extends IMappedCrudApi<AnnexDto, AnnexDto> {

    /**
     * Gets annex by code.
     *
     * @param requestContext the request context
     * @param code           the code
     * @return the annex by code
     */
    @Operation(summary = "getAnnexByCode Api",
            description = "getAnnexByCode")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Api executed successfully",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = AbstractDto.class))})
    })
    @GetMapping(path = "/getByCode/{code}")
    ResponseEntity<List<AnnexDto>> getAnnexByCode(@RequestAttribute(value = JwtContants.JWT_USER_CONTEXT, required = false) RequestContextDto requestContext,
                                                  @PathVariable(name = RestApiContants.code) String code);

    @GetMapping(path = "/getByCodeAndRef/{code}/{reference}")
    ResponseEntity<List<AnnexDto>> getAnnexByCodeAndReference(@RequestAttribute(value = JwtContants.JWT_USER_CONTEXT, required = false) RequestContextDto requestContext,
                                                              @PathVariable(name = RestApiContants.code) String code, @PathVariable(name = RestApiContants.REFERENCE) String reference);
}
