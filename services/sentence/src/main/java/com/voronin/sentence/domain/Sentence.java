package com.voronin.sentence.domain;

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
@Entity(name = "sentences")
public class Sentence {

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
    private Category category;

    /**
     * Any phrase.
     */
    private String sentence;

    /**
     * The translation of the phrase.
     */
    private String translation;

    /**
     * Constructor.
     */
    public Sentence() {
    }

    /**
     * Constructor.
     *
     * @param sentence    any phrase.
     * @param translation the translation of the phrase.
     * @param category    category.
     */
    public Sentence(final String sentence, final String translation, final Category category) {
        this.sentence = sentence;
        this.translation = translation;
        this.category = category;
    }

    public UUID getId() {
        return id;
    }

    public void setId(final UUID id) {
        this.id = id;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(final Category category) {
        this.category = category;
    }

    public String getSentence() {
        return sentence;
    }

    public void setSentence(final String sentence) {
        this.sentence = sentence;
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
        if (!(o instanceof Sentence)) {
            return false;
        }
        Sentence that = (Sentence) o;
        return Objects.equals(getId(), that.getId())
                && Objects.equals(getSentence(), that.getSentence())
                && Objects.equals(getTranslation(), that.getTranslation());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getSentence(), getTranslation());
    }

    @Override
    public String toString() {
        return String.format("Sentence {id=%s, sentence=%s, translation=%s}",
                getId(), getSentence(), getTranslation());
    }
}
