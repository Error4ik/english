package com.voronin.words.controller;

import com.voronin.words.domain.CardFilled;
import com.voronin.words.service.WordService;
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

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * WordController test class.
 *
 * @author Alexey Voronin.
 * @since 28.11.2018.
 */
@RunWith(SpringRunner.class)
@WebMvcTest(controllers = WordController.class, secure = false)
@AutoConfigureMockMvc
@WithMockUser
public class WordControllerTest {

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
     * UUID id for test.
     */
    private UUID uuid = UUID.randomUUID();

    /**
     * When mapping '/word/words' should call the getWords method of the WordService class once.
     *
     * @throws Exception exception.
     */
    @Test
    public void whenMappingWordsShouldReturnStatusOkAndOneCallGetWordsMethod() throws Exception {
        this.mockMvc
                .perform(get("/word/words"))
                .andExpect(status().isOk());
        verify(this.wordService, times(1)).getWords();
    }

    /**
     * When mapping '/word/words-by-part-of-speech/{id}/{limit}/{page}'
     * should call the getWordsByPartOfSpeech method of the WordService class once.
     *
     * @throws Exception exception.
     */
    @Test
    public void whenMappingWordsByPartOfSpeechShouldReturnStatusOkAndOneCallGetWordsByPartOfSpeech() throws Exception {
        final int numberOfPage = 0;
        final int itemPerPage = 1;
        this.mockMvc
                .perform(get(
                        "/word/words/by/part-of-speech/{id}/{limit}/{page}",
                        uuid,
                        itemPerPage,
                        numberOfPage))
                .andExpect(status().isOk());
        verify(this.wordService, times(1))
                .getWordsByPartOfSpeechId(
                        uuid,
                        new PageRequest(
                                numberOfPage,
                                itemPerPage,
                                Sort.Direction.ASC,
                                "word"));

        verify(this.wordService, times(1)).getNumberOfRecordsByPartOfSpeechId(uuid);
    }

    /**
     * WHen mapping /word/add-word should return status isOk
     * and call method prepareAndSave by WordService class once.
     *
     * @throws Exception exception.
     */
    @Test
    public void whenMappingAddWordShouldReturnStatusOk() throws Exception {
        final CardFilled cardFilled = new CardFilled();

        this.mockMvc.perform(get("/word/add-word").flashAttr("cardFilled", cardFilled))
                .andExpect(status().isOk());

        verify(this.wordService, times(1)).prepareAndSave(cardFilled);
    }

    /**
     * When mapping /word/edit-word should return status isOk
     * and call method editNounAndSave by NounService class once.
     *
     * @throws Exception exception.
     */
    @Test
    public void whenMappingEditWordShouldReturnStatusOk() throws Exception {
        final CardFilled cardFilled = mock(CardFilled.class);
        final String wordId = UUID.randomUUID().toString();

        this.mockMvc.perform(
                get("/word/edit-word")
                        .flashAttr("cardFilled", cardFilled)
                        .param("wordId", wordId)
        ).andExpect(status().isOk());

        verify(this.wordService, times(1)).editWordAndSave(cardFilled, wordId);
    }

    /**
     * When mapping /word/delete-word should return status isOk
     * and call method deleteNoun by NounService class once.
     *
     * @throws Exception exception.
     */
    @Test
    public void whenMappingDeleteWordShouldReturnStatusOk() throws Exception {
        final UUID wordId = UUID.randomUUID();

        this.mockMvc.perform(get(
                "/word/delete-word")
                .param("id", wordId.toString())
        ).andExpect(status().isOk());

        verify(this.wordService, times(1)).deleteWord(wordId);
    }

    /**
     * When mapping '/word/words-by-part-of-speech/{id}'
     * should call the getWordsByPartOfSpeechId method of the WordService class once.
     *
     * @throws Exception exception.
     */
    @Test
    public void whenMappingWordsByPartOfSpeechIdShouldReturnStatusOk() throws Exception {
        this.mockMvc
                .perform(get("/word/words/by/part-of-speech/{id}/", uuid))
                .andExpect(status().isOk());
        verify(this.wordService, times(1)).getWordsByPartOfSpeechId(uuid);
    }
}
