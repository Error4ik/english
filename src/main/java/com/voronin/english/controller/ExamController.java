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
 * TODO: comment.
 *
 * @author Alexey Voronin.
 * @since 08.12.2018.
 */
@RestController
@RequestMapping("/exam")
public class ExamController {

    @Autowired
    private ExamService examService;

    @Autowired
    private UserExamsStatsService userExamsStatsService;

    @Autowired
    private UserService userService;

    @RequestMapping("/{id}")
    public Exam getExam(final @PathVariable UUID id) {
        return this.examService.getExamById(id);
    }

    @RequestMapping("/exams")
    public List<Exam> getExams() {
        return this.examService.getExams();
    }

    @RequestMapping("/save-stats-for-exam")
    public UserExamsStats saveStats(final Principal principal,
                                    @RequestParam final UUID examId,
                                    @RequestParam final int correctAnswer) {
        return userExamsStatsService.save(principal, examId, correctAnswer);
    }

    @RequestMapping("/exam-stats-by-user")
    public UserExamsStats getStatsByUser(final Principal principal) {
        return this.userExamsStatsService.getUserExamsStatsByUser(this.userService.getUserByEmail(principal.getName()));
    }
}
