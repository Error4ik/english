package com.voronin.english.service;

import com.voronin.english.domain.TimeOfPhrase;
import com.voronin.english.repository.TimeOfPhraseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * TimeOfPhrase service.
 *
 * @author Alexey Voronin.
 * @since 04.02.2019.
 */
@Service
public class TimeOfPhraseService {

    /**
     * TimeOfPhraseRepository.
     */
    private final TimeOfPhraseRepository timeOfPhraseRepository;

    /**
     * Constructor.
     *
     * @param timeOfPhraseRepository TimeOfPhraseRepository.
     */
    @Autowired
    public TimeOfPhraseService(final TimeOfPhraseRepository timeOfPhraseRepository) {
        this.timeOfPhraseRepository = timeOfPhraseRepository;
    }

    /**
     * Save entity to db.
     *
     * @param timeOfPhrase TimeOfPhrase entity.
     * @return saved entity.
     */
    public TimeOfPhrase save(final TimeOfPhrase timeOfPhrase) {
        return this.timeOfPhraseRepository.save(timeOfPhrase);
    }

    /**
     * Find all entity.
     *
     * @return list of TimeOfPhrase.
     */
    public List<TimeOfPhrase> findAll() {
        return this.timeOfPhraseRepository.findAll();
    }
}
