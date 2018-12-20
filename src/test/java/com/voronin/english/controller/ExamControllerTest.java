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
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * TODO: comment.
 *
 * @author Alexey Voronin.
 * @since 19.12.2018.
 */
@RunWith(SpringRunner.class)
@WebMvcTest(ExamController.class)
@WithMockUser(username = "user", roles = {"USER"})
public class ExamControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ExamService examService;

    @MockBean
    private UserService userService;

    @MockBean
    private UserExamsStatsService userExamsStatsService;

    private UUID uuid = UUID.randomUUID();

    @Test
    public void whenMappingExamByIdShouldReturnStatusOkAndCallGetExamByIdMethod() throws Exception {
        this.mockMvc.perform(get("/exam/{id}", uuid))
                .andExpect(status().isOk());

        verify(examService, times(1)).getExamById(uuid);
    }

    @Test
    public void whenMappingExamsShouldReturnStatusOkAndCallGetExamsMethod() throws Exception {
        this.mockMvc.perform(get("/exam/exams"))
                .andExpect(status().isOk());

        verify(examService, times(1)).getExams();
    }

    @Test
    public void whenMappingGetStatsByUserShouldReturnStatusOkAndCallGetStatsByUserMethod() throws Exception {
        User user = new User();
        when(userService.getUserByEmail("user")).thenReturn(user);

        this.mockMvc.perform(get("/exam/exam-stats-by-user"))
                .andExpect(status().isOk());

        verify(userExamsStatsService, times(1)).getUserExamsStatsByUser(user);
    }

    @Test
    public void whenMappingSaveStatsForExamShouldReturnStatusOk() throws Exception {
        int answer = 5;
        this.mockMvc.perform(get("/exam/save-stats-for-exam")
                .param("examId", uuid.toString())
                .param("correctAnswer", String.valueOf(answer)))
                .andExpect(status().isOk());
    }
}
