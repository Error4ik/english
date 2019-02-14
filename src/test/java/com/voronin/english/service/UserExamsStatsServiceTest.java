package com.voronin.english.service;

import com.voronin.english.domain.Exam;
import com.voronin.english.domain.Question;
import com.voronin.english.domain.User;
import com.voronin.english.domain.UserExamsStats;
import com.voronin.english.repository.UserExamsStatsRepository;
import org.assertj.core.util.Lists;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;

import java.security.Principal;
import java.util.List;
import java.util.UUID;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

/**
 * UserExamsStatsService test class.
 *
 * @author Alexey Voronin.
 * @since 19.12.2018.
 */
@RunWith(SpringRunner.class)
@WebMvcTest(UserExamsStatsService.class)
@WithMockUser(username = "user", roles = {"USER"})
public class UserExamsStatsServiceTest {

    /**
     * The class object under test.
     */
    @Autowired
    private UserExamsStatsService userExamsStatsService;

    /**
     * Mock JavaMailSender.
     */
    @MockBean
    private JavaMailSender javaMailSender;

    /**
     * Mock UserExamsStatsRepository.
     */
    @MockBean
    private UserExamsStatsRepository userExamsStatsRepository;

    /**
     * Mock UserService.
     */
    @MockBean
    private UserService userService;

    /**
     * Mock ExamService.
     */
    @MockBean
    private ExamService examService;

    /**
     * UUID id for test.
     */
    private UUID uuid = UUID.randomUUID();

    /**
     * When you call save, if the statistics already exist in the database.
     *
     * @throws Exception exception.
     */
    @Test
    public void whenSaveWithoutNullShouldReturnSavedExamStats() throws Exception {
        User user = new User();
        Exam exam = new Exam();
        exam.setQuestions(Lists.newArrayList(new Question()));
        Principal principal = () -> "user";

        UserExamsStats examsStats = new UserExamsStats(user, exam, 1, 1);
        when(userService.getUserByEmail("user")).thenReturn(user);
        when(examService.getExamById(uuid)).thenReturn(exam);
        when(userExamsStatsRepository.getUserExamsStatsByUserAndExam(user, exam)).thenReturn(examsStats);
        when(userExamsStatsRepository.save(examsStats)).thenReturn(examsStats);

        assertThat(userExamsStatsService.save(principal, uuid, 1), is(examsStats));

    }

    /**
     * When you call save, if the statistics do not exist in the database.
     *
     * @throws Exception exception.
     */
    @Test
    public void whenSaveWithNullShouldReturnSavedExamStats() throws Exception {
        User user = new User();
        Exam exam = new Exam();
        exam.setQuestions(Lists.newArrayList(new Question()));
        Principal principal = () -> "user";

        UserExamsStats examsStats = new UserExamsStats(user, exam, 1, 1);
        when(userService.getUserByEmail("user")).thenReturn(user);
        when(examService.getExamById(uuid)).thenReturn(exam);
        when(userExamsStatsRepository.getUserExamsStatsByUserAndExam(user, exam)).thenReturn(null);
        when(userExamsStatsRepository.save(any(UserExamsStats.class))).thenReturn(examsStats);

        assertThat(userExamsStatsService.save(principal, uuid, 1), is(examsStats));

    }

    /**
     * When call getUserExamsStatsByUser should return UserExamsStats.
     *
     * @throws Exception exception.
     */
    @Test
    public void whenGetUserExamsStatsByUserShouldReturnUserStats() throws Exception {
        UserExamsStats examsStats = new UserExamsStats();
        List<UserExamsStats> list = Lists.newArrayList(examsStats);
        when(userExamsStatsRepository.getUserExamsStatsByUser(any(User.class))).thenReturn(list);

        assertThat(userExamsStatsService.getUserExamsStatsByUser(new User()), is(list));
    }
}
