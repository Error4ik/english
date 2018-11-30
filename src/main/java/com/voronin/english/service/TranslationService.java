package com.voronin.english.service;

import com.voronin.english.domain.Translation;
import com.voronin.english.repository.TranslationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * TODO: comment.
 *
 * @author Alexey Voronin.
 * @since 10.10.2018.
 */
@Service
public class TranslationService {

    @Autowired
    private TranslationRepository translationRepository;


    public List<Translation> saveAll(final List<Translation> list) {
        return this.translationRepository.saveAll(list);
    }
}
