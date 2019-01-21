package com.voronin.english.service;

import com.google.common.collect.Lists;
import com.voronin.english.domain.PartOfSpeech;
import com.voronin.english.repository.PartOfSpeechRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.UUID;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

/**
 * TODO: comment.
 *
 * @author Alexey Voronin.
 * @since 30.11.2018.
 */
@RunWith(SpringRunner.class)
@WebMvcTest(PartOfSpeechService.class)
@WithMockUser(username = "user", roles = {"USER"})
public class PartOfSpeechServiceTest {

    @Autowired
    private PartOfSpeechService partOfSpeechService;

    @MockBean
    private PartOfSpeechRepository partOfSpeechRepository;

    private PartOfSpeech partOfSpeech = new PartOfSpeech();

    @Before
    public void init() {
        partOfSpeech.setPartOfSpeech("speech");
        partOfSpeech.setId(UUID.randomUUID());
    }

    @Test
    public void whenGetAllShouldReturnListPartOfSpeech() throws Exception {
        List<PartOfSpeech> list = Lists.newArrayList(partOfSpeech);
        when(partOfSpeechRepository.findAll()).thenReturn(list);

        assertThat(partOfSpeechService.getAll(), is(list));
    }

    @Test
    public void getPartOfSpeechByName() throws Exception {
        when(partOfSpeechRepository.getPartOfSpeechByPartOfSpeech(partOfSpeech.getPartOfSpeech()))
                .thenReturn(partOfSpeech);

        assertThat(partOfSpeechService.getPartOfSpeechByName(partOfSpeech.getPartOfSpeech()), is(partOfSpeech));
    }

    @Test
    public void whenGetByIdShouldReturnPartOfSpeechById() throws Exception {
        when(partOfSpeechRepository.getById(partOfSpeech.getId())).thenReturn(partOfSpeech);

        assertThat(partOfSpeechService.getById(partOfSpeech.getId()), is(partOfSpeech));
    }

    @Test
    public void whenSavePartOfSpeechShouldReturnPartOfSpeech() throws Exception {
        when(partOfSpeechRepository.save(partOfSpeech)).thenReturn(partOfSpeech);

        assertThat(partOfSpeechService.save(partOfSpeech), is(partOfSpeech));
    }

    @Test
    public void whenGetSpeechWithoutNounShouldReturnListSpeeches() throws Exception {
        PartOfSpeech noun = new PartOfSpeech("Существительное");
        when(partOfSpeechRepository.findAll())
                .thenReturn(Lists.newArrayList(noun, new PartOfSpeech("Прилагательное"), new PartOfSpeech("Союз")));
        when(partOfSpeechRepository.getPartOfSpeechByPartOfSpeech(noun.getPartOfSpeech())).thenReturn(noun);

        assertFalse(partOfSpeechService.getSpeechesWithoutNoun().contains(noun));
    }
}
