package com.voronin.english.controller;

import com.voronin.english.service.NounService;
import com.voronin.english.service.WordService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

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
@WebMvcTest(WordController.class)
@WithMockUser(username = "user", roles = {"USER"})
public class WordControllerTest {

    /**
     * Main entry point for server-side Spring MVC test support.
     *
     * @see MockMvc
     */
    @Autowired
    private MockMvc mockMvc;

    /**
     * Mock JavaMailSender.
     */
    @MockBean
    private JavaMailSender javaMailSender;

    /**
     * Mock WordService.
     */
    @MockBean
    private WordService wordService;

    /**
     * Mock NounService.
     */
    @MockBean
    private NounService nounService;

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
     * When mapping '/word/words-by-category/{id}/{limit}/{page}'
     * should call the getWordsByCategory method of the WordService class once.
     *
     * @throws Exception exception.
     */
    @Test
    public void whenMappingWordsByCategoryShouldReturnStatusOkAndOneCallGetWordsByCategoryMethod() throws Exception {
        final int numberOfPage = 0;
        final int itemPerPage = 1;
        this.mockMvc
                .perform(get(
                        "/word/words-by-category/{id}/{limit}/{page}",
                        uuid,
                        itemPerPage,
                        numberOfPage))
                .andExpect(status().isOk());
        verify(this.nounService, times(1))
                .getNounsByCategoryId(
                        uuid,
                        new PageRequest(
                                numberOfPage,
                                itemPerPage,
                                Sort.Direction.ASC,
                                "word"));
        verify(this.nounService, times(1)).getNumberOfRecordsByCategoryId(uuid);
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
                        "/word/words-by-part-of-speech/{id}/{limit}/{page}",
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
     * When mapping '/word/words-by-category/{id}'
     * should call the getWordsByCategory method of the WordService class once.
     *
     * @throws Exception exception.
     */
    @Test
    public void whenMappingWordsByCategoryIdShouldReturnStatusOkAndCallGetWordsByCategoryIdMethod() throws Exception {
        this.mockMvc
                .perform(get(
                        "/word/words-by-category/{id}",
                        uuid
                ))
                .andExpect(status().isOk());
        verify(this.nounService, times(1)).getNounsByCategoryId(uuid);
    }
}
