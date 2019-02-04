package com.voronin.english.service;

import com.voronin.english.domain.PhraseCategory;
import com.voronin.english.repository.PhraseCategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * PhraseCategory service.
 *
 * @author Alexey Voronin.
 * @since 02.02.2019.
 */
@Service
public class PhraseCategoryService {

    /**
     * PhraseCategoryRepository.
     */
    private final PhraseCategoryRepository phraseCategoryRepository;

    /**
     * Constructor.
     *
     * @param phraseCategoryRepository PhraseCategoryRepository.
     */
    @Autowired
    public PhraseCategoryService(final PhraseCategoryRepository phraseCategoryRepository) {
        this.phraseCategoryRepository = phraseCategoryRepository;
    }

    /**
     * Save entity.
     *
     * @param phraseCategory PhraseCategory.
     * @return saved entity.
     */
    public PhraseCategory save(final PhraseCategory phraseCategory) {
        return this.phraseCategoryRepository.save(phraseCategory);
    }

    /**
     * Get all PhraseCategory.
     *
     * @return list of PhraseCategory.
     */
    public List<PhraseCategory> findAll() {
        return this.phraseCategoryRepository.findAll();
    }

    /**
     * Get category by name.
     *
     * @param categoryName category name.
     * @return PhraseCategory.
     */
    public PhraseCategory getCategoryByName(final String categoryName) {
        return this.phraseCategoryRepository.getByName(categoryName);
    }
}
