package com.voronin.sentence.controller;

import com.voronin.sentence.domain.Category;
import com.voronin.sentence.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Category controller.
 *
 * @author Alexey Voronin.
 * @since 16.10.2018.
 */
@RestController
@RequestMapping("/category")
public class CategoryController {

    /**
     * Category service.
     */
    private final CategoryService categoryService;


    /**
     * Controller.
     *
     * @param categoryService category service.
     */
    @Autowired
    public CategoryController(final CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    /**
     * @return list of category.
     */
    @RequestMapping("/categories")
    public List<Category> getCategories() {
        return categoryService.getCategories();
    }
}
