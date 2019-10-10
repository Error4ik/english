package com.voronin.sentence.service;

import com.voronin.sentence.domain.KeyWord;
import com.voronin.sentence.repository.KeyWordRepository;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * KeyWordService.
 *
 * @author Alexey Voronin.
 * @since 08.10.2019.
 */
@Service
public class KeyWordService {

    /**
     * KeyWordRepository.
     */
    private final KeyWordRepository keyWordRepository;

    /**
     * Constructor.
     *
     * @param keyWordRepository KeyWord repository.
     */
    public KeyWordService(final KeyWordRepository keyWordRepository) {
        this.keyWordRepository = keyWordRepository;
    }

    /**
     * Save KeyWord.
     *
     * @param word KeyWord.
     * @return Saved KeyWord.
     */
    public KeyWord save(final KeyWord word) {
        return this.keyWordRepository.save(word);
    }

    /**
     * Get KeyWords in names.
     *
     * @param keyWords List of names.
     * @return list of KeyWords.
     */
    public List<KeyWord> getKeyWordsIn(final List<String> keyWords) {
        return this.keyWordRepository.findByWordIn(keyWords);
    }

    /**
     * Get all Key Word.
     *
     * @return list of KeyWords.
     */
    public List<KeyWord> getKeyWords() {
        return this.keyWordRepository.findAll();
    }
}
