package com.voronin.english.service;

import com.voronin.english.domain.TimeOfPhrase;
import com.voronin.english.repository.TimeOfPhraseRepository;
import org.assertj.core.util.Lists;
import org.junit.Test;

import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;

/**
 * TimeOfPhraseService test class.
 *
 * @author Alexey Voronin.
 * @since 04.02.2019.
 */
public class TimeOfPhraseServiceTest {

    /**
     * Mock TimeOfPhraseRepository.
     */
    private TimeOfPhraseRepository timeOfPhraseRepository = mock(TimeOfPhraseRepository.class);

    /**
     * The class object under test.
     */
    private TimeOfPhraseService timeOfPhraseService = new TimeOfPhraseService(timeOfPhraseRepository);

    /**
     * When call save method should return saved entity.
     *
     * @throws Exception exception.
     */
    @Test
    public void whenCallSaveMethodShouldReturnSavedEntity() throws Exception {
        final TimeOfPhrase timeOfPhrase = new TimeOfPhrase("phrase");
        when(timeOfPhraseRepository.save(timeOfPhrase)).thenReturn(timeOfPhrase);

        assertThat(this.timeOfPhraseService.save(timeOfPhrase), is(timeOfPhrase));
        verify(timeOfPhraseRepository, times(1)).save(timeOfPhrase);
    }

    /**
     * When call findAll method should return list of entity.
     *
     * @throws Exception exception.
     */
    @Test
    public void whenCallFindAllShouldReturnListOfEntity() throws Exception {
        final List<TimeOfPhrase> list = Lists.newArrayList(new TimeOfPhrase("phrase"));
        when(this.timeOfPhraseRepository.findAll()).thenReturn(list);

        assertThat(this.timeOfPhraseService.findAll(), is(list));
        verify(timeOfPhraseRepository, times(1)).findAll();
    }
}
