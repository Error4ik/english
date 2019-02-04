package com.voronin.english.controller;

import com.voronin.english.service.PhraseForTrainingService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;


/**
 * PhraseController test class.
 *
 * @author Alexey Voronin.
 * @since 04.02.2019.
 */
@RunWith(SpringRunner.class)
@WebMvcTest(PhraseController.class)
@WithMockUser(username = "user", roles = {"USER"})
public class PhraseControllerTest {

    /**
     * Main entry point for server-side Spring MVC test support.
     *
     * @see MockMvc
     */
    @Autowired
    private MockMvc mockMvc;

    /**
     * Mock PhraseForTrainingService.
     */
    @MockBean
    private PhraseForTrainingService phraseForTrainingService;

    /**
     * Id for test.
     */
    private final UUID id = UUID.randomUUID();

    /**
     * When mapping '/phrase/category/{id}' should return status ok and call getPhrasesByCategoryId method of the
     * PhraseForTrainingService class once.
     *
     * @throws Exception exception.
     */
    @Test
    public void whenMappingCategoryIdShouldReturnStatusOkAndCallGetPhrasesByCategoryIdMethod() throws Exception {
        this.mockMvc
                .perform(get("/phrase/category/{id}", id))
                .andExpect(status().isOk());

        verify(this.phraseForTrainingService, times(1)).getPhrasesByCategoryId(id);
    }
}