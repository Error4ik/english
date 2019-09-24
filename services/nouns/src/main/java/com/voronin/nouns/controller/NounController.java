package com.voronin.nouns.controller;

import com.voronin.nouns.domain.CardFilled;
import com.voronin.nouns.domain.Noun;
import com.voronin.nouns.service.NounService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

/**
 * Word controller.
 *
 * @author Alexey Voronin.
 * @since 10.10.2018.
 */
@RestController
@RequestMapping("/noun")
public class NounController {

    /**
     * Noun service.
     */
    private final NounService nounService;

    /**
     * Constructor.
     *
     * @param nounService noun service.
     */
    @Autowired
    public NounController(final NounService nounService) {
        this.nounService = nounService;
    }

    /**
     * Add new noun.
     *
     * @param cardFilled Filled model for cards.
     * @param photo      the image to the word.
     */
    @PreAuthorize("hasAuthority('admin')")
    @RequestMapping("/add-noun")
    public void saveNoun(
            final CardFilled cardFilled,
            final @RequestParam(value = "photo", required = false) MultipartFile photo) {
        this.nounService.prepareAndSave(cardFilled, photo);
    }

    /**
     * Edit noun.
     *
     * @param cardFilled filled model of card.
     * @param photo      the image to the word.
     * @param nounId     noun id for edit.
     */
    @PreAuthorize("hasAuthority('admin')")
    @RequestMapping("/edit-noun")
    public void editNoun(
            final CardFilled cardFilled,
            final @RequestParam(value = "photo", required = false) MultipartFile photo,
            final String nounId) {
        this.nounService.editNounAndSave(cardFilled, photo, nounId);
    }

    /**
     * Delete noun.
     *
     * @param id nounId for delete.
     */
    @PreAuthorize("hasAuthority('admin')")
    @RequestMapping("/delete-noun")
    public void deleteNoun(@RequestParam final UUID id) {
        nounService.deleteNoun(id);
    }


    /**
     * Get all words.
     *
     * @return list of words.
     */
    @RequestMapping("/nouns")
    public List<Noun> getNouns() {
        return nounService.getNouns();
    }

    /**
     * Get nouns by category.
     *
     * @param id    category id.
     * @param limit item per page.
     * @param page  number of page.
     * @return an object containing a list of nouns and number of records.
     */
    @RequestMapping("/nouns/by/category/{id}/{limit}/{page}")
    public Object getNounsByCategory(
            @PathVariable final UUID id,
            @PathVariable final int limit,
            @PathVariable final int page) {
        return new Object() {
            public List<Noun> getNouns() {
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
     * Get nouns by category id;
     *
     * @param id category id.
     * @return list of nouns.
     */
    @RequestMapping("/nouns/by/category/{id}")
    public List<Noun> getWordsByCategoryId(@PathVariable final UUID id) {
        return this.nounService.getNounsByCategoryId(id);
    }
}
