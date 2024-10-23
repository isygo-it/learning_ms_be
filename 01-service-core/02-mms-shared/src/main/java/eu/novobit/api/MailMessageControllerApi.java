package eu.novobit.api;

import eu.novobit.constants.RestApiContants;
import eu.novobit.dto.AbstractDto;
import eu.novobit.dto.data.MailMessageDto;
import eu.novobit.enumerations.IEnumTemplateName;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;


/**
 * The interface Mail message controller api.
 */
public interface MailMessageControllerApi {

    /**
     * Send mail response entity.
     *
     * @param senderDomainName the sender domain name
     * @param template         the template
     * @param mailMessage      the mail message
     * @return the response entity
     */
    @Operation(summary = "sendMail Api",
            description = "sendMail")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Api executed successfully",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = AbstractDto.class))})
    })
    @PostMapping(path = "/send/{senderDomainName}/{template}", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<?> sendMail(@PathVariable(name = RestApiContants.senderDomainName) String senderDomainName,
                                      @PathVariable(name = RestApiContants.template) IEnumTemplateName.Types template,
                                      @ModelAttribute(name = RestApiContants.mailMessage) MailMessageDto mailMessage);
}
