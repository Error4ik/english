package com.voronin.nouns.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.Id;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.ManyToOne;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import java.util.Objects;
import java.util.UUID;

/**
 * Translation.
 *
 * @author Alexey Voronin.
 * @since 20.09.2018.
 */
@Entity(name = "translations")
public class Translation {

    /**
     * Id.
     */
    @Id
    @org.hibernate.annotations.Type(type = "pg-uuid")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    /**
     * Translation noun.
     */
    private String translation;

    /**
     * Noun.
     */
    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "noun_id")
    private Noun noun;

    /**
     * Empty constructor.
     */
    public Translation() {
    }

    /**
     * Constructor.
     *
     * @param translation noun translation.
     * @param noun        noun.
     */
    public Translation(final String translation, final Noun noun) {
        this.translation = translation;
        this.noun = noun;
    }

    public UUID getId() {
        return id;
    }

    public void setId(final UUID id) {
        this.id = id;
    }

    public String getTranslation() {
        return translation;
    }

    public void setTranslation(final String translation) {
        this.translation = translation;
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
        if (!(o instanceof Translation)) {
            return false;
        }
        Translation that = (Translation) o;
        return Objects.equals(getId(), that.getId())
                && Objects.equals(getTranslation(), that.getTranslation());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getTranslation());
    }

    @Override
    public String toString() {
        return String.format("Translation {id=%s translation=%s}", getId(), getTranslation());
    }
}
