package com.voronin.english.domain;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.ManyToOne;
import javax.persistence.JoinColumn;
import javax.persistence.Column;
import javax.persistence.FetchType;
import java.util.Objects;
import java.util.UUID;

/**
 * Category for phrases.
 *
 * @author Alexey Voronin.
 * @since 30.01.2019.
 */
@Entity(name = "phrase_category")
public class PhraseCategory {

    /**
     * Id.
     */
    @Id
    @org.hibernate.annotations.Type(type = "pg-uuid")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    /**
     * Category name.
     */
    private String name;

    /**
     * TimeOfPhrase.
     */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "time_of_phrase_id")
    private TimeOfPhrase timeOfPhrase;

    /**
     * TypeOfPhrase.
     */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "type_of_phrase_id")
    private TypeOfPhrase typeOfPhrase;

    /**
     * Description.
     */
    private String description;

    /**
     * Number of phrases in category.
     */
    @Column(name = "number_of_phrases")
    private int numberOfPhrases;

    /**
     * Constructor.
     */
    public PhraseCategory() {
    }

    /**
     * Constructor.
     *
     * @param name            category name.
     * @param timeOfPhrase    TimeOfPhrase.
     * @param typeOfPhrase    TypeOfPhrase.
     * @param description     description.
     * @param numberOfPhrases Number of phrases in category.
     */
    public PhraseCategory(
            final String name,
            final TimeOfPhrase timeOfPhrase,
            final TypeOfPhrase typeOfPhrase,
            final String description,
            final int numberOfPhrases) {
        this.name = name;
        this.timeOfPhrase = timeOfPhrase;
        this.typeOfPhrase = typeOfPhrase;
        this.description = description;
        this.numberOfPhrases = numberOfPhrases;
    }

    public UUID getId() {
        return id;
    }

    public void setId(final UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public TimeOfPhrase getTimeOfPhrase() {
        return timeOfPhrase;
    }

    public void setTimeOfPhrase(final TimeOfPhrase timeOfPhrase) {
        this.timeOfPhrase = timeOfPhrase;
    }

    public TypeOfPhrase getTypeOfPhrase() {
        return typeOfPhrase;
    }

    public void setTypeOfPhrase(final TypeOfPhrase typeOfPhrase) {
        this.typeOfPhrase = typeOfPhrase;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(final String description) {
        this.description = description;
    }

    public int getNumberOfPhrases() {
        return numberOfPhrases;
    }

    public void setNumberOfPhrases(final int numberOfPhrases) {
        this.numberOfPhrases = numberOfPhrases;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PhraseCategory)) {
            return false;
        }
        PhraseCategory that = (PhraseCategory) o;
        return getNumberOfPhrases() == that.getNumberOfPhrases()
                && Objects.equals(getId(), that.getId())
                && Objects.equals(getName(), that.getName())
                && Objects.equals(getDescription(), that.getDescription());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getName(), getDescription(), getNumberOfPhrases());
    }

    @Override
    public String toString() {
        return String.format(
                "PhraseCategory{id=%s, name=%s, timeOfPhrase=%s, typeOfPhrase=%s, desc=%s, numberOfPhrase=%s}",
                getId(),
                getName(),
                getTimeOfPhrase(),
                getTypeOfPhrase(),
                getDescription(),
                getNumberOfPhrases());
    }
}
