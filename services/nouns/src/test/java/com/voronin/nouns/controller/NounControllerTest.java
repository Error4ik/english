package com.voronin.nouns.controller;

import com.voronin.nouns.domain.CardFilled;
import com.voronin.nouns.service.NounService;
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

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * NounController test class.
 *
 * @author Alexey Voronin.
 * @since 23.08.2019.
 */
@RunWith(SpringRunner.class)
@WebMvcTest(controllers = NounController.class, secure = false)
@AutoConfigureMockMvc
@WithMockUser
public class NounControllerTest {

    /**
     * Main entry point for server-side Spring MVC test support.
     *
     * @see MockMvc
     */
    @Autowired
    private MockMvc mockMvc;

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
     * When mapping '/noun/nouns' should call the getNouns method of the NounService class once.
     *
     * @throws Exception exception.
     */
    @Test
    public void whenMappingWordsShouldReturnStatusOkAndOneCallGetWordsMethod() throws Exception {
        this.mockMvc
                .perform(get("/noun/nouns"))
                .andExpect(status().isOk());
        verify(this.nounService, times(1)).getNouns();
    }

    /**
     * When mapping '/noun/nouns/by/category/{id}/{limit}/{page}'
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
                        "/noun/nouns/by/category/{id}/{limit}/{page}",
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
                                "noun"));
        verify(this.nounService, times(1)).getNumberOfRecordsByCategoryId(uuid);
    }


    /**
     * When mapping '/noun/nouns/by/category/{id}'
     * should call the getNounsByCategoryId method of the NounService class once.
     *
     * @throws Exception exception.
     */
    @Test
    public void whenMappingWordsByCategoryIdShouldReturnStatusOkAndCallGetWordsByCategoryIdMethod() throws Exception {
        this.mockMvc
                .perform(get(
                        "/noun/nouns/by/category/{id}",
                        uuid
                ))
                .andExpect(status().isOk());
        verify(this.nounService, times(1)).getNounsByCategoryId(uuid);
    }

    /**
     * WHen mapping /noun/add-noun should return status isOk
     * and call method prepareAndSave by NounService class once.
     *
     * @throws Exception exception.
     */
    @Test
    public void whenMappingAddNounShouldReturnStatusOk() throws Exception {
        final CardFilled cardFilled = new CardFilled();

        this.mockMvc.perform(get("/noun/add-noun").flashAttr("cardFilled", cardFilled))
                .andExpect(status().isOk());

        verify(this.nounService, times(1)).prepareAndSave(cardFilled, null);
    }

    /**
     * When mapping /noun/edit-noun should return status isOk
     * and call method editNounAndSave by NounService class once.
     *
     * @throws Exception exception.
     */
    @Test
    public void whenMappingEditNounShouldReturnStatusOk() throws Exception {
        final CardFilled cardFilled = mock(CardFilled.class);
        final String nounId = UUID.randomUUID().toString();

        this.mockMvc.perform(
                get("/noun/edit-noun")
                        .flashAttr("cardFilled", cardFilled)
                        .param("nounId", nounId)
        ).andExpect(status().isOk());

        verify(this.nounService, times(1)).editNounAndSave(cardFilled, null, nounId);
    }

    /**
     * When mapping /noun/delete-noun should return status isOk
     * and call method deleteNoun by NounService class once.
     *
     * @throws Exception exception.
     */
    @Test
    public void whenMappingDeleteNounShouldReturnStatusOk() throws Exception {
        final UUID nounId = UUID.randomUUID();

        this.mockMvc.perform(get(
                "/noun/delete-noun")
                .param("id", nounId.toString())
        ).andExpect(status().isOk());

        verify(this.nounService, times(1)).deleteNoun(nounId);
    }
}