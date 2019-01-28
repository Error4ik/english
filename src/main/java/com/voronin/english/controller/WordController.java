package com.voronin.english.controller;

import com.voronin.english.domain.Word;
import com.voronin.english.service.WordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

/**
 * Word controller.
 *
 * @author Alexey Voronin.
 * @since 10.10.2018.
 */
@RestController
@RequestMapping("/word")
public class WordController {

    /**
     * Word service.
     */
    private final WordService wordService;

    /**
     * Constructor.
     *
     * @param wordService word service.
     */
    @Autowired
    public WordController(final WordService wordService) {
        this.wordService = wordService;
    }

    /**
     * Get all words.
     *
     * @return list of words.
     */
    @GetMapping("/words")
    public Object getWords() {
        return new Object() {
            public List<Word> getAllWord() {
                return wordService.getWords();
            }
        };
    }

    /**
     * Get words by category.
     *
     * @param id category id.
     * @return an object containing a list of words.
     */
    @GetMapping("/words-by-category/{id}")
    public Object getWordsByCategory(@PathVariable final UUID id) {
        return new Object() {
            public List<Word> getWordsByCategory() {
                return wordService.getWordsByCategory(id);
            }
        };
    }

    /**
     * Get words by part of speech.
     *
     * @param id part of speech id.
     * @return list of words.
     */
    @GetMapping("/words-by-part-of-speech/{id}")
    public List<Word> getWordsByPartOfSpeech(@PathVariable final UUID id) {
        return this.wordService.getWordsByPartOfSpeechId(id);
    }
}
