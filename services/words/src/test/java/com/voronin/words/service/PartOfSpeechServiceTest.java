package com.voronin.words.service;

import com.google.common.collect.Lists;
import com.voronin.words.domain.PartOfSpeech;
import com.voronin.words.repository.PartOfSpeechRepository;
import org.junit.Before;
import org.junit.Test;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;
import java.util.UUID;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.*;

/**
 * PartOfSpeechService test class.
 *
 * @author Alexey Voronin.
 * @since 30.11.2018.
 */
public class PartOfSpeechServiceTest {

    /**
     * Mock PartOfSpeechRepository.
     */
    @MockBean
    private PartOfSpeechRepository partOfSpeechRepository = mock(PartOfSpeechRepository.class);

    /**
     * The class object under test.
     */
    private PartOfSpeechService partOfSpeechService = new PartOfSpeechService(partOfSpeechRepository);

    /**
     * Class for test.
     */
    private PartOfSpeech partOfSpeech = new PartOfSpeech();

    /**
     * initialization of objects for the tests.
     */
    @Before
    public void init() {
        partOfSpeech.setPartOfSpeech("speech");
        partOfSpeech.setId(UUID.randomUUID());
    }

    /**
     * When call findAll should return list of PartOfSpeech.
     *
     * @throws Exception exception.
     */
    @Test
    public void whenGetAllShouldReturnListPartOfSpeech() throws Exception {
        List<PartOfSpeech> list = Lists.newArrayList(partOfSpeech);
        when(partOfSpeechRepository.findAll()).thenReturn(list);

        assertThat(partOfSpeechService.findAll(), is(list));
        verify(partOfSpeechRepository, times(1)).findAll();
    }

    /**
     * When call partOfSpeechByName should return PartOfSpeech.
     *
     * @throws Exception exception.
     */
    @Test
    public void getPartOfSpeechByName() throws Exception {
        when(partOfSpeechRepository.getPartOfSpeechByPartOfSpeech(partOfSpeech.getPartOfSpeech()))
                .thenReturn(partOfSpeech);

        assertThat(partOfSpeechService.getPartOfSpeechByName(partOfSpeech.getPartOfSpeech()), is(partOfSpeech));
        verify(partOfSpeechRepository, times(1))
                .getPartOfSpeechByPartOfSpeech(partOfSpeech.getPartOfSpeech());
    }

    /**
     * When call getById should return PartOfSpeech.
     *
     * @throws Exception exception.
     */
    @Test
    public void whenGetByIdShouldReturnPartOfSpeechById() throws Exception {
        when(partOfSpeechRepository.getById(partOfSpeech.getId())).thenReturn(partOfSpeech);

        assertThat(partOfSpeechService.getById(partOfSpeech.getId()), is(partOfSpeech));
        verify(partOfSpeechRepository, times(1)).getById(partOfSpeech.getId());
    }

    /**
     * When call save should return saved PartOfSpeech.
     *
     * @throws Exception exception.
     */
    @Test
    public void whenSavePartOfSpeechShouldReturnPartOfSpeech() throws Exception {
        when(partOfSpeechRepository.save(partOfSpeech)).thenReturn(partOfSpeech);

        assertThat(partOfSpeechService.save(partOfSpeech), is(partOfSpeech));
        verify(partOfSpeechRepository, times(1)).save(partOfSpeech);
    }
}
