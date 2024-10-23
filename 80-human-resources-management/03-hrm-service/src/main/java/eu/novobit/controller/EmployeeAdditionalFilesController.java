package eu.novobit.controller;

import eu.novobit.annotation.CtrlDef;
import eu.novobit.com.rest.controller.AbstractCrudBasicsController;
import eu.novobit.com.rest.controller.ResponseFactory;
import eu.novobit.constants.JwtContants;
import eu.novobit.constants.RestApiContants;
import eu.novobit.dto.AbstractDto;
import eu.novobit.dto.RequestContextDto;
import eu.novobit.dto.data.EmployeeDto;
import eu.novobit.dto.data.EmployeeLinkedFileDto;
import eu.novobit.exception.handler.HrmExceptionHandler;
import eu.novobit.mapper.EmployeeLinkedFileMapper;
import eu.novobit.mapper.EmployeeMapper;
import eu.novobit.model.Employee;
import eu.novobit.service.impl.EmployeeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


/**
 * The type Resume Additional file controller.
 */
@Slf4j
@Validated
@RestController
@RequestMapping(path = "/api/private/employee")
@CtrlDef(handler = HrmExceptionHandler.class, mapper = EmployeeMapper.class, service = EmployeeService.class)
public class EmployeeAdditionalFilesController extends AbstractCrudBasicsController<Employee, EmployeeDto, EmployeeDto, EmployeeService> {

    @Autowired
    private EmployeeLinkedFileMapper employeeLinkedFileMapper;

    /**
     * Upload additional file response entity.
     *
     * @param requestContext the request context
     * @param id             the id
     * @param files          the files
     * @return the response entity
     */
    @Operation(summary = "uploadAdditionalFiles Api",
            description = "uploadAdditionalFiles")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Api executed successfully",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = AbstractDto.class))})
    })
    @PutMapping(path = "/upload/multi-files/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<List<EmployeeLinkedFileDto>> uploadAdditionalFiles(@RequestAttribute(value = JwtContants.JWT_USER_CONTEXT, required = false) RequestContextDto requestContext,
                                                                             @PathVariable(name = RestApiContants.ID) Long id,
                                                                             @Valid @RequestBody MultipartFile[] files) {
        log.info("update additionl file");
        try {
            return ResponseFactory.ResponseOk(employeeLinkedFileMapper.listEntityToDto(crudService().uploadAdditionalFile(id, files)));
        } catch (Throwable e) {
            log.error("<Error>: api exception : {} ", e);
            return getBackExceptionResponse(e);
        }
    }

    /**
     * Delete additional response entity.
     *
     * @param requestContext the request context
     * @param id             the id
     * @param originalFile   the original file
     * @return the response entity
     */
    @Operation(summary = "deleteAdditionalFile Api",
            description = "deleteAdditionalFile")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Api executed successfully",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = AbstractDto.class))})
    })
    @DeleteMapping(path = "/multi-files/{id}/{originalFile}")
    public ResponseEntity<Boolean> deleteAdditionalFile(@RequestAttribute(value = JwtContants.JWT_USER_CONTEXT, required = false) RequestContextDto requestContext,
                                                        @PathVariable(name = RestApiContants.ID) Long id,
                                                        @PathVariable(name = RestApiContants.originalFile) String originalFile) {
        log.info("delete additional file");
        try {
            return ResponseFactory.ResponseOk(crudService().deleteAdditionalFile(id, originalFile));
        } catch (Throwable e) {
            log.error("<Error>: api exception : {} ", e);
            return getBackExceptionResponse(e);
        }
    }

}
