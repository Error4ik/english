package com.voronin.english.service;

import com.voronin.english.domain.Category;
import com.voronin.english.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * TODO: comment.
 *
 * @author Alexey Voronin.
 * @since 10.10.2018.
 */
@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    public List<Category> getCategories() {
        return this.categoryRepository.findAll();
    }

    public Category getCategoryByname(final String name) {
        return this.categoryRepository.getCategoryByName(name);
    }
}
