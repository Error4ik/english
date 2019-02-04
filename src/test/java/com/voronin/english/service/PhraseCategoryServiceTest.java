package com.voronin.english.service;

import com.voronin.english.domain.PhraseCategory;
import com.voronin.english.repository.PhraseCategoryRepository;
import org.assertj.core.util.Lists;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

/**
 * PhraseCategoryService test class.
 *
 * @author Alexey Voronin.
 * @since 04.02.2019.
 */
@RunWith(SpringRunner.class)
@WebMvcTest(PhraseCategoryService.class)
@WithMockUser(username = "user", roles = {"USER"})
public class PhraseCategoryServiceTest {

    /**
     * The class object under test.
     */
    @Autowired
    private PhraseCategoryService phraseCategoryService;

    /**
     * Mock PhraseCategoryRepository.
     */
    @MockBean
    private PhraseCategoryRepository phraseCategoryRepository;

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
    }

}
