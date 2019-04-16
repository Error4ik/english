package com.voronin.english.service;

import com.voronin.english.domain.PhraseCategory;
import com.voronin.english.repository.PhraseCategoryRepository;
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
 * PhraseCategoryService test class.
 *
 * @author Alexey Voronin.
 * @since 04.02.2019.
 */
public class PhraseCategoryServiceTest {

    /**
     * Mock PhraseCategoryRepository.
     */
    private PhraseCategoryRepository phraseCategoryRepository = mock(PhraseCategoryRepository.class);

    /**
     * The class object under test.
     */
    private PhraseCategoryService phraseCategoryService = new PhraseCategoryService(phraseCategoryRepository);

    /**
     * When call save should return saved entity.
     *
     * @throws Exception exception.
     */
    @Test
    public void whenCallSaveShouldReturnSavedEntity() throws Exception {
        final PhraseCategory phraseCategory = new PhraseCategory();
        when(phraseCategoryRepository.save(phraseCategory)).thenReturn(phraseCategory);

        assertThat(this.phraseCategoryService.save(phraseCategory), is(phraseCategory));
        verify(phraseCategoryRepository, times(1)).save(phraseCategory);
    }

    /**
     * When call findAll should return list of entity.
     *
     * @throws Exception exception.
     */
    @Test
    public void whenCallFindAllShouldReturnListOfEntity() throws Exception {
        final List<PhraseCategory> list = Lists.newArrayList(new PhraseCategory());
        when(this.phraseCategoryRepository.findAll()).thenReturn(list);

        assertThat(this.phraseCategoryService.findAll(), is(list));
        verify(phraseCategoryRepository, times(1)).findAll();
    }

    /**
     * When call getCategoryByName should return entity.
     *
     * @throws Exception exception.
     */
    @Test
    public void whenGetCategoryByNameShouldReturnEntity() throws Exception {
        final PhraseCategory phraseCategory = new PhraseCategory();
        when(phraseCategoryRepository.getByName("category")).thenReturn(phraseCategory);

        assertThat(this.phraseCategoryService.getCategoryByName("category"), is(phraseCategory));
        verify(phraseCategoryRepository, times(1)).getByName("category");
    }

}
