package com.voronin.english.service;

import com.voronin.english.domain.Phrase;
import com.voronin.english.repository.PhraseRepository;
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
public class PhraseService {

    @Autowired
    private PhraseRepository phraseRepository;

    public void saveAll(final List<Phrase> list) {
        this.phraseRepository.saveAll(list);
    }
}
