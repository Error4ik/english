package com.voronin.words.controller;

import com.voronin.words.domain.CardFilled;
import com.voronin.words.domain.Word;
import com.voronin.words.service.WordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PathVariable;

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
     * Add new word.
     *
     * @param cardFilled Filled model for cards.
     */
    @PreAuthorize("hasAuthority('admin')")
    @RequestMapping("/add-word")
    public void saveWord(final CardFilled cardFilled) {
        this.wordService.prepareAndSave(cardFilled);
    }

    /**
     * Edit word.
     *
     * @param cardFilled filled model of card.
     * @param wordId     word id fo edit.
     */
    @PreAuthorize("hasAuthority('admin')")
    @RequestMapping("/edit-word")
    public Word editWord(final CardFilled cardFilled, final String wordId) {
        return this.wordService.editWordAndSave(cardFilled, wordId);
    }

    /**
     * Delete word.
     *
     * @param id wordId for delete.
     */
    @PreAuthorize("hasAuthority('admin')")
    @RequestMapping("/delete-word")
    public void deleteWord(@RequestParam final UUID id) {
        wordService.deleteWord(id);
    }

    /**
     * Get words by part of speech.
     *
     * @param id    part of speech id.
     * @param limit item per page.
     * @param page  number of page.
     * @return list of words.
     */
    @RequestMapping("/words/by/part-of-speech/{id}/{limit}/{page}")
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
     * Get list of Word by part of speech id.
     *
     * @param id part of speech id.
     * @return list of Word.
     */
    @RequestMapping("/words/by/part-of-speech/{id}")
    public List<Word> getWordsByPartOfSpeechId(@PathVariable final UUID id) {
        return this.wordService.getWordsByPartOfSpeechId(id);
    }

    /**
     * Get all words.
     *
     * @return list of Word.
     */
    @RequestMapping("/words")
    public List<Word> getWords() {
        return this.wordService.getWords();
    }
}
