package com.voronin.sentence.service;

import com.voronin.sentence.domain.Exam;
import com.voronin.sentence.domain.UserExam;
import com.voronin.sentence.repository.UserExamRepository;
import org.assertj.core.util.Lists;
import org.junit.Before;
import org.junit.Test;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;


import java.util.List;
import java.util.UUID;
import java.util.LinkedHashMap;

import static org.mockito.ArgumentMatchers.any;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * UserExamService test.
 *
 * @author Alexey Voronin.
 * @since 11.10.2019.
 */
public class UserExamServiceTest {

    /**
     * UserExamRepository mock.
     */
    private final UserExamRepository userExamRepository = mock(UserExamRepository.class);

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
    private UserExam userExam;

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

        exam = new Exam("exam", Exam.ExamType.PAST);
        exam.setId(id);
        exam.setQuestions(Lists.emptyList());
        userExam = new UserExam(id, exam, exam.getQuestions().size(), correctAnswers);
    }

    /**
     * The class object under test.
     */
    private UserExamService userExamService = new UserExamService(userExamRepository, examService);

    /**
     * When call method getUserExamByUser should return List of UserExams.
     *
     * @throws Exception exception.
     */
    @Test
    public void whenGetUserExamByUserShouldReturnUserShouldReturnListOfUserExams() throws Exception {
        List<UserExam> userExams = Lists.newArrayList(userExam);

        when(oAuth2Authentication.getUserAuthentication()).thenReturn(token);
        when(this.userExamRepository.getUserExamByUserId(id)).thenReturn(userExams);

        assertThat(this.userExamService.getUserExamByUser(oAuth2Authentication), is(userExams));
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
        when(this.userExamRepository.getUserExamByUserIdAndExamId(id, id)).thenReturn(null);
        when(this.userExamRepository.save(any(UserExam.class))).thenReturn(userExam);

        assertThat(this.userExamService.save(oAuth2Authentication, id, correctAnswers), is(userExam));
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
        when(this.userExamRepository.getUserExamByUserIdAndExamId(id, id)).thenReturn(userExam);
        when(this.userExamRepository.save(any(UserExam.class))).thenReturn(userExam);

        assertThat(this.userExamService.save(oAuth2Authentication, id, correctAnswers), is(userExam));
    }
}
