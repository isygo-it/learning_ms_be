package eu.novobit.api;

import eu.novobit.com.rest.api.IMappedCrudApi;
import eu.novobit.constants.JwtContants;
import eu.novobit.constants.RestApiContants;
import eu.novobit.dto.AbstractDto;
import eu.novobit.dto.RequestContextDto;
import eu.novobit.dto.data.ContractDto;
import eu.novobit.dto.data.MinContractDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * The interface Contract controller api.
 */
public interface ContractControllerApi extends IMappedCrudApi<MinContractDto, ContractDto> {
    /**
     * Update contract status response entity.
     *
     * @param requestContext the request context
     * @param id             the id
     * @param isLocked       the isLocked
     * @return the response entity
     */
    @Operation(summary = "updateContractStatus Api",
            description = "updateContractStatus")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Api executed successfully",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = AbstractDto.class))})
    })
    @PutMapping(path = "/updateContractStatus")
    ResponseEntity<ContractDto> updateContractStatus(@RequestAttribute(value = JwtContants.JWT_USER_CONTEXT, required = false) RequestContextDto requestContext,
                                                     @RequestParam(name = RestApiContants.ID) Long id,
                                                     @RequestParam(name = RestApiContants.isLocked) Boolean isLocked);

}
