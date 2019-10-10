package com.voronin.sentence.controller;

import com.voronin.sentence.domain.KeyWord;
import com.voronin.sentence.service.KeyWordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * KeyWord Controller.
 *
 * @author Alexey Voronin.
 * @since 08.10.2019.
 */
@RestController
@RequestMapping("/key-word")
public class KeyWordController {

    /**
     * KeyWordService.
     */
    private final KeyWordService keyWordService;

    /**
     * Constructor.
     *
     * @param keyWordService KeyWord service.
     */
    @Autowired
    public KeyWordController(final KeyWordService keyWordService) {
        this.keyWordService = keyWordService;
    }

    /**
     * Add keyWord.
     *
     * @param word KeyWord.
     * @return saved KeyWord.
     */
    @PreAuthorize("hasAuthority('admin')")
    @RequestMapping("/add")
    public KeyWord addKeyWord(final KeyWord word) {
        return this.keyWordService.save(word);
    }

    /**
     * Get all Key Word.
     *
     * @return list of KeyWords.
     */
    @RequestMapping("/words")
    public List<KeyWord> getKeyWords() {
        return this.keyWordService.getKeyWords();
    }
}
