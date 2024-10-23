package eu.novobit.controller;

import eu.novobit.com.rest.controller.FakeCrudController;
import eu.novobit.dto.data.ResumeShareInfoDto;
import eu.novobit.model.ResumeShareInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * The type Resume share info controller.
 */
@Slf4j
@Validated
@RestController
@RequestMapping(path = "/api/private/resume/shareInfo")
public class ResumeShareInfoController extends FakeCrudController<ResumeShareInfo, ResumeShareInfoDto, ResumeShareInfoDto> {
}
