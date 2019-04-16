package com.voronin.english.service;

import com.voronin.english.domain.PhraseCategory;
import com.voronin.english.domain.PhraseForTraining;
import com.voronin.english.repository.PhraseForTrainingRepository;
import org.assertj.core.util.Lists;
import org.junit.Test;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;

/**
 * PhraseForTrainingService test class.
 *
 * @author Alexey Voronin.
 * @since 04.02.2019.
 */
public class PhraseForTrainingServiceTest {

    /**
     * Mock PhraseForTrainingRepository.
     */
    private PhraseForTrainingRepository phraseForTrainingRepository = mock(PhraseForTrainingRepository.class);

    /**
     * Mock PhraseCategoryService.
     */
    private PhraseCategoryService phraseCategoryService = mock(PhraseCategoryService.class);

    /**
     * Mock PhraseCategory.
     */
    private PhraseCategory phraseCategory = mock(PhraseCategory.class);

    /**
     * The class object under test.
     */
    private PhraseForTrainingService phraseForTrainingService =
            new PhraseForTrainingService(
                    phraseForTrainingRepository,
                    phraseCategoryService);

    /**
     * Mock Pageable.
     */
    private Pageable pageable;

    /**
     * Id for test.
     */
    private final UUID id = UUID.randomUUID();

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
        List<PhraseForTraining> list = Lists.newArrayList(phraseForTraining);
        when(phraseForTrainingRepository.getAllByPhraseCategoryId(id, pageable)).thenReturn(list);

        assertThat(this.phraseForTrainingService.getPhrasesByCategoryId(id, pageable), is(list));
        verify(phraseForTrainingRepository, times(1)).getAllByPhraseCategoryId(id, pageable);
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
        verify(phraseForTrainingRepository, times(1)).save(phraseForTraining);
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
        verify(phraseForTrainingRepository, times(1)).save(phraseForTraining);
        verify(phraseCategoryService, times(1)).getCategoryByName(phraseCategory.getName());
    }

    /**
     * When getNumberOfRecordsByPhraseCategoryId should return number of records.
     *
     * @throws Exception exception.
     */
    @Test
    public void whenGetNumberOfRecordsByPhraseCategoryIdShouldReturnNumberOfRecords() throws Exception {
        final long numberOfRecords = 10;
        when(this.phraseForTrainingRepository.getNumberOfRecordsByPhraseCategoryId(id)).thenReturn(numberOfRecords);

        assertThat(this.phraseForTrainingService.getNumberOfRecordsByPhraseCategoryId(id), is(numberOfRecords));
        verify(phraseForTrainingRepository, times(1)).getNumberOfRecordsByPhraseCategoryId(id);
    }
}
