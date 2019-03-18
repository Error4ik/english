package com.voronin.english.controller;

import com.voronin.english.domain.PhraseForTraining;
import com.voronin.english.service.PhraseForTrainingService;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
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
     * @param id    id.
     * @param limit item per page.
     * @param page  number of page.
     * @return list of phrases.
     */
    @RequestMapping("/category/{id}/{limit}/{page}")
    public Object getPhrasesByCategoryId(
            @PathVariable final UUID id,
            @PathVariable final int limit,
            @PathVariable final int page) {
        return new Object() {
            public List<PhraseForTraining> getPhrasesByCategoryId() {
                return phraseForTrainingService.getPhrasesByCategoryId(
                        id,
                        new PageRequest(
                                page,
                                limit,
                                Sort.Direction.ASC,
                                "id"
                        ));
            }

            public long getAllRecords() {
                return phraseForTrainingService.getNumberOfRecordsByPhraseCategoryId(id);
            }
        };
    }
}
