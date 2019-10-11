package com.voronin.sentence.controller;

import com.voronin.sentence.domain.KeyWord;
import com.voronin.sentence.service.KeyWordService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * KeyWordController test.
 *
 * @author Alexey Voronin.
 * @since 11.10.2019.
 */
@RunWith(SpringRunner.class)
@WebMvcTest(controllers = KeyWordController.class, secure = false)
@AutoConfigureMockMvc
@WithMockUser
public class KeyWordControllerTest {

    /**
     * Main entry point for server-side Spring MVC test support.
     *
     * @see MockMvc
     */
    @Autowired
    private MockMvc mockMvc;

    /**
     * KeyWordService.
     */
    @MockBean
    private KeyWordService keyWordService;

    /**
     * When mapping '/key-word/add' should return status isOk
     * and call method save once.
     *
     * @throws Exception exception.
     */
    @Test
    public void whenMappingAddShouldReturnStatusOkAndCallMethodSaveOnce() throws Exception {
        final KeyWord keyWord = new KeyWord();

        mockMvc.perform(get("/key-word/add")
                .flashAttr("keyWord", keyWord)
        ).andExpect(status().isOk());

        verify(this.keyWordService, times(1)).save(keyWord);
    }

    /**
     * When mapping '/key-word/words' should return status isOk
     * and call method getKeyWords once.
     *
     * @throws Exception exception.
     */
    @Test
    public void whenMappingWordsShouldReturnStatusOkAndCallMethodGetKeyWordsOnce() throws Exception {
        mockMvc.perform(get("/key-word/words")).andExpect(status().isOk());

        verify(this.keyWordService, times(1)).getKeyWords();
    }
}
