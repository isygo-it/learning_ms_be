package eu.novobit.service.impl;

import eu.novobit.annotation.SrvRepo;
import eu.novobit.com.rest.service.AbstractCrudService;
import eu.novobit.model.Category;
import eu.novobit.repository.CategoryRepository;
import eu.novobit.service.ICategoryService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * The type Category service.
 */
@Service
@Transactional
@SrvRepo(value = CategoryRepository.class)
public class CategoryService extends AbstractCrudService<Category, CategoryRepository> implements ICategoryService {

}
