package eu.novobit.controller;

import eu.novobit.annotation.CtrlHandler;
import eu.novobit.com.rest.controller.AbstractController;
import eu.novobit.com.rest.controller.ResponseFactory;
import eu.novobit.constants.JwtContants;
import eu.novobit.dto.AbstractDto;
import eu.novobit.dto.RequestContextDto;
import eu.novobit.dto.data.TimelineDto;
import eu.novobit.exception.handler.RpmExceptionHandler;
import eu.novobit.mapper.TimelineMapper;
import eu.novobit.service.impl.TimelineService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * The type Timeline controller.
 */
@Slf4j
@Validated
@RestController
@CtrlHandler(RpmExceptionHandler.class)
@RequestMapping(path = "/api/private/timeline")
public class TimelineController extends AbstractController {
    /**
     * The Timeline service.
     */
    @Autowired
    TimelineService timelineService;
    /**
     * The Timeline mapper.
     */
    @Autowired
    TimelineMapper timelineMapper;

    /**
     * Find timeline response entity.
     *
     * @param requestContext the request context
     * @param code           the code
     * @param domain         the domain
     * @return the response entity
     */
    @Operation(summary = "findTimeline Api",
            description = "findTimeline")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Api executed successfully",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = AbstractDto.class))})
    })
    @GetMapping(path = "/{code}/{domain}")
    public ResponseEntity<List<TimelineDto>> findTimeline(@RequestAttribute(value = JwtContants.JWT_USER_CONTEXT, required = false) RequestContextDto requestContext,
                                                          @PathVariable String code,
                                                          @PathVariable String domain) {
        try {
            List<TimelineDto> timeline = timelineMapper.listEntityToDto(timelineService.getTimelineByDomainAndCode(code, domain));
            if (timeline.isEmpty()) {
                return ResponseFactory.ResponseNoContent();
            }
            return ResponseFactory.ResponseOk(timeline);
        } catch (Exception ex) {
            return getBackExceptionResponse(ex);
        }
    }
}
