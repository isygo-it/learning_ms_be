package eu.novobit.controller;

import eu.novobit.annotation.CtrlDef;
import eu.novobit.com.rest.controller.MappedImageController;
import eu.novobit.dto.data.AuthorDto;
import eu.novobit.exception.handler.LmsExceptionHandler;
import eu.novobit.mapper.AuthorMapper;
import eu.novobit.model.Author;
import eu.novobit.service.impl.AuthorService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * The type Author controller.
 */
@Slf4j
@Validated
@RestController
@CtrlDef(handler = LmsExceptionHandler.class, mapper = AuthorMapper.class, service = AuthorService.class)
@RequestMapping(value = "/api/private/author")
public class AuthorImageController extends MappedImageController<Author, AuthorDto, AuthorDto, AuthorService> {

}
