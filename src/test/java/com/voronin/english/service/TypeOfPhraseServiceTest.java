package com.voronin.english.service;

import com.voronin.english.domain.TypeOfPhrase;
import com.voronin.english.repository.TypeOfPhraseRepository;
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
 * TypeOfPhraseService test class.
 *
 * @author Alexey Voronin.
 * @since 04.02.2019.
 */
public class TypeOfPhraseServiceTest {

    /**
     * Mock TypeOfPhraseRepository.
     */
    private TypeOfPhraseRepository typeOfPhraseRepository = mock(TypeOfPhraseRepository.class);

    /**
     * The class object under test.
     */
    private TypeOfPhraseService typeOfPhraseService = new TypeOfPhraseService(typeOfPhraseRepository);

    /**
     * When call save method should return saved entity.
     *
     * @throws Exception exception.
     */
    @Test
    public void whenCallSaveShouldReturnSavedEntity() throws Exception {
        final TypeOfPhrase typeOfPhrase = new TypeOfPhrase("type");
        when(typeOfPhraseRepository.save(typeOfPhrase)).thenReturn(typeOfPhrase);

        assertThat(this.typeOfPhraseService.save(typeOfPhrase), is(typeOfPhrase));
        verify(typeOfPhraseRepository, times(1)).save(typeOfPhrase);
    }

    /**
     * When call findAll method should return list of entity.
     *
     * @throws Exception exception.
     */
    @Test
    public void whenCallFindAllShouldReturnListOfEntity() throws Exception {
        List<TypeOfPhrase> list = Lists.newArrayList(new TypeOfPhrase("type"));
        when(typeOfPhraseRepository.findAll()).thenReturn(list);

        assertThat(this.typeOfPhraseService.findAll(), is(list));
        verify(typeOfPhraseRepository, times(1)).findAll();
    }
}
