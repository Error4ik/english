package com.voronin.english.service;

import com.voronin.english.domain.PhraseCategory;
import com.voronin.english.domain.PhraseForTraining;
import com.voronin.english.repository.PhraseForTrainingRepository;
import org.assertj.core.util.Lists;
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
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

/**
 * PhraseForTrainingService test class.
 *
 * @author Alexey Voronin.
 * @since 04.02.2019.
 */
@RunWith(SpringRunner.class)
@WebMvcTest(PhraseForTrainingService.class)
@WithMockUser(username = "user", roles = {"USER"})
public class PhraseForTrainingServiceTest {

    /**
     * The class object under test.
     */
    @Autowired
    private PhraseForTrainingService phraseForTrainingService;

    /**
     * Mock PhraseForTrainingRepository.
     */
    @MockBean
    private PhraseForTrainingRepository phraseForTrainingRepository;

    /**
     * Mock PhraseCategoryService.
     */
    @MockBean
    private PhraseCategoryService phraseCategoryService;

    /**
     * Mock PhraseCategory.
     */
    @MockBean
    private PhraseCategory phraseCategory;

    /**
     * Object for test.
     */
    private final PhraseForTraining phraseForTraining =
            new PhraseForTraining("phrase", "translate", phraseCategory);

    /**
     * When call getPhrasesByCategoryId should return PhraseForTraining entity.
     *
     * @throws Exception exception.
     */
    @Test
    public void whenGetPhrasesByCategoryIdShouldReturnPhraseForTrainingEntity() throws Exception {
        final UUID id = UUID.randomUUID();
        List<PhraseForTraining> list = Lists.newArrayList(phraseForTraining);
        when(phraseForTrainingRepository.getAllByPhraseCategoryId(id)).thenReturn(list);

        assertThat(this.phraseForTrainingService.getPhrasesByCategoryId(id), is(list));
    }

    /**
     * When call save should return saved entity.
     *
     * @throws Exception exception.
     */
    @Test
    public void whenCallSaveShouldReturnSavedEntity() throws Exception {
        when(phraseForTrainingRepository.save(phraseForTraining)).thenReturn(phraseForTraining);

        assertThat(this.phraseForTrainingService.save(phraseForTraining), is(phraseForTraining));
    }

    /**
     * When call prepareAndSave should return saved entity.
     *
     * @throws Exception exception.
     */
    @Test
    public void whenCallPrepareAndSaveShouldReturnSavedEntityAndIncreaseNumberOfPhrasesByPhraseCategory() throws Exception {
        when(phraseCategoryService.getCategoryByName(phraseCategory.getName())).thenReturn(phraseCategory);
        when(phraseForTrainingRepository.save(phraseForTraining)).thenReturn(phraseForTraining);

        assertThat(this.phraseForTrainingService.prepareAndSave(
                phraseForTraining.getPhrase(),
                phraseForTraining.getTranslation(),
                phraseCategory.getName()), is(phraseForTraining));
    }

}