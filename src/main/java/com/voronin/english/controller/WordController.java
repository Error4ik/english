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
 * TODO: comment.
 *
 * @author Alexey Voronin.
 * @since 10.10.2018.
 */
@RestController
@RequestMapping("/word")
public class WordController {

    @Autowired
    private WordService wordService;

    @GetMapping("/words")
    public Object getWords() {
        return new Object() {
            public List<Word> getAllWord() {
                return wordService.getWords();
            }
        };
    }

    @GetMapping("/words-by-category/{id}")
    public Object getWordsByCategory(@PathVariable final UUID id) {
        return new Object() {
            public List<Word> getWordsByCategory() {
                return wordService.getWordsByCategory(id);
            }
        };
    }

    @GetMapping("/words-by-part-of-speech/{id}")
    public List<Word> getWordsByPartOfSpeech(@PathVariable final UUID id) {
        return this.wordService.getWordsByPartOfSpeech(id);
    }
}
