package com.voronin.english.service;

import com.voronin.english.domain.Translation;
import com.voronin.english.repository.TranslationRepository;
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
 * TranslationService test class.
 *
 * @author Alexey Voronin.
 * @since 30.11.2018.
 */
public class TranslationServiceTest {

    /**
     * Mock TranslationRepository.
     */
    private TranslationRepository translationRepository = mock(TranslationRepository.class);

    /**
     * The class object under test.
     */
    private TranslationService translationService = new TranslationService(translationRepository);

    /**
     * When call saveAll should return saved list.
     *
     * @throws Exception exception.
     */
    @Test
    public void whenSaveAllShouldReturnSavedList() throws Exception {
        Translation translation = new Translation("translation", null);
        List<Translation> list = Lists.newArrayList(translation);

        when(translationRepository.saveAll(list)).thenReturn(list);

        assertThat(translationService.saveAll(list), is(list));
        verify(translationRepository, times(1)).saveAll(list);
    }

    /**
     * When call delete method should call delete method TranslationRepository class once.
     * @throws Exception
     */
    @Test
    public void whenDeleteMethodShouldCallDeleteMethodRepository() throws Exception {
        Translation translation = new Translation();

        this.translationService.delete(translation);

        verify(this.translationRepository, times(1)).delete(translation);
    }
}
