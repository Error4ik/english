package com.voronin.english.service;

import com.voronin.english.domain.PartOfSpeech;
import com.voronin.english.repository.PartOfSpeechRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

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

    public List<PartOfSpeech> getSpeechesWithoutNoun() {
        List<PartOfSpeech> speeches = this.partOfSpeechRepository.findAll();
        speeches.remove(this.getPartOfSpeechByName("Существительное"));
        return speeches;
    }

    public PartOfSpeech getById(final UUID id) {
        return this.partOfSpeechRepository.getById(id);
    }

    public PartOfSpeech save(final PartOfSpeech partOfSpeech) {
        return this.partOfSpeechRepository.save(partOfSpeech);
    }
}
