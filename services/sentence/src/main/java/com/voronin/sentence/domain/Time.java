package com.voronin.sentence.domain;

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
@Entity(name = "times")
public class Time {

    /**
     * Id.
     */
    @Id
    @org.hibernate.annotations.Type(type = "pg-uuid")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    /**
     * Time of sentence.
     */
    @Column(name = "time")
    private String time;

    /**
     * Constructor.
     *
     * @param time Time Of sentence.
     */
    public Time(final String time) {
        this.time = time;
    }

    /**
     * Constructor.
     */
    public Time() {
    }

    public UUID getId() {
        return id;
    }

    public void setId(final UUID id) {
        this.id = id;
    }

    public String getTime() {
        return time;
    }

    public void setTime(final String time) {
        this.time = time;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Time)) {
            return false;
        }
        Time time1 = (Time) o;
        return Objects.equals(getId(), time1.getId())
                && Objects.equals(getTime(), time1.getTime());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getTime());
    }

    @Override
    public String toString() {
        return String.format("Time {id=%s, time=%s}", getId(), getTime());
    }
}
