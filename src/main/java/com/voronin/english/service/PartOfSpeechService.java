package com.voronin.english.service;

import com.voronin.english.domain.PartOfSpeech;
import com.voronin.english.repository.PartOfSpeechRepository;
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
public class PartOfSpeechService {

    @Autowired
    private PartOfSpeechRepository partOfSpeechRepository;

    public List<PartOfSpeech> getAll() {
        return this.partOfSpeechRepository.findAll();
    }

    public PartOfSpeech getPartOfSpeechByName(final String name) {
        return this.partOfSpeechRepository.getPartOfSpeechByPartOfSpeech(name);
    }
}
