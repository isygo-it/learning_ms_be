package eu.novobit.repository;


import eu.novobit.model.Category;

import java.util.Optional;

/**
 * The interface Category repository.
 */
public interface CategoryRepository extends JpaPagingAndSortingRepository<Category, Long> {
    /**
     * Find by name optional.
     *
     * @param categoryName the category name
     * @return the optional
     */
    Optional<Category> findByName(String categoryName);
}
