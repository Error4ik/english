package com.voronin.english.controller;

import com.voronin.english.domain.CardFilled;
import com.voronin.english.domain.Word;
import com.voronin.english.service.WordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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

    @RequestMapping(value = "/add", method = RequestMethod.POST, consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public void saveWord(CardFilled cardFilled, @RequestParam(value = "photo", required = false) MultipartFile photo) {
        this.wordService.prepareAndSave(cardFilled, photo);
    }
}
