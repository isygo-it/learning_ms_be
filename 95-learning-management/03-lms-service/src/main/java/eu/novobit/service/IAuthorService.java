package eu.novobit.service;

import eu.novobit.com.rest.service.ICrudImageService;
import eu.novobit.com.rest.service.ICrudServiceMethods;
import eu.novobit.model.Author;

/**
 * The interface Author service.
 */
public interface IAuthorService extends ICrudServiceMethods<Author>, ICrudImageService<Author> {
}
