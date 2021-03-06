package com.voronin.nouns.service;

import com.voronin.nouns.domain.Category;
import com.voronin.nouns.domain.Exam;
import com.voronin.nouns.domain.UserExamsStats;
import com.voronin.nouns.repository.UserExamsStatsRepository;
import org.assertj.core.util.Lists;
import org.junit.Before;
import org.junit.Test;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.UUID;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * UserExamsStats test.
 *
 * @author Alexey Voronin.
 * @since 03.09.2019.
 */
public class UserExamsStatsServiceTest {

    /**
     * UserExamRepository mock.
     */
    private final UserExamsStatsRepository userExamsStatsRepository = mock(UserExamsStatsRepository.class);

    /**
     * ExamService mock.
     */
    private final ExamService examService = mock(ExamService.class);

    /**
     * OAuth2Authentication mock.
     */
    private final OAuth2Authentication oAuth2Authentication = mock(OAuth2Authentication.class);

    /**
     * UUID id for test.
     */
    private final UUID id = UUID.randomUUID();

    /**
     * Exam for test.
     */
    private Exam exam;

    /**
     * Correct answers for exam.
     */
    private final int correctAnswers = 5;

    /**
     * UserExam for test.
     */
    private UserExamsStats userExamsStats;

    /**
     * Token for test.
     */
    private UsernamePasswordAuthenticationToken token;

    /**
     * Created token with tests.
     */
    @Before
    public void init() {
        token = new UsernamePasswordAuthenticationToken(null, null, null);
        LinkedHashMap<String, Object> linkedHashMap = new LinkedHashMap<>();
        LinkedHashMap<String, Object> principal = new LinkedHashMap<>();
        principal.put("id", id.toString());
        linkedHashMap.put("principal", principal);
        token.setDetails(linkedHashMap);

        exam = new Exam("exam", new Category(), 1);
        exam.setId(id);
        exam.setQuestions(Lists.emptyList());
        userExamsStats = new UserExamsStats(id, exam, exam.getQuestions().size(), correctAnswers);
    }

    /**
     * The class object under test.
     */
    private UserExamsStatsService userExamService = new UserExamsStatsService(userExamsStatsRepository, examService);

    /**
     * When call method getUserExamByUser should return List of UserExams.
     *
     * @throws Exception exception.
     */
    @Test
    public void whenGetUserExamByUserShouldReturnUserShouldReturnListOfUserExams() throws Exception {
        List<UserExamsStats> userExams = Lists.newArrayList(userExamsStats);

        when(oAuth2Authentication.getUserAuthentication()).thenReturn(token);
        when(this.userExamsStatsRepository.getUserExamsStatsByUserId(id)).thenReturn(userExams);

        assertThat(this.userExamService.getUserExamsStatsByUser(oAuth2Authentication), is(userExams));
    }

    /**
     * When call method save with first User Exam should return saved User Exam.
     *
     * @throws Exception exception.
     */
    @Test
    public void whenSaveWithFirstUserExamThenReturnSavedUserExam() throws Exception {
        when(oAuth2Authentication.getUserAuthentication()).thenReturn(token);
        when(this.examService.getExamById(id)).thenReturn(exam);
        when(this.userExamsStatsRepository.getUserExamsStatsByUserIdAndExam(id, exam)).thenReturn(null);
        when(this.userExamsStatsRepository.save(any(UserExamsStats.class))).thenReturn(userExamsStats);

        assertThat(this.userExamService.save(oAuth2Authentication, id, correctAnswers), is(userExamsStats));
    }

    /**
     * When call method save with UserExam not null
     * should return saved UserExam.
     *
     * @throws Exception exception.
     */
    @Test
    public void whenSaveWithUserExamNotNullShouldReturnSavedUserExam() throws Exception {
        when(oAuth2Authentication.getUserAuthentication()).thenReturn(token);
        when(this.examService.getExamById(id)).thenReturn(exam);
        when(this.userExamsStatsRepository.getUserExamsStatsByUserIdAndExam(id, exam)).thenReturn(userExamsStats);
        when(this.userExamsStatsRepository.save(any(UserExamsStats.class))).thenReturn(userExamsStats);

        assertThat(this.userExamService.save(oAuth2Authentication, id, correctAnswers), is(userExamsStats));
    }
}
