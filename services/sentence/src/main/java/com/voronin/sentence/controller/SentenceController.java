package com.voronin.sentence.controller;

import com.voronin.sentence.domain.Sentence;
import com.voronin.sentence.service.SentenceService;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
@RequestMapping("/sentence")
public class SentenceController {

    /**
     * PhraseCategoryService.
     */
    private final SentenceService sentenceService;

    /**
     * Constructor.
     *
     * @param sentenceService SentenceService.
     */
    public SentenceController(final SentenceService sentenceService) {
        this.sentenceService = sentenceService;
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
    public Object getSentencesByCategoryId(
            @PathVariable final UUID id,
            @PathVariable final int limit,
            @PathVariable final int page) {
        return new Object() {
            public List<Sentence> getSentencesByCategoryId() {
                return sentenceService.getSentencesByCategoryId(
                        id,
                        new PageRequest(
                                page,
                                limit,
                                Sort.Direction.ASC,
                                "id"
                        ));
            }

            public long getAllRecords() {
                return sentenceService.getNumberOfRecordsBySentenceCategoryId(id);
            }
        };
    }

    /**
     * @param sentence  english sentence.
     * @param translate translate.
     * @param category  category name.
     * @return saved PhraseForTraining.
     */
    @PreAuthorize("hasAuthority('admin')")
    @RequestMapping("/add-sentence")
    public Sentence addSentence(
            final @RequestParam String sentence,
            final @RequestParam String translate,
            final @RequestParam String category) {
        return this.sentenceService.prepareAndSave(sentence, translate, category);
    }
}
