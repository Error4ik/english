package com.voronin.words.service;

import com.voronin.words.domain.PartOfSpeech;
import com.voronin.words.repository.PartOfSpeechRepository;
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
    public List<PartOfSpeech> findAll() {
        return this.partOfSpeechRepository.findAll();
    }

    /**
     * Get part ogf speech by name.
     *
     * @param name part of speech name.
     * @return PartOfSpeech.
     */
    public PartOfSpeech getPartOfSpeechByName(final String name) {
        return this.partOfSpeechRepository.getPartOfSpeechByName(name);
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
