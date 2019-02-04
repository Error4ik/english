package com.voronin.english.controller;

import com.voronin.english.domain.CardFilled;
import com.voronin.english.domain.Category;
import com.voronin.english.service.WordService;
import com.voronin.english.service.CategoryService;
import com.voronin.english.service.ExamService;
import com.voronin.english.service.QuestionService;
import com.voronin.english.service.PhraseForTrainingService;
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
 * AdminController test.
 *
 * @author Alexey Voronin.
 * @since 29.11.2018.
 */
@RunWith(SpringRunner.class)
@WebMvcTest(AdminController.class)
@WithMockUser(username = "user", roles = {"USER"})
public class AdminControllerTest {

    /**
     * Main entry point for server-side Spring MVC test support.
     *
     * @see MockMvc
     */
    @Autowired
    private MockMvc mockMvc;

    /**
     * Mock WordService.
     */
    @MockBean
    private WordService wordService;

    /**
     * Mock CategoryService.
     */
    @MockBean
    private CategoryService categoryService;

    /**
     * Mock CardFilled class.
     */
    @MockBean
    private CardFilled cardFilled;

    /**
     * Mock Category class.
     */
    @MockBean
    private Category category;

    /**
     * Mock ExamService.
     */
    @MockBean
    private ExamService examService;

    /**
     * Mock QuestionService.
     */
    @MockBean
    private QuestionService questionService;

    /**
     * Mock PhraseForTrainingService.
     */
    @MockBean
    private PhraseForTrainingService phraseForTrainingService;

    /**
     * When mapping '/admin/add-card' should call the prepareAndSave method of the WordService class once.
     *
     * @throws Exception exception.
     */
    @Test
    public void whenGetMappingAddCardShouldReturnStatusOkAndOneCallPrepareAndSaveMethod() throws Exception {
        this.mockMvc
                .perform(get("/admin/add-card").flashAttr("cardFilled", cardFilled))
                .andExpect(status().isOk());

        verify(this.wordService, times(1)).prepareAndSave(cardFilled, null);
    }

    /**
     * When mapping '/admin/add-category' should call the prepareAndSave method of the CategoryService class once.
     *
     * @throws Exception exception.
     */
    @Test
    public void whenGetMappingAddCategoryShouldReturnStatusOkAndOneCallPrepareAndSaveMethod() throws Exception {
        this.mockMvc
                .perform(get("/admin/add-category").flashAttr("category", category))
                .andExpect(status().isOk());

        verify(this.categoryService, times(1)).prepareAndSave(category, null);
    }

    /**
     * When mapping '/admin/add-question' should call the prepareAndSave method of the QuestionService class once.
     *
     * @throws Exception exception.
     */
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

    /**
     * When mapping '/admin/add-exam' should call the prepareAndSave method of the ExamService class once.
     *
     * @throws Exception exception.
     */
    @Test
    public void whenMappingAddExamShouldReturnStatusOkAndCallPrepareAndSaveMethod() throws Exception {
        String name = "name";
        String categoryName = "category";
        int examType = 0;
        this.mockMvc.perform(get("/admin/add-exam")
                .param("name", name)
                .param("category", categoryName)
                .param("type", String.valueOf(examType)))
                .andExpect(status().isOk());

        verify(this.examService, times(1)).prepareAndSave(name, categoryName, examType);
    }

    /**
     * When mapping '/admin/add-phrase-for-training' should call
     * the prepareAndSave method of the PhraseForTrainingService class once.
     *
     * @throws Exception exception.
     */
    @Test
    public void whenMappingAddPhraseFromTrainingShouldReturnStatusOkAndCallPrepareAndSaveMethodOneTime()
            throws Exception {
        String phrase = "phrase";
        String translate = "translate";
        String category = "category";
        this.mockMvc.perform(get("/admin/add-phrase-for-training")
                .param("phrase", phrase)
                .param("translate", translate)
                .param("category", category))
                .andExpect(status().isOk());
        verify(this.phraseForTrainingService, times(1))
                .prepareAndSave(phrase, translate, category);
    }
}
