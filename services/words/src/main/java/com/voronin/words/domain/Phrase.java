package com.voronin.words.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.ManyToOne;
import javax.persistence.JoinColumn;
import javax.persistence.FetchType;
import java.util.Objects;
import java.util.UUID;

/**
 * Phrase.
 *
 * @author Alexey Voronin.
 * @since 20.09.2018.
 */
@Entity(name = "phrases")
public class Phrase {

    /**
     * Id.
     */
    @Id
    @org.hibernate.annotations.Type(type = "pg-uuid")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    /**
     * Phrase.
     */
    private String phrase;

    /**
     * Phrase translate.
     */
    private String translate;

    /**
     * Word for phrase.
     */
    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "word_id")
    private Word word;

    /**
     * Empty constructor.
     */
    public Phrase() {
    }

    /**
     * Constructor.
     *
     * @param phrase    phrase.
     * @param translate translate.
     * @param word      word for phrase.
     */
    public Phrase(final String phrase, final String translate, final Word word) {
        this.phrase = phrase;
        this.translate = translate;
        this.word = word;
    }

    public UUID getId() {
        return id;
    }

    public void setId(final UUID id) {
        this.id = id;
    }

    public String getPhrase() {
        return phrase;
    }

    public void setPhrase(final String phrase) {
        this.phrase = phrase;
    }

    public String getTranslate() {
        return translate;
    }

    public void setTranslate(final String translate) {
        this.translate = translate;
    }

    public Word getWord() {
        return word;
    }

    public void setWord(final Word word) {
        this.word = word;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Phrase)) {
            return false;
        }
        Phrase phrase1 = (Phrase) o;
        return Objects.equals(getId(), phrase1.getId())
                && Objects.equals(getPhrase(), phrase1.getPhrase())
                && Objects.equals(getTranslate(), phrase1.getTranslate());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getPhrase(), getTranslate());
    }

    @Override
    public String toString() {
        return String.format("Phrase {id=%s phrase=%s translate=%s}", getId(), getPhrase(), getTranslate());
    }
}
