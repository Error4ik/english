package com.voronin.english.controller;

import com.voronin.english.domain.Noun;
import com.voronin.english.domain.Word;
import com.voronin.english.service.NounService;
import com.voronin.english.service.WordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
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
     * Noun service.
     */
    private final NounService nounService;

    /**
     * Constructor.
     *
     * @param wordService word service.
     */
    @Autowired
    public WordController(final WordService wordService, final NounService nounService) {
        this.wordService = wordService;
        this.nounService = nounService;
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
     * @param id    category id.
     * @param limit item per page.
     * @param page  number of page.
     * @return an object containing a list of words.
     */
    @GetMapping("/words-by-category/{id}/{limit}/{page}")
    public Object getWordsByCategory(
            @PathVariable final UUID id,
            @PathVariable final int limit,
            @PathVariable final int page) {
        return new Object() {
            public List<Noun> getWordsByCategory() {
                return nounService.getNounsByCategoryId(id, new PageRequest(
                        page,
                        limit,
                        Sort.Direction.ASC,
                        "word"));
            }

            public long getAllRecords() {
                return nounService.getNumberOfRecordsByCategoryId(id);
            }
        };
    }

    /**
     * Get words by part of speech.
     *
     * @param id    part of speech id.
     * @param limit item per page.
     * @param page  number of page.
     * @return list of words.
     */
    @GetMapping("/words-by-part-of-speech/{id}/{limit}/{page}")
    public Object getWordsByPartOfSpeech(
            @PathVariable final UUID id,
            @PathVariable final int limit,
            @PathVariable final int page) {
        return new Object() {
            public List<Word> getWordsByPartOfSpeech() {
                return wordService.getWordsByPartOfSpeechId(id, new PageRequest(
                        page,
                        limit,
                        Sort.Direction.ASC,
                        "word"
                ));
            }

            public long getAllRecords() {
                return wordService.getNumberOfRecordsByPartOfSpeechId(id);
            }
        };
    }

    /**
     * Get words by category id;
     *
     * @param id category id.
     * @return list of words.
     */
    @RequestMapping("/words-by-category/{id}")
    public List<Noun> getWordsByCategoryId(@PathVariable final UUID id) {
        return this.nounService.getNounsByCategoryId(id);
    }
}
