package com.voronin.nouns.service;

import com.voronin.nouns.domain.Phrase;
import com.voronin.nouns.repository.PhraseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Phrase service.
 *
 * @author Alexey Voronin.
 * @since 10.10.2018.
 */
@Service
public class PhraseService {

    /**
     * Phrase repository.
     */
    private final PhraseRepository phraseRepository;

    /**
     * Constructor.
     *
     * @param phraseRepository phrase repository.
     */
    @Autowired
    public PhraseService(final PhraseRepository phraseRepository) {
        this.phraseRepository = phraseRepository;
    }

    /**
     * Save all Phrases.
     *
     * @param list list phrases.
     * @return List of phrases.
     */
    public List<Phrase> saveAll(final List<Phrase> list) {
        return this.phraseRepository.saveAll(list);
    }
}
