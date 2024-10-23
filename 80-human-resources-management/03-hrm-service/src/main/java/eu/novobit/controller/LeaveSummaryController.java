package eu.novobit.controller;

import eu.novobit.annotation.CtrlDef;
import eu.novobit.com.rest.controller.MappedCrudController;
import eu.novobit.dto.data.LeaveSummaryDto;
import eu.novobit.exception.handler.HrmExceptionHandler;
import eu.novobit.mapper.LeaveSummaryMapper;
import eu.novobit.model.LeaveSummary;
import eu.novobit.service.impl.LeaveSummaryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * The type Leave summary controller.
 */
@Slf4j
@Validated
@RestController
@CtrlDef(handler = HrmExceptionHandler.class, mapper = LeaveSummaryMapper.class, service = LeaveSummaryService.class)
@RequestMapping(value = "/api/private/leaveStatus")
public class LeaveSummaryController extends MappedCrudController<LeaveSummary, LeaveSummaryDto, LeaveSummaryDto, LeaveSummaryService> {

}
