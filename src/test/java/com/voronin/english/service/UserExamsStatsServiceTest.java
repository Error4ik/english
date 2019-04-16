package com.voronin.english.service;

import com.voronin.english.domain.Exam;
import com.voronin.english.domain.Question;
import com.voronin.english.domain.User;
import com.voronin.english.domain.UserExamsStats;
import com.voronin.english.repository.UserExamsStatsRepository;
import org.assertj.core.util.Lists;
import org.junit.Test;

import java.security.Principal;
import java.util.List;
import java.util.UUID;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;

/**
 * UserExamsStatsService test class.
 *
 * @author Alexey Voronin.
 * @since 19.12.2018.
 */
public class UserExamsStatsServiceTest {

    /**
     * Mock UserExamsStatsRepository.
     */
    private UserExamsStatsRepository userExamsStatsRepository = mock(UserExamsStatsRepository.class);

    /**
     * Mock UserService.
     */
    private UserService userService = mock(UserService.class);

    /**
     * Mock ExamService.
     */
    private ExamService examService = mock(ExamService.class);

    /**
     * The class object under test.
     */
    private UserExamsStatsService userExamsStatsService =
            new UserExamsStatsService(
                    userExamsStatsRepository,
                    userService,
                    examService);

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
        verify(userService, times(1)).getUserByEmail("user");
        verify(examService, times(1)).getExamById(uuid);
        verify(userExamsStatsRepository, times(1)).getUserExamsStatsByUserAndExam(user, exam);
        verify(userExamsStatsRepository, times(1)).save(examsStats);

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
        verify(userService, times(1)).getUserByEmail("user");
        verify(examService, times(1)).getExamById(uuid);
        verify(userExamsStatsRepository, times(1)).getUserExamsStatsByUserAndExam(user, exam);
        verify(userExamsStatsRepository, times(1)).save(any(UserExamsStats.class));

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
        verify(userExamsStatsRepository, times(1)).getUserExamsStatsByUser(any(User.class));
    }
}
