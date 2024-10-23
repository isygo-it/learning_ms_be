package eu.novobit.controller;

import eu.novobit.annotation.CtrlDef;
import eu.novobit.com.rest.controller.MappedCrudController;
import eu.novobit.dto.data.JobTemplateDto;
import eu.novobit.exception.handler.RpmExceptionHandler;
import eu.novobit.mapper.JobTemplateMapper;
import eu.novobit.model.JobTemplate;
import eu.novobit.service.impl.JobTemplateService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * The type Job template controller.
 */
@Slf4j
@Validated
@RestController
@RequestMapping(path = "/api/private/jobTemplate")
@CtrlDef(handler = RpmExceptionHandler.class, mapper = JobTemplateMapper.class, service = JobTemplateService.class)
public class JobTemplateController extends MappedCrudController<JobTemplate, JobTemplateDto, JobTemplateDto, JobTemplateService> {

}
