package com.voronin.sentence.service;

import com.voronin.sentence.domain.Category;
import com.voronin.sentence.domain.Sentence;
import com.voronin.sentence.repository.SentenceRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.UUID;

/**
 * PhraseOfTraining service.
 *
 * @author Alexey Voronin.
 * @since 02.02.2019.
 */
@Service
public class SentenceService {

    /**
     * Logger.
     */
    private final Logger logger = LoggerFactory.getLogger(SentenceService.class);

    /**
     * PhraseForTrainingRepository.
     */
    private final SentenceRepository sentenceRepository;

    /**
     * PhraseCategoryService.
     */
    private final CategoryService categoryService;

    /**
     * Constructor.
     *
     * @param sentenceRepository SentenceRepository.
     * @param categoryService    CategoryService.
     */
    @Autowired
    public SentenceService(
            final SentenceRepository sentenceRepository,
            final CategoryService categoryService) {
        this.sentenceRepository = sentenceRepository;
        this.categoryService = categoryService;
    }

    /**
     * Get all by PhraseCategory.
     *
     * @param categoryId id.
     * @param pageable   Pageable.
     * @return list of Sentence.
     */
    public List<Sentence> getSentencesByCategoryId(final UUID categoryId, final Pageable pageable) {
        return this.sentenceRepository.getAllByCategoryId(categoryId, pageable);
    }

    /**
     * Get the number of records by PhraseCategory id.
     *
     * @param phraseCategoryId PhraseCategory id.
     * @return number of records.
     */
    public long getNumberOfRecordsBySentenceCategoryId(final UUID phraseCategoryId) {
        return this.sentenceRepository.getNumberOfRecordsBySentenceCategoryId(phraseCategoryId);
    }

    /**
     * Save entity to db.
     *
     * @param sentence Sentence.
     * @return saved entity.
     */
    public Sentence save(final Sentence sentence) {
        return this.sentenceRepository.save(sentence);
    }

    /**
     * Prepared and save entity.
     *
     * @param sentence  english sentence.
     * @param translate translate.
     * @param category  category.
     * @return saved entity.
     */
    @Transactional
    public Sentence prepareAndSave(final String sentence, final String translate, final String category) {
        logger.debug(String.format("Arguments - sentense - %s, translate - %s, category - %s",
                sentence,
                translate,
                category));
        Category cat = this.categoryService.getCategoryByName(category);
        Sentence newSentence = new Sentence(sentence, translate, cat);
        this.save(newSentence);
        cat.setNumberOfSentences(cat.getNumberOfSentences() + 1);
        this.categoryService.save(cat);
        logger.debug(String.format("Return - %s", newSentence));
        return newSentence;
    }
}
