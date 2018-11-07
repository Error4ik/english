package com.voronin.english.domain;

import javax.persistence.*;
import java.util.Objects;
import java.util.UUID;

/**
 * TODO: comment.
 *
 * @author Alexey Voronin.
 * @since 19.09.2018.
 */
@Entity(name = "part_of_speech")
public class PartOfSpeech {

    @Id
    @org.hibernate.annotations.Type(type = "pg-uuid")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(name = "part_of_speech")
    private String partOfSpeech;

    public PartOfSpeech() {
    }

    public PartOfSpeech(String partOfSpeech) {
        this.partOfSpeech = partOfSpeech;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getPartOfSpeech() {
        return partOfSpeech;
    }

    public void setPartOfSpeech(String partOfSpeech) {
        this.partOfSpeech = partOfSpeech;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PartOfSpeech)) return false;
        PartOfSpeech that = (PartOfSpeech) o;
        return Objects.equals(getId(), that.getId()) &&
                Objects.equals(getPartOfSpeech(), that.getPartOfSpeech());
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
