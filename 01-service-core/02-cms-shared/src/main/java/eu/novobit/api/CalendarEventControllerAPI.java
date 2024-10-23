package eu.novobit.api;

import eu.novobit.constants.JwtContants;
import eu.novobit.constants.RestApiContants;
import eu.novobit.dto.AbstractDto;
import eu.novobit.dto.RequestContextDto;
import eu.novobit.dto.data.VCalendarEventDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * The interface Calendar event controller api.
 */
public interface CalendarEventControllerAPI {

    /**
     * Event by domain and calendar and code response entity.
     *
     * @param requestContext the request context
     * @param domain         the domain
     * @param calendar       the calendar
     * @param code           the code
     * @return the response entity
     */
    @Operation(summary = "eventByDomainAndCalendarAndCode Api",
            description = "eventByDomainAndCalendarAndCode")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Api executed successfully",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = AbstractDto.class))})
    })
    @GetMapping(path = "/byDomainAndCalendarAndCode/{domain}/{calendar}/{code}")
    ResponseEntity<VCalendarEventDto> eventByDomainAndCalendarAndCode(@RequestAttribute(value = JwtContants.JWT_USER_CONTEXT, required = false) RequestContextDto requestContext,
                                                                      @PathVariable(name = RestApiContants.DOMAIN_NAME) String domain,
                                                                      @PathVariable(name = RestApiContants.calendar) String calendar,
                                                                      @PathVariable(name = RestApiContants.code) String code);

    /**
     * Gets all by domain and calendar name.
     *
     * @param requestContext the request context
     * @param domain         the domain
     * @param calendar       the calendar
     * @return the all by domain and calendar name
     */
    @Operation(summary = "getAllByDomainAndCalendarName Api",
            description = "getAllByDomainAndCalendarName")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Api executed successfully",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = AbstractDto.class))})
    })
    @GetMapping(path = "/byDomainAndCalendarName/{domain}/{calendar}")
    ResponseEntity<List<VCalendarEventDto>> getAllByDomainAndCalendarName(@RequestAttribute(value = JwtContants.JWT_USER_CONTEXT, required = false) RequestContextDto requestContext,
                                                                          @PathVariable(name = RestApiContants.DOMAIN_NAME) String domain,
                                                                          @PathVariable(name = RestApiContants.calendar) String calendar);

    /**
     * Save event response entity.
     *
     * @param event the event
     * @return the response entity
     */
    @Operation(summary = "saveEvent Api",
            description = "saveEvent")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Api executed successfully",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = AbstractDto.class))})
    })
    @PostMapping()
    ResponseEntity<VCalendarEventDto> saveEvent(//@RequestAttribute(value = JwtContants.JWT_USER_CONTEXT, required = false) RequestContextDto requestContext,
                                                @Valid @RequestBody VCalendarEventDto event);

    /**
     * Update event response entity.
     *
     * @param id    the id
     * @param event the event
     * @return the response entity
     */
    @Operation(summary = "updateEvent Api",
            description = "updateEvent")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Api executed successfully",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = AbstractDto.class))})
    })
    @PutMapping(path = "/{id}")
    ResponseEntity<VCalendarEventDto> updateEvent(//@RequestAttribute(value = JwtContants.JWT_USER_CONTEXT, required = false) RequestContextDto requestContext,
                                                  @PathVariable(name = RestApiContants.ID) Long id,
                                                  @Valid @RequestBody VCalendarEventDto event);
}
