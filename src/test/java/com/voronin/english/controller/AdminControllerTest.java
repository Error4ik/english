package com.voronin.english.controller;

import com.voronin.english.domain.CardFilled;
import com.voronin.english.domain.Category;
import com.voronin.english.service.CategoryService;
import com.voronin.english.service.ExamService;
import com.voronin.english.service.QuestionService;
import com.voronin.english.service.WordService;
import org.assertj.core.util.Lists;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * TODO: comment.
 *
 * @author Alexey Voronin.
 * @since 29.11.2018.
 */
@RunWith(SpringRunner.class)
@WebMvcTest(AdminController.class)
@WithMockUser(username = "user", roles = {"USER"})
public class AdminControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private WordService wordService;

    @MockBean
    private CategoryService categoryService;

    @MockBean
    private CardFilled cardFilled;
    @MockBean
    private Category category;
    @MockBean
    private ExamService examService;
    @MockBean
    private QuestionService questionService;

    @Test
    public void whenGetMappingAddCardShouldReturnStatusOkAndOneCallPrepareAndSaveMethod() throws Exception {
        this.mockMvc
                .perform(get("/admin/add-card").flashAttr("cardFilled", cardFilled))
                .andExpect(status().isOk());

        verify(this.wordService, times(1)).prepareAndSave(cardFilled, null);
    }

    @Test
    public void whenGetMappingAddCategoryShouldReturnStatusOkAndOneCallPrepareAndSaveMethod() throws Exception {
        this.mockMvc
                .perform(get("/admin/add-category").flashAttr("category", category))
                .andExpect(status().isOk());

        verify(this.categoryService, times(1)).prepareAndSave(category, null);
    }

    @Test
    public void whenGetMappingAddQuestionShouldReturnStatusOkAndCallPrepareAndSaveMethod() throws Exception {
        String exam = "exam";
        String word = "word";
        List<String> list = Lists.newArrayList("word", "word", "word");
        this.mockMvc.perform(get("/admin/add-question")
                .param("exam", exam)
                .param("word", word)
                .param("variants", "word")
                .param("variants", "word")
                .param("variants", "word"))
                .andExpect(status().isOk());

        verify(this.questionService, times(1)).prepareAndSave(exam, word, list);
    }

    @Test
    public void whenMappingAddExamShouldReturnStatusOkAndCallPrepareAndSaveMethod() throws Exception {
        String name = "name";
        String category = "category";
        this.mockMvc.perform(get("/admin/add-exam")
                .param("name", name)
                .param("category", category))
                .andExpect(status().isOk());

        verify(this.examService, times(1)).prepareAndSave(name, category);
    }
}
