package eu.novobit.controller;

import eu.novobit.annotation.CtrlDef;
import eu.novobit.com.rest.controller.MappedCrudController;
import eu.novobit.com.rest.controller.ResponseFactory;
import eu.novobit.constants.JwtContants;
import eu.novobit.constants.RestApiContants;
import eu.novobit.dto.AbstractDto;
import eu.novobit.dto.RequestContextDto;
import eu.novobit.dto.data.JobOfferDto;
import eu.novobit.dto.data.JobShareInfoDto;
import eu.novobit.dto.request.ShareJobRequestDto;
import eu.novobit.exception.handler.RpmExceptionHandler;
import eu.novobit.mapper.JobOfferMapper;
import eu.novobit.mapper.JobShareInfoMapper;
import eu.novobit.model.JobOffer;
import eu.novobit.service.impl.JobOfferService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * The type Job controller.
 */
@Slf4j
@Validated
@RestController
@RequestMapping(path = "/api/private/Job")
@CtrlDef(handler = RpmExceptionHandler.class, mapper = JobOfferMapper.class, service = JobOfferService.class)
public class JobOfferController extends MappedCrudController<JobOffer, JobOfferDto, JobOfferDto, JobOfferService> {

    @Autowired
    private JobShareInfoMapper jobShareInfoMapper;

    /**
     * Share response entity.
     *
     * @param requestContext     the request context
     * @param id                 the id
     * @param shareJobRequestDto the share job request dto
     * @return the response entity
     */
    @Operation(summary = "share Api",
            description = "share")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Api executed successfully",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = AbstractDto.class))})
    })
    @PostMapping(path = "/share/{id}")
    public ResponseEntity<List<JobShareInfoDto>> share(@RequestAttribute(value = JwtContants.JWT_USER_CONTEXT, required = false) RequestContextDto requestContext,
                                                       @PathVariable(name = RestApiContants.ID) Long id,
                                                       @Valid @RequestBody ShareJobRequestDto shareJobRequestDto) {
        log.info("share job ");
        try {
            return ResponseFactory.ResponseOk(jobShareInfoMapper.listEntityToDto(crudService().shareJob(id, shareJobRequestDto.getJobOwner(), shareJobRequestDto.getAccountsCode())));
        } catch (Throwable e) {
            log.error("<Error>: api exception : {} ", e);
            return getBackExceptionResponse(e);
        }
    }
}
