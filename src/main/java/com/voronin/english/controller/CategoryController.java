package com.voronin.english.controller;

import com.voronin.english.domain.Category;
import com.voronin.english.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;

/**
 * TODO: comment.
 *
 * @author Alexey Voronin.
 * @since 16.10.2018.
 */
@RestController
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @RequestMapping("/categories")
    public List<Category> getCategories(Principal principal) {
        return this.categoryService.getCategories();
    }
}
