package com.voronin.english.controller;

import com.voronin.english.domain.PhraseForTraining;
import com.voronin.english.service.PhraseForTrainingService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

/**
 * Phrase controller.
 *
 * @author Alexey Voronin.
 * @since 04.02.2019.
 */
@RestController
@RequestMapping("/phrase")
public class PhraseController {

    /**
     * PhraseCategoryService.
     */
    private final PhraseForTrainingService phraseForTrainingService;

    /**
     * Constructor.
     *
     * @param phraseForTrainingService PhraseCategoryService.
     */
    public PhraseController(final PhraseForTrainingService phraseForTrainingService) {
        this.phraseForTrainingService = phraseForTrainingService;
    }

    /**
     * Get phrases by category id.
     *
     * @param id id.
     * @return list of phrases.
     */
    @RequestMapping("/category/{id}")
    public List<PhraseForTraining> getPhrasesByCategoryId(@PathVariable final UUID id) {
        return this.phraseForTrainingService.getPhrasesByCategoryId(id);
    }
}
