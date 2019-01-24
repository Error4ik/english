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
 * User exam stats service.
 *
 * @author Alexey Voronin.
 * @since 12.12.2018.
 */
@Service
public class UserExamsStatsService {

    /**
     * User exam stats repository.
     */
    private final UserExamsStatsRepository userExamsStatsRepository;

    /**
     * User service.
     */
    private final UserService userService;

    /**
     * Exam service.
     */
    private final ExamService examService;

    /**
     * Constructor.
     *
     * @param userExamsStatsRepository User exam stats repository.
     * @param userService              user service.
     * @param examService              exam service.
     */
    @Autowired
    public UserExamsStatsService(
            final UserExamsStatsRepository userExamsStatsRepository,
            final UserService userService,
            final ExamService examService) {
        this.userExamsStatsRepository = userExamsStatsRepository;
        this.userService = userService;
        this.examService = examService;
    }

    /**
     * Save UserExamsStats.
     *
     * @param principal     user.
     * @param examId        exam id.
     * @param correctAnswer correct answer.
     * @return UserExamsStats.
     */
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

    /**
     * Get list UserExamStats by user.
     * @param user user.
     * @return List of UserExamsStats.
     */
    public List<UserExamsStats> getUserExamsStatsByUser(final User user) {
        return this.userExamsStatsRepository.getUserExamsStatsByUser(user);
    }
}
