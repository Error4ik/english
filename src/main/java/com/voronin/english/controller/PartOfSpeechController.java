package com.voronin.english.controller;

import com.voronin.english.domain.PartOfSpeech;
import com.voronin.english.service.PartOfSpeechService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Part of speech controller.
 *
 * @author Alexey Voronin.
 * @since 22.10.2018.
 */
@RestController
@RequestMapping("/part-of-speech")
public class PartOfSpeechController {

    /**
     * Part of speech service.
     */
    private final PartOfSpeechService partOfSpeechService;

    /**
     * Constructor.
     *
     * @param partOfSpeechService part of speech service.
     */
    @Autowired
    public PartOfSpeechController(final PartOfSpeechService partOfSpeechService) {
        this.partOfSpeechService = partOfSpeechService;
    }

    /**
     * Get all part of speech.
     *
     * @return list of part of speech.
     */
    @GetMapping("/parts-of-speech")
    public List<PartOfSpeech> getSpeeches() {
        return partOfSpeechService.getAll();
    }

    /**
     * Get part of speech without noun.
     * @return list of part of speech without noun.
     */
    @GetMapping("/part-of-speech-without-noun")
    public List<PartOfSpeech> getSpeechesWithoutNoun() {
        return this.partOfSpeechService.getSpeechesWithoutNoun();
    }
}
