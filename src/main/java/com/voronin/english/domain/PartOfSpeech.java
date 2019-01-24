package com.voronin.english.domain;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Column;
import java.util.Objects;
import java.util.UUID;

/**
 * Part of speech.
 *
 * @author Alexey Voronin.
 * @since 19.09.2018.
 */
@Entity(name = "part_of_speech")
public class PartOfSpeech {

    /**
     * Id.
     */
    @Id
    @org.hibernate.annotations.Type(type = "pg-uuid")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    /**
     * Part of speech name.
     */
    @Column(name = "part_of_speech")
    private String partOfSpeech;

    /**
     * Number words in part of speech.
     */
    @Column(name = "number_of_words")
    private int numberOfWords;

    /**
     * Empty constructor.
     */
    public PartOfSpeech() {
    }

    public PartOfSpeech(final String partOfSpeech) {
        this.partOfSpeech = partOfSpeech;
    }

    public UUID getId() {
        return id;
    }

    public void setId(final UUID id) {
        this.id = id;
    }

    public String getPartOfSpeech() {
        return partOfSpeech;
    }

    public void setPartOfSpeech(final String partOfSpeech) {
        this.partOfSpeech = partOfSpeech;
    }

    public int getNumberOfWords() {
        return numberOfWords;
    }

    public void setNumberOfWords(final int numberOfWords) {
        this.numberOfWords = numberOfWords;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PartOfSpeech)) {
            return false;
        }
        PartOfSpeech that = (PartOfSpeech) o;
        return Objects.equals(getId(), that.getId())
                && Objects.equals(getPartOfSpeech(), that.getPartOfSpeech());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getPartOfSpeech());
    }

    @Override
    public String toString() {
        return String.format("Part of speech {id=%s partOfSpeech=%s}", getId(), getPartOfSpeech());
    }
}
