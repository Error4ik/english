package com.voronin.words.controller;

import com.voronin.words.service.ExamService;
import com.voronin.words.service.QuestionService;
import com.voronin.words.service.UserExamService;
import org.assertj.core.util.Lists;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.security.Principal;
import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.anyInt;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * ExamController test class.
 *
 * @author Alexey Voronin.
 * @since 06.09.2019.
 */
@RunWith(SpringRunner.class)
@WebMvcTest(controllers = ExamController.class, secure = false)
@AutoConfigureMockMvc
@WithMockUser
public class ExamControllerTest {

    /**
     * Main entry point for server-side Spring MVC test support.
     *
     * @see MockMvc
     */
    @Autowired
    private MockMvc mockMvc;

    /**
     * Mock ExamService class.
     */
    @MockBean
    private ExamService examService;

    /**
     * Mock UserExamStatsService class.
     */
    @MockBean
    private UserExamService userExamService;

    /**
     * Mock QuestionService class.
     */
    @MockBean
    private QuestionService questionService;

    /**
     * When mapping /exam/add-exam should return status isOk
     * and call method prepareAndSave by ExamService class once.
     *
     * @throws Exception exception.
     */
    @Test
    public void whenMappingAddExamShouldReturnStatusOk() throws Exception {
        final String name = "name";
        final String speech = "speech";
        final int type = 0;

        this.mockMvc.perform(
                get("/exam/add-exam")
                        .param("name", name)
                        .param("speech", speech)
                        .param("type", String.valueOf(type)))
                .andExpect(status().isOk());

        verify(examService, times(1)).prepareAndSave(name, speech, type);
    }

    /**
     * When mapping /exam/{id} should return status isOk and
     * call method getExamById by ExamService class once.
     *
     * @throws Exception exception.
     */
    @Test
    public void whenMappingExamIdShouldReturnStatusOk() throws Exception {
        final UUID uuid = UUID.randomUUID();

        this.mockMvc.perform(get("/exam/{id}", uuid)).andExpect(status().isOk());

        verify(examService, times(1)).getExamById(uuid);
    }

    /**
     * When mapping /exam/exams should return status isOk and
     * call method getExams by ExamService class once.
     *
     * @throws Exception exception.
     */
    @Test
    public void whenMappingExamExamsShouldReturnStatusOk() throws Exception {
        this.mockMvc.perform(get("/exam/exams")).andExpect(status().isOk());

        verify(examService, times(1)).getExams();
    }

    /**
     * When mapping /exam/add-question should return status idOk and
     * call method prepareAndSave by QuestionService class once.
     *
     * @throws Exception exception.
     */
    @Test
    public void whenMappingExamAddQuestionShouldReturnStatusOk() throws Exception {
        final String exam = "exam";
        final String word = "word";
        final List<String> variants = Lists.newArrayList("word");
        this.mockMvc.perform(get("/exam/add-question")
                .param("exam", exam)
                .param("word", word)
                .param("variants", "word"))
                .andExpect(status().isOk());

        verify(questionService, times(1)).prepareAndSave(exam, word, variants);
    }

    /**
     * When mapping '/exam/save-stats-for-exam' should return status isOk.
     * and call method save by UserExamService class once.
     *
     * @throws Exception exception.
     */
    @Test
    public void whenMappingSaveStatsForExamShouldReturnStatusOk() throws Exception {
        final UUID uuid = UUID.randomUUID();
        final int answer = 5;

        this.mockMvc.perform(get("/exam/save-stats-for-exam")
                .param("examId", uuid.toString())
                .param("correctAnswer", String.valueOf(answer)))
                .andExpect(status().isOk());

        verify(userExamService, times(1)).save(any(Principal.class), any(UUID.class), anyInt());
    }

    /**
     * When mapping '/exam/exams' should call the getUserExamByUser
     * method of the UserExamService class once.
     *
     * @throws Exception exception.
     */
    @Test
    public void whenMappingGetStatsByUserShouldReturnStatusOkAndCallGetStatsByUserMethod() throws Exception {
        this.mockMvc.perform(get("/exam/exam-stats-by-user"))
                .andExpect(status().isOk());

        verify(userExamService, times(1)).getUserExamByUser(any(Principal.class));
    }
}
