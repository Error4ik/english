package com.voronin.sentence.service;

import com.voronin.sentence.domain.Time;
import com.voronin.sentence.repository.TimeRepository;
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
public class TimeService {

    /**
     * TimeOfPhraseRepository.
     */
    private final TimeRepository timeRepository;

    /**
     * Constructor.
     *
     * @param timeRepository TimeRepository.
     */
    @Autowired
    public TimeService(final TimeRepository timeRepository) {
        this.timeRepository = timeRepository;
    }

    /**
     * Save entity to db.
     *
     * @param timeOfPhrase TimeOfPhrase entity.
     * @return saved entity.
     */
    public Time save(final Time timeOfPhrase) {
        return this.timeRepository.save(timeOfPhrase);
    }

    /**
     * Find all entity.
     *
     * @return list of TimeOfPhrase.
     */
    public List<Time> findAll() {
        return this.timeRepository.findAll();
    }
}
