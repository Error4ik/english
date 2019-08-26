package com.voronin.nouns.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.Id;
import javax.persistence.Entity;
import javax.persistence.GenerationType;
import javax.persistence.GeneratedValue;
import javax.persistence.ManyToOne;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
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
     * Noun for phrase.
     */
    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "noun_id")
    private Noun noun;

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
     * @param noun      noun.
     */
    public Phrase(final String phrase, final String translate, final Noun noun) {
        this.phrase = phrase;
        this.translate = translate;
        this.noun = noun;
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

    public Noun getNoun() {
        return noun;
    }

    public void setNoun(final Noun noun) {
        this.noun = noun;
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
