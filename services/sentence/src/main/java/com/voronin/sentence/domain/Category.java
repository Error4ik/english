package com.voronin.sentence.domain;

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
@Entity(name = "category")
public class Category {

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
    @JoinColumn(name = "time_id")
    private Time time;

    /**
     * TypeOfPhrase.
     */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "type_id")
    private Type type;

    /**
     * Description.
     */
    private String description;

    /**
     * Number of phrases in category.
     */
    @Column(name = "number_of_sentences")
    private int numberOfSentences;

    /**
     * Constructor.
     */
    public Category() {
    }

    /**
     * Constructor.
     *
     * @param name            category name.
     * @param time    TimeOfPhrase.
     * @param type    TypeOfPhrase.
     * @param description     description.
     * @param numberOfSentences Number of phrases in category.
     */
    public Category(
            final String name,
            final Time time,
            final Type type,
            final String description,
            final int numberOfSentences) {
        this.name = name;
        this.time = time;
        this.type = type;
        this.description = description;
        this.numberOfSentences = numberOfSentences;
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

    public Time getTime() {
        return time;
    }

    public void setTime(final Time time) {
        this.time = time;
    }

    public Type getType() {
        return type;
    }

    public void setType(final Type type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(final String description) {
        this.description = description;
    }

    public int getNumberOfSentences() {
        return numberOfSentences;
    }

    public void setNumberOfSentences(final int numberOfSentences) {
        this.numberOfSentences = numberOfSentences;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Category)) {
            return false;
        }
        Category that = (Category) o;
        return Objects.equals(getId(), that.getId())
                && Objects.equals(getName(), that.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getName());
    }

    @Override
    public String toString() {
        return String.format(
                "SentenceCategory {id=%s, name=%s, time=%s, type=%s, desc=%s, numberOfSentences=%s}",
                getId(),
                getName(),
                getTime(),
                getType(),
                getDescription(),
                getNumberOfSentences());
    }
}
