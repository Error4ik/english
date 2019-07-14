package com.voronin.english.controller;

import com.google.common.collect.Lists;
import com.voronin.english.domain.User;
import com.voronin.english.domain.Category;
import com.voronin.english.domain.CardFilled;
import com.voronin.english.domain.Question;
import com.voronin.english.domain.Exam;
import com.voronin.english.domain.Role;
import com.voronin.english.domain.PhraseForTraining;
import com.voronin.english.service.WordService;
import com.voronin.english.service.CategoryService;
import com.voronin.english.service.NounService;
import com.voronin.english.service.QuestionService;
import com.voronin.english.service.ExamService;
import com.voronin.english.service.PhraseForTrainingService;
import com.voronin.english.service.UserService;
import com.voronin.english.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;
import java.util.List;
import java.util.UUID;

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
     * Noun service.
     */
    private final NounService nounService;

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
     * UserService.
     */
    private final UserService userService;

    /**
     * RoleService.
     */
    private final RoleService roleService;

    /**
     * Controller.
     *
     * @param nounService              noun service.
     * @param wordService              word service.
     * @param categoryService          category service.
     * @param questionService          question service.
     * @param examService              exam service.
     * @param phraseForTrainingService PhraseForTrainingService.
     * @param userService              UserService.
     * @param roleService              RoleService.
     */
    @Autowired
    public AdminController(
            final NounService nounService,
            final WordService wordService,
            final CategoryService categoryService,
            final QuestionService questionService,
            final ExamService examService,
            final PhraseForTrainingService phraseForTrainingService,
            final UserService userService,
            final RoleService roleService) {
        this.nounService = nounService;
        this.wordService = wordService;
        this.categoryService = categoryService;
        this.questionService = questionService;
        this.examService = examService;
        this.phraseForTrainingService = phraseForTrainingService;
        this.userService = userService;
        this.roleService = roleService;
    }

    /**
     * Add new noun.
     *
     * @param cardFilled Filled model for cards.
     * @param photo      the image to the word.
     */
    @RequestMapping("/add-noun")
    public void saveNoun(
            final CardFilled cardFilled,
            final @RequestParam(value = "photo", required = false) MultipartFile photo) {
        this.nounService.prepareAndSave(cardFilled, photo);
    }

    /**
     * Edit noun.
     *
     * @param cardFilled filled model of card.
     * @param photo      the image to the word.
     * @param nounId     noun id for edit.
     */
    @RequestMapping("/edit-noun")
    public void editNoun(
            final CardFilled cardFilled,
            final @RequestParam(value = "photo", required = false) MultipartFile photo,
            final String nounId) {
        this.nounService.editNounAndSave(cardFilled, photo, nounId);
    }

    /**
     * Delete noun.
     *
     * @param id nounId for delete.
     */
    @RequestMapping("/delete-noun")
    public void deleteNoun(@RequestParam final UUID id) {
        nounService.deleteNoun(id);
    }

    /**
     * Add new word.
     *
     * @param cardFilled Filled model for cards.
     */
    @RequestMapping("/add-word")
    public void saveWord(final CardFilled cardFilled) {
        this.wordService.prepareAndSave(cardFilled);
    }

    /**
     * Edit word.
     *
     * @param cardFilled filled model of card.
     * @param wordId     word id fo edit.
     */
    @RequestMapping("/edit-word")
    public void editWord(final CardFilled cardFilled, final String wordId) {
        this.wordService.editWordAndSave(cardFilled, wordId);
    }

    /**
     * Delete word.
     *
     * @param id wordId for delete.
     */
    @RequestMapping("/delete-word")
    public void deleteWord(@RequestParam final UUID id) {
        wordService.deleteWord(id);
    }


    /**
     * Add new category for nouns.
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
     * Add new question for exam by nouns.
     *
     * @param exam     name of the exam.
     * @param noun     correct answer.
     * @param variants answers variants.
     * @return question saved to the database.
     */
    @RequestMapping("/add-question")
    public Question addQuestion(
            final @RequestParam String exam,
            final @RequestParam String noun,
            final @RequestParam List<String> variants) {
        return this.questionService.prepareAndSave(exam, noun, variants);
    }

    /**
     * @param name     exam name.
     * @param category name of the category for exam.
     * @param type     the type of exam.
     * @return exam saved to the database.
     */
    @RequestMapping("/add-exam")
    public Exam addExam(final String name, final String category, final int type) {
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

    /**
     * Get all users.
     *
     * @return list of User.
     */
    @RequestMapping("/users")
    public List<User> getUsers() {
        return this.userService.getUsers();
    }

    /**
     * Return all roles.
     *
     * @return list of Role.
     */
    @RequestMapping("/roles")
    public List<Role> getRoles() {
        return this.roleService.getRoles();
    }

    /**
     * Change the user role.
     *
     * @param principal Principal.
     * @param userId    User.
     * @param roleId    Role.
     */
    @RequestMapping("/change-role")
    public void changeRole(
            final Principal principal,
            final @RequestParam UUID userId,
            final @RequestParam String roleId) {
        userService.changeUserRole(principal, userId, UUID.fromString(roleId));
    }
}
