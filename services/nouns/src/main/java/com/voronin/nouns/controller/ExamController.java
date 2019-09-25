package com.voronin.nouns.controller;

import com.voronin.nouns.domain.Exam;
import com.voronin.nouns.domain.Question;
import com.voronin.nouns.domain.UserExamsStats;
import com.voronin.nouns.service.ExamService;
import com.voronin.nouns.service.QuestionService;
import com.voronin.nouns.service.UserExamsStatsService;
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
 * Exam controller.
 *
 * @author Alexey Voronin.
 * @since 30.08.2019.
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
    private final UserExamsStatsService userExamsStatsService;

    /**
     * Question Service.
     */
    private QuestionService questionService;

    /**
     * Constructor.
     *
     * @param examService           exam service.
     * @param userExamsStatsService user exam stat service.
     */
    @Autowired
    public ExamController(
            final ExamService examService,
            final UserExamsStatsService userExamsStatsService,
            final QuestionService questionService) {
        this.examService = examService;
        this.userExamsStatsService = userExamsStatsService;
        this.questionService = questionService;
    }

    /**
     * @param name     exam name.
     * @param category name of the category for exam.
     * @param type     the type of exam.
     * @return exam saved to the database.
     */
    @RequestMapping("/add-exam")
    @PreAuthorize("hasAuthority('admin')")
    public Exam addExam(final String name, final String category, final int type) {
        return examService.prepareAndSave(name, category, type);
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
    @PreAuthorize("hasAuthority('admin')")
    public Question addQuestion(
            final @RequestParam String exam,
            final @RequestParam String noun,
            final @RequestParam List<String> variants) {
        return this.questionService.prepareAndSave(exam, noun, variants);
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
    public UserExamsStats saveStats(final Principal principal,
                                    @RequestParam final UUID examId,
                                    @RequestParam final int correctAnswer) {
        return userExamsStatsService.save(principal, examId, correctAnswer);
    }

    /**
     * @param principal principal.
     * @return list of exam statistics.
     */
    @RequestMapping("/exam-stats-by-user")
    public List<UserExamsStats> getStatsByUser(final Principal principal) {
        return this.userExamsStatsService.getUserExamsStatsByUser(principal);
    }
}
