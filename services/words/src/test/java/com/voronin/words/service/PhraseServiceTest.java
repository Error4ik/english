package com.voronin.words.service;

import com.voronin.words.domain.Phrase;
import com.voronin.words.repository.PhraseRepository;
import org.assertj.core.util.Lists;
import org.junit.Test;

import java.util.List;
import java.util.UUID;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;

/**
 * PhraseService test class.
 *
 * @author Alexey Voronin.
 * @since 30.11.2018.
 */
public class PhraseServiceTest {

    /**
     * Mock PhraseRepository.
     */
    private PhraseRepository phraseRepository = mock(PhraseRepository.class);

    /**
     * The class object under test.
     */
    private PhraseService phraseService = new PhraseService(phraseRepository);

    /**
     * When call saveAll should return saved list.
     *
     * @throws Exception exception.
     */
    @Test
    public void whenSaveAllShouldReturnSavedList() throws Exception {
        Phrase phrase = new Phrase();
        phrase.setId(UUID.randomUUID());
        phrase.setPhrase("Test Phrase");
        List<Phrase> list = Lists.newArrayList(phrase);
        when(phraseRepository.saveAll(list)).thenReturn(list);

        assertThat(phraseService.saveAll(list), is(list));
        verify(phraseRepository, times(1)).saveAll(list);
    }
}
