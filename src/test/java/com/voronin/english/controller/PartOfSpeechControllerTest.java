package com.voronin.english.controller;

import com.voronin.english.service.PartOfSpeechService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
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
 * TODO: comment.
 *
 * @author Alexey Voronin.
 * @since 28.11.2018.
 */
@RunWith(SpringRunner.class)
@WebMvcTest(PartOfSpeechController.class)
@WithMockUser(username = "user", roles = {"USER"})
public class PartOfSpeechControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PartOfSpeechService partOfSpeechService;

    @Test
    public void whenMappingPartsOfSpeechShouldReturnStatusOkAndOneCallGetAllMethod() throws Exception {
        this.mockMvc
                .perform(get("/part-of-speech/parts-of-speech"))
                .andExpect(status().isOk());
        verify(this.partOfSpeechService, times(1)).getAll();
    }

    @Test
    public void whenMappingPartOfSpeechWithoutNounReturnStatusOkAndCallGetSpeechesWithoutNun() throws Exception {
        this.mockMvc
                .perform(get("/part-of-speech/part-of-speech-without-noun"))
                .andExpect(status().isOk());
        verify(this.partOfSpeechService, times(1)).getSpeechesWithoutNoun();
    }
}
