package com.voronin.sentence.service;

import com.voronin.sentence.domain.Exam;
import com.voronin.sentence.domain.UserExam;
import com.voronin.sentence.repository.UserExamRepository;
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
 * UserExam Service.
 *
 * @author Alexey Voronin.
 * @since 10.10.2019.
 */
@Service
public class UserExamService {

    /**
     * Logger.
     */
    private final Logger logger = LoggerFactory.getLogger(UserExamService.class);

    /**
     * UserExamRepository.
     */
    private final UserExamRepository userExamRepository;

    /**
     * ExamService.
     */
    private final ExamService examService;

    /**
     * Constructor.
     *
     * @param userExamRepository UserExamRepository.
     * @param examService        ExamService.
     */
    @Autowired
    public UserExamService(final UserExamRepository userExamRepository, final ExamService examService) {
        this.userExamRepository = userExamRepository;
        this.examService = examService;
    }

    /**
     * Save UserExam.
     *
     * @param principal      user.
     * @param examId         exam id.
     * @param correctAnswers correct answers.
     * @return saved UserExam.
     */
    public UserExam save(final Principal principal, final UUID examId, final int correctAnswers) {
        logger.debug(String.format("Arguments - examId - %s, correctAnswers - %s", examId, correctAnswers));
        UUID userId = this.getUserId(principal);
        Exam exam = this.examService.getExamById(examId);
        UserExam examsStats = this.userExamRepository.getUserExamByUserIdAndExamId(userId, exam.getId());
        if (examsStats == null) {
            examsStats = new UserExam(userId, exam, exam.getQuestions().size(), correctAnswers);
        } else {
            examsStats.setTotalQuestions(exam.getQuestions().size());
            examsStats.setCorrectAnswers(correctAnswers);
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
