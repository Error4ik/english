package com.voronin.english.controller;

import com.voronin.english.domain.User;
import com.voronin.english.service.ExamService;
import com.voronin.english.service.UserExamsStatsService;
import com.voronin.english.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.security.Principal;
import java.util.UUID;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * ExamController test class.
 *
 * @author Alexey Voronin.
 * @since 19.12.2018.
 */
@RunWith(SpringRunner.class)
@WebMvcTest(ExamController.class)
@WithMockUser(username = "user", roles = {"USER"})
public class ExamControllerTest {

    /**
     * Main entry point for server-side Spring MVC test support.
     *
     * @see MockMvc
     */
    @Autowired
    private MockMvc mockMvc;

    /**
     * Mock JavaMailSender.
     */
    @MockBean
    private JavaMailSender javaMailSender;

    /**
     * Mock ExamService.
     */
    @MockBean
    private ExamService examService;

    /**
     * Mock UserService.
     */
    @MockBean
    private UserService userService;

    /**
     * Mock UserExamsStatsService.
     */
    @MockBean
    private UserExamsStatsService userExamsStatsService;

    /**
     * Mock Principal.
     */
    @MockBean
    private Principal principal;

    /**
     * UUID id for test.
     */
    private UUID uuid = UUID.randomUUID();

    /**
     * When mapping '/exam/{id}' should call the getExamById method of the ExamService class once.
     *
     * @throws Exception exception.
     */
    @Test
    public void whenMappingExamByIdShouldReturnStatusOkAndCallGetExamByIdMethod() throws Exception {
        this.mockMvc.perform(get("/exam/{id}", uuid))
                .andExpect(status().isOk());

        verify(examService, times(1)).getExamById(uuid);
    }

    /**
     * When mapping '/exam/exams' should call the getExams method of the ExamService class once.
     *
     * @throws Exception exception.
     */
    @Test
    public void whenMappingExamsShouldReturnStatusOkAndCallGetExamsMethod() throws Exception {
        this.mockMvc.perform(get("/exam/exams"))
                .andExpect(status().isOk());

        verify(examService, times(1)).getExams();
    }

    /**
     * When mapping '/exam/exams' should call the getUserExamsStatsByUser
     * method of the UserExamsStatsService class once.
     *
     * @throws Exception exception.
     */
    @Test
    public void whenMappingGetStatsByUserShouldReturnStatusOkAndCallGetStatsByUserMethod() throws Exception {
        User user = new User();
        when(userService.getUserByEmail("user")).thenReturn(user);

        this.mockMvc.perform(get("/exam/exam-stats-by-user"))
                .andExpect(status().isOk());

        verify(userExamsStatsService, times(1)).getUserExamsStatsByUser(user);
    }

    /**
     * When mapping '/exam/save-stats-for-exam' should return status isOk.
     *
     * @throws Exception exception.
     */
    @Test
    public void whenMappingSaveStatsForExamShouldReturnStatusOk() throws Exception {
        int answer = 5;
        this.mockMvc.perform(get("/exam/save-stats-for-exam")
                .param("examId", uuid.toString())
                .param("correctAnswer", String.valueOf(answer)))
                .andExpect(status().isOk());
    }
}
