package com.voronin.english.domain;

import javax.persistence.Id;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Entity;
import javax.persistence.GenerationType;
import java.util.Objects;
import java.util.UUID;

/**
 * The time at which the phrase is located, such as future, present, or past.
 *
 * @author Alexey Voronin.
 * @since 01.02.2019.
 */
@Entity(name = "time_of_phrase")
public class TimeOfPhrase {

    /**
     * Id.
     */
    @Id
    @org.hibernate.annotations.Type(type = "pg-uuid")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    /**
     * Time of phrase.
     */
    @Column(name = "time_of_phrase")
    private String timeOfPhrase;

    /**
     * Constructor.
     *
     * @param timeOfPhrase TimeOfPhrase.
     */
    public TimeOfPhrase(final String timeOfPhrase) {
        this.timeOfPhrase = timeOfPhrase;
    }

    /**
     * Constructor.
     */
    public TimeOfPhrase() {
    }

    public UUID getId() {
        return id;
    }

    public void setId(final UUID id) {
        this.id = id;
    }

    public String getTimeOfPhrase() {
        return timeOfPhrase;
    }

    public void setTimeOfPhrase(final String timeOfPhrase) {
        this.timeOfPhrase = timeOfPhrase;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TimeOfPhrase)) {
            return false;
        }
        TimeOfPhrase that = (TimeOfPhrase) o;
        return Objects.equals(getId(), that.getId())
                && Objects.equals(getTimeOfPhrase(), that.getTimeOfPhrase());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getTimeOfPhrase());
    }

    @Override
    public String toString() {
        return String.format("TimeOfPhrase{id=%s, timeOfPhrase=%s}", getId(), getTimeOfPhrase());
    }
}
