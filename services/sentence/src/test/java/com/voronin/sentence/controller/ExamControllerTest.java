package com.voronin.sentence.controller;

import com.voronin.sentence.service.ExamService;
import com.voronin.sentence.service.QuestionService;
import com.voronin.sentence.service.UserExamService;
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

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * ExamController test.
 *
 * @author Alexey Voronin.
 * @since 11.10.2019.
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
     * ExamService.
     */
    @MockBean
    private ExamService examService;

    /**
     * QuestionService.
     */
    @MockBean
    private QuestionService questionService;

    /**
     * UserExamService.
     */
    @MockBean
    private UserExamService userExamService;

    /**
     * When mapping '/exam/exams' should return status isOk
     * and call method getExams once.
     *
     * @throws Exception exception.
     */
    @Test
    public void whenMappingExamsShouldReturnStatusOkAndCallMethodGetExamsOnce() throws Exception {
        mockMvc.perform(get("/exam/exams")).andExpect(status().isOk());

        verify(this.examService, times(1)).getExams();
    }

    /**
     * When mapping '/exam/add-exam' should return status isOk
     * and call method save once.
     *
     * @throws Exception exception.
     */
    @Test
    public void whenMappingAddExamShouldReturnStatusOkAndCallMethodSaveOnce() throws Exception {
        final String examName = "examName";
        final String examType = "examType";
        mockMvc.perform(get("/exam/add-exam")
                .param("examName", examName)
                .param("examType", examType)
        ).andExpect(status().isOk());

        verify(this.examService, times(1)).save(examName, examType);
    }

    /**
     * When mapping '/exam/add-question' shoudl return status isOk
     * and call method save once.
     *
     * @throws Exception exception.
     */
    @Test
    public void whenMappingAddQuestionShouldReturnStatusOkAndCallMethodSAveOnce() throws Exception {
        final String question = "question";
        final String answer = "answer";
        final List<String> keyWords = Lists.newArrayList("test");
        final String examId = UUID.randomUUID().toString();
        mockMvc.perform(get("/exam/add-question")
                .param("question", question)
                .param("answer", answer)
                .param("keyWords", "test")
                .param("examId", examId)
        ).andExpect(status().isOk());

        verify(this.questionService, times(1)).save(question, answer, keyWords, examId);
    }

    /**
     * When mapping '/exam/{id}' should return status isOk
     * and call method getExamById once.
     *
     * @throws Exception exception.
     */
    @Test
    public void whenMappingIdShouldReturnStatusOkAndCallMethodGetExamByIdOnce() throws Exception {
        final UUID id = UUID.randomUUID();

        mockMvc.perform(get("/exam/{id}", id)).andExpect(status().isOk());

        verify(this.examService, times(1)).getExamById(id);
    }

    /**
     * When mapping '/exam/save-stats-for-exam' should return status isOk
     * and call method save once.
     *
     * @throws Exception exception.
     */
    @Test
    public void whenMappingSaveStatsForExamShouldReturnStatusOkAndCallMethodSaveOnce() throws Exception {
        final UUID examId = UUID.randomUUID();
        final int correctAnswers = 5;

        mockMvc.perform(get("/exam/save-stats-for-exam")
                .param("examId", examId.toString())
                .param("correctAnswers", String.valueOf(correctAnswers))
        ).andExpect(status().isOk());

        verify(this.userExamService, times(1)).save(any(Principal.class), any(UUID.class), anyInt());
    }

    /**
     * Whe mapping '/exam/exam-stats-by-user' should return status isOk
     * and call method getUserExamByUser once.
     *
     * @throws Exception exception.
     */
    @Test
    public void whenMappingExamStatsByUserShouldReturnSTatusOkAndCallMethodGetUserExamByUserOnce() throws Exception {
        mockMvc.perform(get("/exam/exam-stats-by-user")).andExpect(status().isOk());

        verify(this.userExamService, times(1)).getUserExamByUser(any(Principal.class));
    }
}
