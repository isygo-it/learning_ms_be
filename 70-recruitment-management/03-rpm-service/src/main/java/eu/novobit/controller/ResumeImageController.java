package eu.novobit.controller;

import eu.novobit.annotation.CtrlDef;
import eu.novobit.com.rest.controller.MappedImageController;
import eu.novobit.dto.data.ResumeDto;
import eu.novobit.exception.handler.RpmExceptionHandler;
import eu.novobit.mapper.ResumeMapper;
import eu.novobit.model.Resume;
import eu.novobit.service.impl.ResumeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * The type Resume image controller.
 */
@Slf4j
@Validated
@RestController
@RequestMapping(path = "/api/private/resume")
@CtrlDef(handler = RpmExceptionHandler.class, mapper = ResumeMapper.class, service = ResumeService.class)
public class ResumeImageController extends MappedImageController<Resume, ResumeDto, ResumeDto, ResumeService> {

}
