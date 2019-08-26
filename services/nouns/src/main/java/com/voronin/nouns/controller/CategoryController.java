package com.voronin.nouns.controller;

import com.voronin.nouns.domain.Category;
import com.voronin.nouns.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

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
        return this.categoryService.getCategories();
    }

    /**
     * Add new category for nouns.
     *
     * @param category category.
     * @param file     the image to the category.
     */
    @PreAuthorize("hasAuthority('admin')")
    @RequestMapping("/add-category")
    public void saveCategory(
            final Category category,
            final @RequestParam(value = "photo", required = false) MultipartFile file) {
        this.categoryService.prepareAndSave(category, file);
    }
}
