package com.voronin.english.controller;

import com.voronin.english.domain.CardFilled;
import com.voronin.english.domain.Category;
import com.voronin.english.domain.Question;
import com.voronin.english.domain.Exam;
import com.voronin.english.domain.PhraseForTraining;
import com.voronin.english.service.WordService;
import com.voronin.english.service.CategoryService;
import com.voronin.english.service.QuestionService;
import com.voronin.english.service.ExamService;
import com.voronin.english.service.PhraseForTrainingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * Admin controller class.
 * This controller contain all method for admins.
 *
 * @author Alexey Voronin.
 * @since 08.11.2018.
 */
@RestController
@RequestMapping("/admin")
public class AdminController {

    /**
     * Word service.
     */
    private final WordService wordService;

    /**
     * Category service.
     */
    private final CategoryService categoryService;

    /**
     * Question service.
     */
    private final QuestionService questionService;

    /**
     * Exam service.
     */
    private final ExamService examService;

    /**
     * PhraseForTrainingService.
     */
    private PhraseForTrainingService phraseForTrainingService;

    /**
     * Controller.
     *
     * @param wordService              word service.
     * @param categoryService          category service.
     * @param questionService          question service.
     * @param examService              exam service.
     * @param phraseForTrainingService PhraseForTrainingService.
     */
    @Autowired
    public AdminController(
            final WordService wordService,
            final CategoryService categoryService,
            final QuestionService questionService,
            final ExamService examService,
            final PhraseForTrainingService phraseForTrainingService) {
        this.wordService = wordService;
        this.categoryService = categoryService;
        this.questionService = questionService;
        this.examService = examService;
        this.phraseForTrainingService = phraseForTrainingService;
    }

    /**
     * Add new card.
     *
     * @param cardFilled Filled model for cards.
     * @param photo      the image to the word.
     */
    @RequestMapping("/add-card")
    public void saveWord(
            final CardFilled cardFilled,
            final @RequestParam(value = "photo", required = false) MultipartFile photo) {
        this.wordService.prepareAndSave(cardFilled, photo);
    }

    /**
     * Add new category for words.
     *
     * @param category category.
     * @param file     the image to the category.
     */
    @RequestMapping("/add-category")
    public void saveCategory(
            final Category category,
            final @RequestParam(value = "photo", required = false) MultipartFile file) {
        this.categoryService.prepareAndSave(category, file);
    }

    /**
     * @param exam     name of the exam.
     * @param word     correct answer.
     * @param variants answers variants.
     * @return question saved to the database.
     */
    @RequestMapping("/add-question")
    public Question addQuestion(
            final @RequestParam String exam,
            final @RequestParam String word,
            final @RequestParam List<String> variants) {
        return this.questionService.prepareAndSave(exam, word, variants);
    }

    /**
     * @param name     exam name.
     * @param category name of the category for exam.
     * @param type     the type of exam.
     * @return exam saved to the database.
     */
    @RequestMapping("/add-exam")
    public Exam addExam(final @RequestParam String name, final String category, final int type) {
        return examService.prepareAndSave(name, category, type);
    }

    /**
     * @param phrase    english phrase.
     * @param translate translate.
     * @param category  category name.
     * @return saved PhraseForTraining.
     */
    @RequestMapping("/add-phrase-for-training")
    public PhraseForTraining addPhrase(
            final @RequestParam String phrase,
            final @RequestParam String translate,
            final @RequestParam String category) {
        return this.phraseForTrainingService.prepareAndSave(phrase, translate, category);
    }
}
