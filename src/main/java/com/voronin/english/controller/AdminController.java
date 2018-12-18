package com.voronin.english.controller;

import com.voronin.english.domain.CardFilled;
import com.voronin.english.domain.Category;
import com.voronin.english.domain.Exam;
import com.voronin.english.domain.Question;
import com.voronin.english.service.CategoryService;
import com.voronin.english.service.ExamService;
import com.voronin.english.service.QuestionService;
import com.voronin.english.service.WordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

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

    @Autowired
    private QuestionService questionService;

    @Autowired
    private ExamService examService;

    @RequestMapping("/add-card")
    public void saveWord(final CardFilled cardFilled, @RequestParam(value = "photo", required = false) MultipartFile photo) {
        this.wordService.prepareAndSave(cardFilled, photo);
    }

    @RequestMapping("/add-category")
    public void saveCategory(final Category category, @RequestParam(value = "photo", required = false) MultipartFile file) {
        this.categoryService.prepareAndSave(category, file);
    }

    @RequestMapping("/add-question")
    public Question addQuestion(@RequestParam String exam, @RequestParam String word, @RequestParam List<String> variants) {
        return this.questionService.prepareAndSave(exam, word, variants);
    }

    @RequestMapping("/add-exam")
    public Exam addExam(@RequestParam String name, String category) {
        return examService.prepareAndSave(name, category);
    }
}
