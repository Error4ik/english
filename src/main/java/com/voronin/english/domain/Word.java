package com.voronin.english.domain;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

/**
 * Not a noun word.
 *
 * @author Alexey Voronin.
 * @since 05.04.2019.
 */
@Entity
@DiscriminatorValue("word")
public class Word extends AnyWord {

    /**
     * Empty constructor.
     */
    public Word() {

    }

    /**
     * Constructor.
     *
     * @param word          any word.
     * @param transcription Transcription.
     * @param partOfSpeech  PartOfSpeech.
     * @param description   any description.
     */
    public Word(final String word,
                final String transcription,
                final PartOfSpeech partOfSpeech,
                final String description) {
        super(word, transcription, partOfSpeech, description);
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
