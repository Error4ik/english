package com.voronin.sentence.service;

import com.voronin.sentence.domain.Category;
import com.voronin.sentence.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Category service.
 *
 * @author Alexey Voronin.
 * @since 02.02.2019.
 */
@Service
public class CategoryService {

    /**
     * CategoryRepository.
     */
    private final CategoryRepository categoryRepository;

    /**
     * Constructor.
     *
     * @param categoryRepository CategoryRepository.
     */
    @Autowired
    public CategoryService(final CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    /**
     * Save entity.
     *
     * @param category Category.
     * @return saved entity.
     */
    public Category save(final Category category) {
        return this.categoryRepository.save(category);
    }

    /**
     * Get all Categories.
     *
     * @return list of Categories.
     */
    public List<Category> getCategories() {
        return this.categoryRepository.findAllByOrderByTimeAsc();
    }

    /**
     * Get category by name.
     *
     * @param categoryName category name.
     * @return Category.
     */
    public Category getCategoryByName(final String categoryName) {
        return this.categoryRepository.getByName(categoryName);
    }
}
