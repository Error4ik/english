package com.voronin.sentence.controller;

import com.voronin.sentence.domain.Exam;
import com.voronin.sentence.domain.Question;
import com.voronin.sentence.domain.UserExam;
import com.voronin.sentence.service.ExamService;
import com.voronin.sentence.service.QuestionService;
import com.voronin.sentence.service.UserExamService;
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
 * @since 04.10.2019.
 */
@RestController
@RequestMapping("/exam")
public class ExamController {

    /**
     * Exam service.
     */
    private final ExamService examService;

    /**
     * QuestionService.
     */
    private final QuestionService questionService;

    /**
     * UserExamService.
     */
    private final UserExamService userExamService;

    /**
     * Constructor.
     *
     * @param examService     ExamService.
     * @param questionService QuestionService.
     * @param userExamService UserExamService.
     */
    @Autowired
    public ExamController(
            final ExamService examService,
            final QuestionService questionService,
            final UserExamService userExamService) {
        this.examService = examService;
        this.questionService = questionService;
        this.userExamService = userExamService;
    }

    /**
     * Get exams.
     *
     * @return list of exams.
     */
    @RequestMapping("/exams")
    public List<Exam> getExams() {
        return examService.getExams();
    }

    /**
     * Save exam.
     *
     * @param examName exam name.
     * @param examType exam type.
     * @return saved exam.
     */
    @PreAuthorize("hasAuthority('admin')")
    @RequestMapping("/add-exam")
    public Exam saveExam(final String examName, final String examType) {
        return this.examService.save(examName, examType);
    }

    /**
     * Add Question.
     *
     * @param question Question.
     * @param answer   answer for question.
     * @param keyWords list of KeyWords.
     * @param examId   Exam id.
     * @return saved Question.
     */
    @PreAuthorize("hasAuthority('admin')")
    @RequestMapping("/add-question")
    public Question save(
            @RequestParam final String question,
            @RequestParam final String answer,
            @RequestParam final List<String> keyWords,
            @RequestParam final String examId) {
        return this.questionService.save(question, answer, keyWords, examId);
    }

    /**
     * Get Exam by Id.
     *
     * @param id exam id.
     * @return Exam.
     */
    @RequestMapping("/{id}")
    public Exam getExamById(@PathVariable final UUID id) {
        return this.examService.getExamById(id);
    }

    /**
     * Save all user-completed exams.
     *
     * @param principal      principal.
     * @param examId         exam id.
     * @param correctAnswers number of correct answers.
     * @return user exam stats.
     */
    @RequestMapping("/save-stats-for-exam")
    public UserExam saveStats(final Principal principal,
                              @RequestParam final UUID examId,
                              @RequestParam final int correctAnswers) {
        return userExamService.save(principal, examId, correctAnswers);
    }

    /**
     * Get UserExam by user.
     *
     * @param principal user.
     * @return List of UserExams.
     */
    @RequestMapping("/exam-stats-by-user")
    public List<UserExam> getStatsByUser(final Principal principal) {
        return this.userExamService.getUserExamByUser(principal);
    }
}
