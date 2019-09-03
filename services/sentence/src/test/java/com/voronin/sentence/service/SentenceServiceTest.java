package com.voronin.sentence.service;

import com.voronin.sentence.domain.Category;
import com.voronin.sentence.domain.Sentence;
import com.voronin.sentence.repository.SentenceRepository;
import org.assertj.core.util.Lists;
import org.junit.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.times;

/**
 * PhraseForTrainingService test class.
 *
 * @author Alexey Voronin.
 * @since 04.02.2019.
 */
public class SentenceServiceTest {

    /**
     * Mock SentenceRepository.
     */
    private SentenceRepository sentenceRepository = mock(SentenceRepository.class);

    /**
     * Mock CategoryService.
     */
    private CategoryService categoryService = mock(CategoryService.class);

    /**
     * Mock Category.
     */
    private Category category = new Category();

    /**
     * The class object under test.
     */
    private SentenceService sentenceService =
            new SentenceService(
                    sentenceRepository,
                    categoryService);

    /**
     * Mock Pageable.
     */
    @MockBean
    private Pageable pageable;

    /**
     * Id for test.
     */
    private final UUID id = UUID.randomUUID();

    /**
     * Object for test.
     */
    private final Sentence sentence =
            new Sentence("phrase", "translate", category);

    /**
     * When call getSentencesByCategoryId should return PhraseForTraining entity.
     *
     * @throws Exception exception.
     */
    @Test
    public void whenGetSentencesByCategoryIdShouldReturnListOfSentence() throws Exception {
        List<Sentence> list = Lists.newArrayList(sentence);
        when(sentenceRepository.getAllByCategoryId(id, pageable)).thenReturn(list);

        assertThat(this.sentenceService.getSentencesByCategoryId(id, pageable), is(list));
        verify(sentenceRepository, times(1)).getAllByCategoryId(id, pageable);
    }

    /**
     * When call save should return saved sentence.
     *
     * @throws Exception exception.
     */
    @Test
    public void whenCallSaveShouldReturnSavedSentence() throws Exception {
        when(sentenceRepository.save(sentence)).thenReturn(sentence);

        assertThat(this.sentenceService.save(sentence), is(sentence));
        verify(sentenceRepository, times(1)).save(sentence);
    }

    /**
     * When call prepareAndSave should return saved sentence.
     *
     * @throws Exception exception.
     */
    @Test
    public void whenCallPrepareAndSaveShouldReturnSavedSentenceAndIncreaseNumberSentence() throws Exception {
        category.setName("category");
        when(categoryService.getCategoryByName(category.getName())).thenReturn(category);
        when(sentenceRepository.save(sentence)).thenReturn(sentence);
        final int numberOfSentence = 1;

        assertThat(this.sentenceService.prepareAndSave(
                sentence.getSentence(),
                sentence.getTranslation(),
                category.getName()), is(sentence));
        assertThat(category.getNumberOfSentences(), is(numberOfSentence));
        verify(sentenceRepository, times(1)).save(sentence);
        verify(categoryService, times(1)).getCategoryByName(category.getName());
    }

    /**
     * When getNumberOfRecordsBySentenceCategoryId should return number of records.
     *
     * @throws Exception exception.
     */
    @Test
    public void whenGetNumberOfRecordsBySentenceCategoryIdShouldReturnNumberOfRecords() throws Exception {
        final long numberOfRecords = 10;
        when(this.sentenceRepository.getNumberOfRecordsBySentenceCategoryId(id)).thenReturn(numberOfRecords);

        assertThat(this.sentenceService.getNumberOfRecordsBySentenceCategoryId(id), is(numberOfRecords));
        verify(sentenceRepository, times(1)).getNumberOfRecordsBySentenceCategoryId(id);
    }
}
