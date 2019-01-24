package com.voronin.english.service;

import com.voronin.english.domain.PartOfSpeech;
import com.voronin.english.repository.PartOfSpeechRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

/**
 * Part of speech service.
 *
 * @author Alexey Voronin.
 * @since 10.10.2018.
 */
@Service
public class PartOfSpeechService {

    /**
     * Part of speech repository.
     */
    private final PartOfSpeechRepository partOfSpeechRepository;

    /**
     * Constructor.
     *
     * @param partOfSpeechRepository part of speech repository.
     */
    @Autowired
    public PartOfSpeechService(final PartOfSpeechRepository partOfSpeechRepository) {
        this.partOfSpeechRepository = partOfSpeechRepository;
    }

    /**
     * Get all part of speech.
     *
     * @return List of PartOfSpeech.
     */
    public List<PartOfSpeech> getAll() {
        return this.partOfSpeechRepository.findAll();
    }

    /**
     * Get part ogf speech by name.
     *
     * @param name part of speech name.
     * @return PartOfSpeech.
     */
    public PartOfSpeech getPartOfSpeechByName(final String name) {
        return this.partOfSpeechRepository.getPartOfSpeechByPartOfSpeech(name);
    }

    /**
     * Get list part of speech without noun.
     *
     * @return list of PartOfSpeech without noun.
     */
    public List<PartOfSpeech> getSpeechesWithoutNoun() {
        List<PartOfSpeech> speeches = this.partOfSpeechRepository.findAll();
        speeches.remove(this.getPartOfSpeechByName("Существительное"));
        return speeches;
    }

    /**
     * Get PartOfSpeech by id.
     *
     * @param id id.
     * @return PartOfSpeech.
     */
    public PartOfSpeech getById(final UUID id) {
        return this.partOfSpeechRepository.getById(id);
    }

    /**
     * Save part of speech.
     *
     * @param partOfSpeech PartOfSpeech.
     * @return PartOfSpeech.
     */
    public PartOfSpeech save(final PartOfSpeech partOfSpeech) {
        return this.partOfSpeechRepository.save(partOfSpeech);
    }
}
