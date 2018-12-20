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
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;

import java.security.Principal;
import java.util.UUID;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

/**
 * TODO: comment.
 *
 * @author Alexey Voronin.
 * @since 19.12.2018.
 */
@RunWith(SpringRunner.class)
@WebMvcTest(UserExamsStatsService.class)
@WithMockUser(username = "user", roles = {"USER"})
public class UserExamsStatsServiceTest {

    @Autowired
    private UserExamsStatsService userExamsStatsService;

    @MockBean
    private UserExamsStatsRepository userExamsStatsRepository;
    @MockBean
    private UserService userService;
    @MockBean
    private ExamService examService;

    private UUID uuid = UUID.randomUUID();

    @Test
    public void whenSaveShouldReturnSavedExamStatsWithoutNull() throws Exception {
        User user = new User();
        Exam exam = new Exam();
        exam.setQuestions(Lists.newArrayList(new Question()));
        Principal principal = new Principal() {
            @Override
            public String getName() {
                return "user";
            }
        };

        UserExamsStats examsStats = new UserExamsStats(user, exam, 1, 1);
        when(userService.getUserByEmail("user")).thenReturn(user);
        when(examService.getExamById(uuid)).thenReturn(exam);
        when(userExamsStatsRepository.getUserExamsStatsByUserAndExam(user, exam)).thenReturn(examsStats);
        when(userExamsStatsRepository.save(examsStats)).thenReturn(examsStats);

        assertThat(userExamsStatsService.save(principal, uuid, 1), is(examsStats));

    }

    @Test
    public void whenSaveShouldReturnSavedExamStatsWithNull() throws Exception {
        User user = new User();
        Exam exam = new Exam();
        exam.setQuestions(Lists.newArrayList(new Question()));
        Principal principal = new Principal() {
            @Override
            public String getName() {
                return "user";
            }
        };

        UserExamsStats examsStats = new UserExamsStats(user, exam, 1, 1);
        when(userService.getUserByEmail("user")).thenReturn(user);
        when(examService.getExamById(uuid)).thenReturn(exam);
        when(userExamsStatsRepository.getUserExamsStatsByUserAndExam(user, exam)).thenReturn(null);
        when(userExamsStatsRepository.save(any(UserExamsStats.class))).thenReturn(examsStats);

        assertThat(userExamsStatsService.save(principal, uuid, 1), is(examsStats));

    }

    @Test
    public void whenGetUserExamsStatsByUserShouldReturnUserStats() throws Exception {
        UserExamsStats examsStats = new UserExamsStats();
        when(userExamsStatsRepository.getUserExamsStatsByUser(any(User.class))).thenReturn(examsStats);

        assertThat(userExamsStatsService.getUserExamsStatsByUser(new User()), is(examsStats));
    }

}