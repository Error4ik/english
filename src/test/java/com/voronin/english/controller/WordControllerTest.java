package com.voronin.english.controller;

import com.voronin.english.service.WordService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * TODO: comment.
 *
 * @author Alexey Voronin.
 * @since 28.11.2018.
 */
@RunWith(SpringRunner.class)
@WebMvcTest(WordController.class)
@WithMockUser(username = "user", roles = {"USER"})
public class WordControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private WordService wordService;

    private UUID uuid = UUID.randomUUID();

    @Test
    public void whenMappingWordsShouldReturnStatusOkAndOneCallGetWordsMethod() throws Exception {
        this.mockMvc
                .perform(get("/word/words"))
                .andExpect(status().isOk());
        verify(this.wordService, times(1)).getWords();
    }

    @Test
    public void whenMappingWordsByCategoryShouldReturnStatusOkAndOneCallGetWordsByCategoryMethod() throws Exception {
        this.mockMvc
                .perform(get("/word/words-by-category/{id}", uuid))
                .andExpect(status().isOk());
        verify(this.wordService, times(1)).getWordsByCategory(uuid);
    }
}
