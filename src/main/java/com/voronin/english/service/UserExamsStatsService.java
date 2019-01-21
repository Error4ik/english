package com.voronin.english.service;

import com.voronin.english.domain.Exam;
import com.voronin.english.domain.User;
import com.voronin.english.domain.UserExamsStats;
import com.voronin.english.repository.UserExamsStatsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

/**
 * TODO: comment.
 *
 * @author Alexey Voronin.
 * @since 12.12.2018.
 */
@Service
public class UserExamsStatsService {

    @Autowired
    private UserExamsStatsRepository userExamsStatsRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private ExamService examService;

    public UserExamsStats save(final Principal principal, final UUID examId, final int correctAnswer) {
        User user = this.userService.getUserByEmail(principal.getName());
        Exam exam = this.examService.getExamById(examId);
        UserExamsStats examsStats = this.userExamsStatsRepository.getUserExamsStatsByUserAndExam(user, exam);
        if (examsStats == null) {
            examsStats = new UserExamsStats(user, exam, exam.getQuestions().size(), correctAnswer);
        } else {
            examsStats.setTotalQuestions(exam.getQuestions().size());
            examsStats.setCorrectAnswer(correctAnswer);
            examsStats.setDateOfTheExam(new Timestamp(System.currentTimeMillis()));
        }
        return this.userExamsStatsRepository.save(examsStats);
    }

    public List<UserExamsStats> getUserExamsStatsByUser(final User user) {
        return this.userExamsStatsRepository.getUserExamsStatsByUser(user);
    }
}
