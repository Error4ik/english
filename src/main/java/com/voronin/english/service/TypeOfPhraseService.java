package com.voronin.english.service;

import com.voronin.english.domain.TypeOfPhrase;
import com.voronin.english.repository.TypeOfPhraseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * TypeOfPhraseService.
 *
 * @author Alexey Voronin.
 * @since 04.02.2019.
 */
@Service
public class TypeOfPhraseService {

    /**
     * TypeOfPhraseRepository.
     */
    private final TypeOfPhraseRepository typeOfPhraseRepository;

    /**
     * Constructor.
     *
     * @param typeOfPhraseRepository TypeOfPhraseRepository.
     */
    @Autowired
    public TypeOfPhraseService(final TypeOfPhraseRepository typeOfPhraseRepository) {
        this.typeOfPhraseRepository = typeOfPhraseRepository;
    }

    /**
     * Save entity to db.
     *
     * @param typeOfPhrase TypeOfPhrase
     * @return saved TypeOfPhrase.
     */
    public TypeOfPhrase save(final TypeOfPhrase typeOfPhrase) {
        return this.typeOfPhraseRepository.save(typeOfPhrase);
    }

    /**
     * Find all entity.
     *
     * @return list of TypeOfPhrase.
     */
    public List<TypeOfPhrase> findAll() {
        return this.typeOfPhraseRepository.findAll();
    }
}
