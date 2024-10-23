package eu.novobit.controller;

import eu.novobit.annotation.CtrlDef;
import eu.novobit.com.rest.controller.MappedCrudController;
import eu.novobit.com.rest.controller.ResponseFactory;
import eu.novobit.constants.JwtContants;
import eu.novobit.constants.RestApiContants;
import eu.novobit.dto.AbstractDto;
import eu.novobit.dto.RequestContextDto;
import eu.novobit.dto.data.ResumeDto;
import eu.novobit.dto.data.ResumeShareInfoDto;
import eu.novobit.dto.request.ShareResumeRequestDto;
import eu.novobit.exception.handler.RpmExceptionHandler;
import eu.novobit.helper.UrlHelper;
import eu.novobit.jwt.JwtService;
import eu.novobit.mapper.ResumeMapper;
import eu.novobit.mapper.ResumeShareInfoMapper;
import eu.novobit.model.Resume;
import eu.novobit.model.ResumeShareInfo;
import eu.novobit.repository.ResumeSharedInfoRepository;
import eu.novobit.service.impl.ResumeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * The type Resume controller.
 */
@Slf4j
@Validated
@RestController
@RequestMapping(path = "/api/private/resume")
@CtrlDef(handler = RpmExceptionHandler.class, mapper = ResumeMapper.class, service = ResumeService.class)
public class ResumeController extends MappedCrudController<Resume, ResumeDto, ResumeDto, ResumeService> {

    @Autowired
    private ResumeSharedInfoRepository resumeSharedInfoRepository;
    @Autowired
    private ResumeShareInfoMapper resumeShareInfoMapper;
    @Autowired
    private JwtService jwtService;

    /**
     * Share resume response entity.
     *
     * @param requestContext        the request context
     * @param id                    the id
     * @param shareResumeRequestDto the share resume request dto
     * @return the response entity
     */
    @Operation(summary = "shareResume Api",
            description = "shareResume")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Api executed successfully",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = AbstractDto.class))})
    })
    @PostMapping(path = "/share/{id}")
    public ResponseEntity<List<ResumeShareInfoDto>> shareResume(@RequestAttribute(value = JwtContants.JWT_USER_CONTEXT, required = false) RequestContextDto requestContext,
                                                                @PathVariable(name = RestApiContants.ID) Long id,
                                                                @Valid @RequestBody ShareResumeRequestDto shareResumeRequestDto) {
        log.info("share resume ");
        try {
            return ResponseFactory.ResponseOk(resumeShareInfoMapper.listEntityToDto(crudService().shareWithAccounts(
                    id, shareResumeRequestDto.getResumeOwner(), shareResumeRequestDto.getAccountsCode())));
        } catch (Throwable e) {
            log.error("<Error>: api exception : {} ", e);
            return getBackExceptionResponse(e);
        }
    }

    /**
     * Update resume review response entity.
     *
     * @param requestContext  the request context
     * @param id              the id
     * @param resumeShareInfo the resume share info
     * @return the response entity
     */
    @Operation(summary = "updateResumeReview Api",
            description = "updateResumeReview")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Api executed successfully",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = AbstractDto.class))})
    })
    @PutMapping(path = "/resume-review/update/{id}")
    public ResponseEntity<ResumeShareInfo> updateResumeReview(@RequestAttribute(value = JwtContants.JWT_USER_CONTEXT, required = false) RequestContextDto requestContext,
                                                              @PathVariable(name = RestApiContants.ID) Long id,
                                                              @Valid @RequestBody ResumeShareInfo resumeShareInfo) {
        log.info("update resume review");
        try {
            ResumeShareInfo resumeDto = resumeSharedInfoRepository.save(resumeShareInfo);
            return ResponseFactory.ResponseOk(resumeDto);
        } catch (Throwable e) {
            log.error("<Error>: api exception : {} ", e);
            return getBackExceptionResponse(e);
        }
    }


    /**
     * Find current user resume response entity.
     *
     * @param requestContext the request context
     * @param request        the request
     * @return the response entity
     */
    @Operation(summary = "findCurrentUserResume Api",
            description = "findCurrentUserResume")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Api executed successfully",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = AbstractDto.class))})
    })
    @GetMapping(path = "/findByCandidateCode")
    public ResponseEntity<ResumeDto> getResumeByAccountCode(@RequestAttribute(value = JwtContants.JWT_USER_CONTEXT, required = false) RequestContextDto requestContext,
                                                            HttpServletRequest request) {
        try {
            String jwt = UrlHelper.getJwtFromRequest(request);
            String userName = jwtService.extractSubject(jwt);
            String[] userNameArray = userName.split("@");
            ResumeDto resumeDto = mapper().entityToDto(crudService().getResumeByAccountCode(userNameArray[0]));
            if (resumeDto != null) {
                return ResponseFactory.ResponseOk(resumeDto);

            }
            return ResponseEntity.noContent().build();
        } catch (Throwable e) {
            log.error("<Error>: api exception : {} ", e);
            return getBackExceptionResponse(e);
        }
    }


    /**
     * Create current user resume response entity.
     *
     * @param requestContext the request context
     * @param resumeDto      the resume dto
     * @return the response entity
     */
    @Operation(summary = "createCurrentUserResume Api",
            description = "createCurrentUserResume")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Api executed successfully",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = AbstractDto.class))})
    })
    @PostMapping(path = "/createResumeCandidate")
    public ResponseEntity<ResumeDto> createCurrentUserResume(@RequestAttribute(value = JwtContants.JWT_USER_CONTEXT, required = false) RequestContextDto requestContext,
                                                             @Valid @RequestBody ResumeDto resumeDto) {
        try {
            if (!StringUtils.hasText(resumeDto.getDomain())) {
                resumeDto.setDomain(requestContext.getSenderDomain());
            }
            return ResponseFactory.ResponseOk(mapper().entityToDto(crudService().createResumeForAccount(requestContext.getSenderDomain(),
                    mapper().dtoToEntity(resumeDto))));
        } catch (Exception ex) {
            return getBackExceptionResponse(ex);
        }
    }
}
