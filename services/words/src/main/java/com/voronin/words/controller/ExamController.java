package com.voronin.words.controller;

import com.voronin.words.domain.Exam;
import com.voronin.words.domain.Question;
import com.voronin.words.domain.UserExam;
import com.voronin.words.service.ExamService;
import com.voronin.words.service.QuestionService;
import com.voronin.words.service.UserExamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;
import java.util.UUID;

/**
 * ExamController.
 *
 * @author Alexey Voronin.
 * @since 06.09.2019.
 */
@RestController
@RequestMapping("/exam")
public class ExamController {

    /**
     * Exam service.
     */
    private final ExamService examService;

    /**
     * User exam stat service.
     */
    private final UserExamService userExamService;

    /**
     * Question Service.
     */
    private QuestionService questionService;

    /**
     * Constructor.
     *
     * @param examService     exam service.
     * @param userExamService user exam service.
     */
    @Autowired
    public ExamController(
            final ExamService examService,
            final UserExamService userExamService,
            final QuestionService questionService) {
        this.examService = examService;
        this.userExamService = userExamService;
        this.questionService = questionService;
    }

    /**
     * @param name   exam name.
     * @param speech part of speech for exam.
     * @param type   the type of exam.
     * @return exam saved to the database.
     */
    @RequestMapping("/add-exam")
    @PreAuthorize("hasAuthority('admin')")
    public Exam addExam(final String name, final String speech, final int type) {
        return examService.prepareAndSave(name, speech, type);
    }

    /**
     * Add new question for exam by nouns.
     *
     * @param exam     name of the exam.
     * @param word     correct answer.
     * @param variants answers variants.
     * @return question saved to the database.
     */
    @RequestMapping("/add-question")
    @PreAuthorize("hasAuthority('admin')")
    public Question addQuestion(
            final @RequestParam String exam,
            final @RequestParam String word,
            final @RequestParam List<String> variants) {
        return this.questionService.prepareAndSave(exam, word, variants);
    }

    /**
     * Get exam by id.
     *
     * @param id id.
     * @return exam.
     */
    @RequestMapping("/{id}")
    public Exam getExam(final @PathVariable UUID id) {
        return this.examService.getExamById(id);
    }

    /**
     * Get all exams.
     *
     * @return list of exam.
     */
    @RequestMapping("/exams")
    public List<Exam> getExams() {
        return this.examService.getExams();
    }

    /**
     * Save all user-completed exams.
     *
     * @param principal     principal.
     * @param examId        exam id.
     * @param correctAnswer number of correct answers.
     * @return user exam stats.
     */
    @RequestMapping("/save-stats-for-exam")
    public UserExam saveStats(final Principal principal,
                              @RequestParam final UUID examId,
                              @RequestParam final int correctAnswer) {
        return userExamService.save(principal, examId, correctAnswer);
    }

    /**
     * @param principal principal.
     * @return list of exam statistics.
     */
    @RequestMapping("/exam-stats-by-user")
    public List<UserExam> getStatsByUser(final Principal principal) {
        return this.userExamService.getUserExamByUser(principal);
    }
}
