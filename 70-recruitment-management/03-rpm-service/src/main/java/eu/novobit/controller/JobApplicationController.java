package eu.novobit.controller;

import eu.novobit.annotation.CtrlDef;
import eu.novobit.com.rest.controller.MappedCrudController;
import eu.novobit.com.rest.controller.ResponseFactory;
import eu.novobit.constants.JwtContants;
import eu.novobit.dto.AbstractDto;
import eu.novobit.dto.RequestContextDto;
import eu.novobit.dto.data.JobApplicationDto;
import eu.novobit.dto.data.JobOfferDto;
import eu.novobit.exception.handler.RpmExceptionHandler;
import eu.novobit.mapper.JobApplicationMapper;
import eu.novobit.mapper.JobOfferMapper;
import eu.novobit.model.JobApplication;
import eu.novobit.service.IJobOfferService;
import eu.novobit.service.impl.JobApplicationService;
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
 * The type Job application controller.
 */
@Slf4j
@Validated
@RestController
@RequestMapping(path = "/api/private/jobApplication")
@CtrlDef(handler = RpmExceptionHandler.class, mapper = JobApplicationMapper.class, service = JobApplicationService.class)
public class JobApplicationController extends MappedCrudController<JobApplication, JobApplicationDto, JobApplicationDto, JobApplicationService> {

    @Autowired
    private IJobOfferService jobOfferService;

    @Autowired
    private JobOfferMapper jobOfferMapper;


    /**
     * Gets job offers not assigned to resume.
     *
     * @param requestContext the request context
     * @param resumeCode     the resume code
     * @return the job offers not assigned to resume
     */
    @Operation(summary = "getJobOffersNotAssignedToResume Api",
            description = "getJobOffersNotAssignedToResume")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Api executed successfully",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = AbstractDto.class))})
    })
    @GetMapping(path = "/not-applied/{resumeCode}")
    public ResponseEntity<List<JobOfferDto>> getJobOffersNotAssignedToResume(@RequestAttribute(value = JwtContants.JWT_USER_CONTEXT, required = false) RequestContextDto requestContext,
                                                                             @PathVariable String resumeCode) {
        try {
            List<JobOfferDto> jobOffers = jobOfferMapper.listEntityToDto(jobOfferService.findJobOffersNotAssignedToResume(resumeCode));
            if (jobOffers.isEmpty()) {
                return ResponseFactory.ResponseNoContent();
            }

            return ResponseFactory.ResponseOk(jobOffers);
        } catch (Throwable e) {
            log.error("<Error>: api exception : {} ", e);
            return getBackExceptionResponse(e);
        }
    }
}
