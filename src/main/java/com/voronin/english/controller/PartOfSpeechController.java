package com.voronin.english.controller;

import com.voronin.english.domain.PartOfSpeech;
import com.voronin.english.service.PartOfSpeechService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * TODO: comment.
 *
 * @author Alexey Voronin.
 * @since 22.10.2018.
 */
@RestController
@RequestMapping("/part-of-speech")
public class PartOfSpeechController {

    @Autowired
    private PartOfSpeechService partOfSpeechService;

    @GetMapping("/parts-of-speech")
    public List<PartOfSpeech> getSpeeches() {
        return partOfSpeechService.getAll();
    }

    @GetMapping("/part-of-speech-without-noun")
    public List<PartOfSpeech> getSpeechesWithoutNoun() {
        return this.partOfSpeechService.getSpeechesWithoutNoun();
    }
}
