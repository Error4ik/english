package com.voronin.english.domain;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.GenerationType;
import javax.persistence.GeneratedValue;
import javax.persistence.ManyToOne;
import javax.persistence.JoinColumn;
import javax.persistence.FetchType;
import java.util.Objects;
import java.util.UUID;

/**
 * Learning by phrase.
 *
 * @author Alexey Voronin.
 * @since 01.02.2019.
 */
@Entity(name = "phrase_for_training")
public class PhraseForTraining {

    /**
     * Id.
     */
    @Id
    @org.hibernate.annotations.Type(type = "pg-uuid")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    /**
     * Category.
     */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "category_id")
    private PhraseCategory phraseCategory;

    /**
     * Any phrase.
     */
    private String phrase;

    /**
     * The translation of the phrase.
     */
    private String translation;

    /**
     * Constructor.
     */
    public PhraseForTraining() {
    }

    /**
     * Constructor.
     *
     * @param phrase      any phrase.
     * @param translation the translation of the phrase.
     */
    public PhraseForTraining(final String phrase, final String translation, final PhraseCategory phraseCategory) {
        this.phrase = phrase;
        this.translation = translation;
        this.phraseCategory = phraseCategory;
    }

    public UUID getId() {
        return id;
    }

    public void setId(final UUID id) {
        this.id = id;
    }

    public PhraseCategory getPhraseCategory() {
        return phraseCategory;
    }

    public void setPhraseCategory(final PhraseCategory phraseCategory) {
        this.phraseCategory = phraseCategory;
    }

    public String getPhrase() {
        return phrase;
    }

    public void setPhrase(final String phrase) {
        this.phrase = phrase;
    }

    public String getTranslation() {
        return translation;
    }

    public void setTranslation(final String translation) {
        this.translation = translation;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PhraseForTraining)) {
            return false;
        }
        PhraseForTraining that = (PhraseForTraining) o;
        return Objects.equals(getId(), that.getId())
                && Objects.equals(getPhrase(), that.getPhrase())
                && Objects.equals(getTranslation(), that.getTranslation());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getPhrase(), getTranslation());
    }

    @Override
    public String toString() {
        return String.format("PhraseForTraining{id=%s, phrase=%s, translation=%s}",
                getId(), getPhrase(), getTranslation());
    }
}
