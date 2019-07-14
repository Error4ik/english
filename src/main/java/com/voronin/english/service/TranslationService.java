package com.voronin.english.service;

import com.voronin.english.domain.Translation;
import com.voronin.english.repository.TranslationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Translate service.
 *
 * @author Alexey Voronin.
 * @since 10.10.2018.
 */
@Service
public class TranslationService {

    /**
     * Translate repository.
     */
    private final TranslationRepository translationRepository;

    /**
     * Constructor.
     *
     * @param translationRepository translate repository.
     */
    @Autowired
    public TranslationService(final TranslationRepository translationRepository) {
        this.translationRepository = translationRepository;
    }

    /**
     * Save list translations.
     *
     * @param list list.
     * @return List of translation.
     */
    public List<Translation> saveAll(final List<Translation> list) {
        return this.translationRepository.saveAll(list);
    }

    /**
     * @param translation t.
     */
    public void delete(final Translation translation) {
        this.translationRepository.delete(translation);
    }
}
