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
 * PartOfSpeechController test class.
 *
 * @author Alexey Voronin.
 * @since 28.11.2018.
 */
@RunWith(SpringRunner.class)
@WebMvcTest(PartOfSpeechController.class)
@WithMockUser(username = "user", roles = {"USER"})
public class PartOfSpeechControllerTest {

    /**
     * Main entry point for server-side Spring MVC test support.
     *
     * @see MockMvc
     */
    @Autowired
    private MockMvc mockMvc;

    /**
     * Mock PartOfSpeechService.
     */
    @MockBean
    private PartOfSpeechService partOfSpeechService;

    /**
     * When mapping '/part-of-speech/parts-of-speech' should return status isOk
     * and call the getAll method of the PartOfSpeechService class once.
     *
     * @throws Exception exception.
     */
    @Test
    public void whenMappingPartsOfSpeechShouldReturnStatusOkAndOneCallGetAllMethod() throws Exception {
        this.mockMvc
                .perform(get("/part-of-speech/parts-of-speech"))
                .andExpect(status().isOk());
        verify(this.partOfSpeechService, times(1)).getAll();
    }

    /**
     * When mapping '/part-of-speech/part-of-speech-without-noun' should return status isOk
     * and call the getSpeechesWithoutNoun method of the PartOfSpeechService class once.
     *
     * @throws Exception exception.
     */
    @Test
    public void whenMappingPartOfSpeechWithoutNounReturnStatusOkAndCallGetSpeechesWithoutNun() throws Exception {
        this.mockMvc
                .perform(get("/part-of-speech/part-of-speech-without-noun"))
                .andExpect(status().isOk());
        verify(this.partOfSpeechService, times(1)).getSpeechesWithoutNoun();
    }
}
