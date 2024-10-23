package eu.novobit.controller;

import eu.novobit.annotation.CtrlDef;
import eu.novobit.com.rest.controller.MappedCrudController;
import eu.novobit.dto.data.CategoryDto;
import eu.novobit.exception.handler.DmsExceptionHandler;
import eu.novobit.mapper.CategoryMapper;
import eu.novobit.model.Category;
import eu.novobit.service.impl.CategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * The type Category controller.
 */
@Slf4j
@Validated
@RestController
@RequestMapping(path = "/api/private/dms/category")
@CtrlDef(handler = DmsExceptionHandler.class, mapper = CategoryMapper.class, service = CategoryService.class)
public class CategoryController extends MappedCrudController<Category, CategoryDto, CategoryDto, CategoryService> {

}
