package eu.novobit.api;

import eu.novobit.dto.AbstractDto;
import eu.novobit.dto.UserContextDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * The interface Public password controller api.
 */
public interface PublicPasswordControllerApi {

    /**
     * Generate forgot password access token response entity.
     *
     * @param userContextDto the user context dto
     * @return the response entity
     */
    @Operation(summary = "generateForgotPasswordAccessToken Api",
            description = "generateForgotPasswordAccessToken")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Api executed successfully",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = AbstractDto.class))})
    })
    @PostMapping(path = "/access-token")
    public ResponseEntity<Boolean> generateForgotPasswordAccessToken(@Valid @RequestBody UserContextDto userContextDto);

}
