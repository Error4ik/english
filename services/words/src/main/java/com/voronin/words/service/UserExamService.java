package com.voronin.words.service;

import com.voronin.words.domain.Exam;
import com.voronin.words.domain.UserExam;
import com.voronin.words.repository.UserExamRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * UserExam service.
 *
 * @author Alexey Voronin.
 * @since 06.09.2019.
 */
@Service
public class UserExamService {

    /**
     * Logger.
     */
    private final Logger logger = LoggerFactory.getLogger(UserExamService.class);

    /**
     * User exam repository.
     */
    private final UserExamRepository userExamRepository;

    /**
     * Exam service.
     */
    private final ExamService examService;

    /**
     * Constructor.
     *
     * @param userExamRepository User exam repository.
     * @param examService        exam service.
     */
    @Autowired
    public UserExamService(final UserExamRepository userExamRepository, final ExamService examService) {
        this.userExamRepository = userExamRepository;
        this.examService = examService;
    }

    /**
     * Save UserExamsStats.
     *
     * @param principal     principal.
     * @param examId        exam id.
     * @param correctAnswer correct answer.
     * @return UserExamsStats.
     */
    public UserExam save(final Principal principal, final UUID examId, final int correctAnswer) {
        logger.debug(String.format("Arguments - examId - %s, correctAnswer - %s", examId, correctAnswer));
        UUID userId = this.getUserId(principal);
        Exam exam = this.examService.getExamById(examId);
        UserExam examsStats = this.userExamRepository.getUserExamByUserIdAndExam(userId, exam);
        if (examsStats == null) {
            examsStats = new UserExam(userId, exam, exam.getQuestions().size(), correctAnswer);
        } else {
            examsStats.setTotalQuestions(exam.getQuestions().size());
            examsStats.setCorrectAnswer(correctAnswer);
            examsStats.setDate(new Timestamp(System.currentTimeMillis()));
        }
        examsStats = this.userExamRepository.save(examsStats);
        logger.debug(String.format("Return - %s", examsStats));
        return examsStats;
    }

    /**
     * Get list UserExamStats by user.
     *
     * @param principal principal.
     * @return List of UserExamsStats.
     */
    public List<UserExam> getUserExamByUser(final Principal principal) {
        UUID userId = getUserId(principal);
        return this.userExamRepository.getUserExamByUserId(userId);
    }

    /**
     * Get user id from Auth service.
     *
     * @param principal access token.
     * @return user id.
     */
    private UUID getUserId(final Principal principal) {
        final String key = (String) ((Map) ((Map) ((OAuth2Authentication) principal)
                .getUserAuthentication().getDetails()).get("principal")).get("id");
        return UUID.fromString(key);
    }
}
