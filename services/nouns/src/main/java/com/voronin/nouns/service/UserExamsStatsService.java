package com.voronin.nouns.service;

import com.google.gson.Gson;
import com.voronin.nouns.domain.Exam;
import com.voronin.nouns.domain.UserExamsStats;
import com.voronin.nouns.repository.UserExamsStatsRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.security.Principal;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * User exam stats service.
 *
 * @author Alexey Voronin.
 * @since 30.08.2019.
 */
@Service
public class UserExamsStatsService {

    /**
     * Logger.
     */
    private final Logger logger = LoggerFactory.getLogger(UserExamsStatsService.class);

    /**
     * User exam stats repository.
     */
    private final UserExamsStatsRepository userExamsStatsRepository;

    /**
     * RestTemplate.
     */
    @Autowired
    private RestTemplate restTemplate;

    /**
     * Exam service.
     */
    private final ExamService examService;

    /**
     * User service url.
     */
    @Value("${user.url}")
    private String userUrl;

    /**
     * class for using Gson.
     */
    @Autowired
    private Gson gson;

    /**
     * Constructor.
     *
     * @param userExamsStatsRepository User exam stats repository.
     * @param examService              exam service.
     */
    @Autowired
    public UserExamsStatsService(
            final UserExamsStatsRepository userExamsStatsRepository,
            final ExamService examService) {
        this.userExamsStatsRepository = userExamsStatsRepository;
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
    public UserExamsStats save(final Principal principal, final UUID examId, final int correctAnswer) {
        logger.debug(String.format("Arguments - examId - %s, correctAnswer - %s", examId, correctAnswer));
        UUID userId = this.getUserId(principal);
        Exam exam = this.examService.getExamById(examId);
        UserExamsStats examsStats = this.userExamsStatsRepository.getUserExamsStatsByUserIdAndExam(userId, exam);
        if (examsStats == null) {
            examsStats = new UserExamsStats(userId, exam, exam.getQuestions().size(), correctAnswer);
        } else {
            examsStats.setTotalQuestions(exam.getQuestions().size());
            examsStats.setCorrectAnswer(correctAnswer);
            examsStats.setDate(new Timestamp(System.currentTimeMillis()));
        }
        examsStats = this.userExamsStatsRepository.save(examsStats);
        logger.debug(String.format("Return - %s", examsStats));
        return examsStats;
    }

    /**
     * Get list UserExamStats by user.
     *
     * @return List of UserExamsStats.
     */
    public List<UserExamsStats> getUserExamsStatsByUser(final Principal principal) {
        UUID userId = getUserId(principal);
        return this.userExamsStatsRepository.getUserExamsStatsByUserId(userId);
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
