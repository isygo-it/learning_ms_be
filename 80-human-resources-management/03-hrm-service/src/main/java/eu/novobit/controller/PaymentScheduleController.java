package eu.novobit.controller;

import eu.novobit.annotation.CtrlDef;
import eu.novobit.com.rest.controller.MappedCrudController;
import eu.novobit.dto.AbstractDto;
import eu.novobit.dto.data.PaymentScheduleDto;
import eu.novobit.exception.handler.HrmExceptionHandler;
import eu.novobit.mapper.PaymentScheduleMapper;
import eu.novobit.model.PaymentSchedule;
import eu.novobit.service.IPaymentScheduleService;
import eu.novobit.service.impl.PaymentScheduleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * The type PaymentSchedule controller.
 */
@Slf4j
@Validated
@RestController
@CtrlDef(handler = HrmExceptionHandler.class, mapper = PaymentScheduleMapper.class, service = PaymentScheduleService.class)
@RequestMapping(value = "/api/private/payment-Schedule")
public class PaymentScheduleController extends MappedCrudController<PaymentSchedule, PaymentScheduleDto, PaymentScheduleDto, PaymentScheduleService> {
    @Autowired
    private IPaymentScheduleService paymentScheduleService;

    /**
     * Calculate payment schedule response entity.
     *
     * @param contractId the contract id
     * @return the response entity
     */
    @Operation(summary = "xxx Api", description = "xxx")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Api executed successfully",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = AbstractDto.class))),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })

    @GetMapping(path = "/calculate/{contractId}")
    public ResponseEntity<List<PaymentScheduleDto>> calculatePaymentSchedule(@PathVariable Long contractId) {
        try {
            return ResponseEntity.ok(mapper().listEntityToDto(paymentScheduleService.calculatePaymentSchedule(contractId)));
        } catch (Exception e) {
            log.error(ERROR_API_EXCEPTION, e);
            return getBackExceptionResponse(e);
        }

    }
}