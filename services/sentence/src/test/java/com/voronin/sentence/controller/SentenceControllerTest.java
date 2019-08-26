package com.voronin.sentence.controller;

import com.voronin.sentence.service.SentenceService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * SentenceController test.
 *
 * @author Alexey Voronin.
 * @since 19.08.2019.
 */
@RunWith(SpringRunner.class)
@WebMvcTest(controllers = SentenceController.class, secure = false)
@AutoConfigureMockMvc
@WithMockUser
public class SentenceControllerTest {

    /**
     * Main entry point for server-side Spring MVC test support.
     *
     * @see MockMvc
     */
    @Autowired
    private MockMvc mockMvc;

    /**
     * Mock SentenceService.
     */
    @MockBean
    private SentenceService sentenceService;

    /**
     * When mapping /sentence/category/id/limit/page should return status ok
     * and call method getSentencesByCategoryId by SentenceService class once
     * and call method getNumberOfRecordsBySentenceCategoryId by SentenceService
     * class once.
     *
     * @throws Exception exception.
     */
    @Test
    public void whenCategoryIdLimitPageShouldReturnStatusOk() throws Exception {
        final int itemPerPage = 1;
        final int numberOfPage = 0;
        final UUID id = UUID.randomUUID();
        final PageRequest pageRequest = new PageRequest(
                numberOfPage,
                itemPerPage,
                Sort.Direction.ASC,
                "id"
        );
        this.mockMvc.perform(get(
                "/sentence/category/{id}/{limit}/{page}",
                id,
                itemPerPage,
                numberOfPage))
                .andExpect(status().isOk());

        verify(sentenceService, times(1)).getSentencesByCategoryId(id, pageRequest);
        verify(sentenceService, times(1)).getNumberOfRecordsBySentenceCategoryId(id);
    }

    /**
     * When mapping /sentence/add-sentence should return status isOk
     * and call method prepareAdnSave by SentenceService class once.
     *
     * @throws Exception exception.
     */
    @Test
    public void whenAddSentenceShouldReturnStatusOk() throws Exception {
        final String sentence = "sentence";
        final String translate = "translate";
        final String category = "category";
        this.mockMvc
                .perform(
                        get("/sentence/add-sentence")
                                .param("sentence", sentence)
                                .param("translate", translate)
                                .param("category", category))
                .andExpect(status().isOk());

        verify(this.sentenceService, times(1)).prepareAndSave(sentence, translate, category);
    }
}
