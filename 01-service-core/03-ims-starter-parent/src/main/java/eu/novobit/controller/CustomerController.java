package eu.novobit.controller;

import eu.novobit.annotation.CtrlDef;
import eu.novobit.com.rest.controller.MappedCrudController;
import eu.novobit.com.rest.controller.ResponseFactory;
import eu.novobit.constants.JwtContants;
import eu.novobit.constants.RestApiContants;
import eu.novobit.dto.AbstractDto;
import eu.novobit.dto.RequestContextDto;
import eu.novobit.dto.data.CustomerDto;
import eu.novobit.enumerations.IEnumBinaryStatus;
import eu.novobit.exception.handler.ImsExceptionHandler;
import eu.novobit.mapper.CustomerMapper;
import eu.novobit.model.Customer;
import eu.novobit.service.ICustomerService;
import eu.novobit.service.impl.CustomerService;
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
 * The type Customer controller.
 */
@Slf4j
@Validated
@RestController
@RequestMapping(path = "/api/private/customer")
@CtrlDef(handler = ImsExceptionHandler.class, mapper = CustomerMapper.class, service = CustomerService.class)
public class CustomerController extends MappedCrudController<Customer, CustomerDto, CustomerDto, CustomerService> {

    @Autowired
    private ICustomerService customerService;

    /**
     * Update customer status response entity.
     *
     * @param requestContext the request context
     * @param id             the id
     * @param newStatus      the new status
     * @return the response entity
     */
    @Operation(summary = "Update customer status Api",
            description = "Update customer status")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Api executed successfully",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = AbstractDto.class))})
    })
    @PutMapping(path = "/update-status")
    public ResponseEntity<CustomerDto> updateCustomerStatus(@RequestAttribute(value = JwtContants.JWT_USER_CONTEXT, required = false) RequestContextDto requestContext,
                                                            @RequestParam(name = RestApiContants.ID) Long id,
                                                            @RequestParam(name = RestApiContants.NEW_STATUS) IEnumBinaryStatus.Types newStatus) {
        log.info("in update status");
        try {
            return ResponseFactory.ResponseOk(mapper().entityToDto(crudService().updateStatus(id, newStatus)));
        } catch (Throwable e) {
            log.error("<Error>: api exception : {} ", e);
            return getBackExceptionResponse(e);
        }
    }

    /**
     * Gets name customer.
     *
     * @param requestContext the request context
     * @return the name customer
     */
    @Operation(summary = "xxx Api",
            description = "xxx")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Api executed successfully",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = AbstractDto.class))})
    })
    @GetMapping(path = "/names")
    public ResponseEntity<List<String>> getNameCustomer(@RequestAttribute(value = JwtContants.JWT_USER_CONTEXT, required = false) RequestContextDto requestContext) {
        try {
            return ResponseFactory.ResponseOk(customerService.getNames());
        } catch (Throwable e) {
            log.error("<Error>: api exception : {} ", e);
            return getBackExceptionResponse(e);
        }
    }
}
