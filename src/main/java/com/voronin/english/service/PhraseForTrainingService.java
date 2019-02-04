package com.voronin.english.service;

import com.voronin.english.domain.PhraseCategory;
import com.voronin.english.domain.PhraseForTraining;
import com.voronin.english.repository.PhraseForTrainingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

/**
 * PhraseOfTraining service.
 *
 * @author Alexey Voronin.
 * @since 02.02.2019.
 */
@Service
public class PhraseForTrainingService {

    /**
     * PhraseForTrainingRepository.
     */
    private final PhraseForTrainingRepository phraseForTrainingRepository;

    /**
     * PhraseCategoryService.
     */
    private final PhraseCategoryService phraseCategoryService;

    /**
     * Constructor.
     *
     * @param phraseForTrainingRepository PhraseForTrainingRepository.
     */
    @Autowired
    public PhraseForTrainingService(
            final PhraseForTrainingRepository phraseForTrainingRepository,
            final PhraseCategoryService phraseCategoryService) {
        this.phraseForTrainingRepository = phraseForTrainingRepository;
        this.phraseCategoryService = phraseCategoryService;
    }

    /**
     * Get all by PhraseCategory.
     *
     * @param phraseCategoryId id.
     * @return list of PhraseForTraining.
     */
    public List<PhraseForTraining> getPhrasesByCategoryId(final UUID phraseCategoryId) {
        return this.phraseForTrainingRepository.getAllByPhraseCategoryId(phraseCategoryId);
    }

    /**
     * Save entity to db.
     *
     * @param phraseForTraining PhraseForTraining.
     * @return saved entity.
     */
    public PhraseForTraining save(final PhraseForTraining phraseForTraining) {
        return this.phraseForTrainingRepository.save(phraseForTraining);
    }

    /**
     * Prepared and save entity.
     *
     * @param phrase    english phrase.
     * @param translate translate.
     * @param category  category.
     * @return saved entity.
     */
    public PhraseForTraining prepareAndSave(final String phrase, final String translate, final String category) {
        PhraseCategory phraseCategory = this.phraseCategoryService.getCategoryByName(category);
        PhraseForTraining phraseForTraining = new PhraseForTraining(phrase, translate, phraseCategory);
        this.save(phraseForTraining);
        phraseCategory.setNumberOfPhrases(phraseCategory.getNumberOfPhrases() + 1);
        this.phraseCategoryService.save(phraseCategory);
        return phraseForTraining;
    }
}
