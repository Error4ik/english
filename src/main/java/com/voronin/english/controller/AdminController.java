package com.voronin.english.controller;

import com.voronin.english.domain.CardFilled;
import com.voronin.english.domain.Category;
import com.voronin.english.service.CategoryService;
import com.voronin.english.service.WordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * TODO: comment.
 *
 * @author Alexey Voronin.
 * @since 08.11.2018.
 */
@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private WordService wordService;

    @Autowired
    private CategoryService categoryService;

    @RequestMapping("/add-card")
    public void saveWord(final CardFilled cardFilled, @RequestParam(value = "photo", required = false) MultipartFile photo) {
        this.wordService.prepareAndSave(cardFilled, photo);
    }

    @RequestMapping("/add-category")
    public void saveCategory(final Category category, @RequestParam(value = "photo", required = false) MultipartFile file) {
        this.categoryService.prepareAndSave(category, file);
    }
}
