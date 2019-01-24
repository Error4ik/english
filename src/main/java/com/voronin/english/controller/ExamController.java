package com.voronin.english.controller;

import com.voronin.english.domain.Exam;
import com.voronin.english.domain.UserExamsStats;
import com.voronin.english.service.ExamService;
import com.voronin.english.service.UserExamsStatsService;
import com.voronin.english.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
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
 * @since 08.12.2018.
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
     * User service.
     */
    private final UserService userService;

    /**
     * Constructor.
     *
     * @param examService           exam service.
     * @param userExamsStatsService user exam stat service.
     * @param userService           user service.
     */
    @Autowired
    public ExamController(
            final ExamService examService,
            final UserExamsStatsService userExamsStatsService,
            final UserService userService) {
        this.examService = examService;
        this.userExamsStatsService = userExamsStatsService;
        this.userService = userService;
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
     * @param principal     user.
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
     *
     * @param principal user.
     * @return list of exam statistics.
     */
    @RequestMapping("/exam-stats-by-user")
    public List<UserExamsStats> getStatsByUser(final Principal principal) {
        return this.userExamsStatsService.getUserExamsStatsByUser(
                this.userService.getUserByEmail(principal.getName()));
    }
}
