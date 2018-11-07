package com.voronin.english.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Objects;
import java.util.UUID;

/**
 * TODO: comment.
 *
 * @author Alexey Voronin.
 * @since 20.09.2018.
 */
@Entity(name = "phrases")
public class Phrase {

    @Id
    @org.hibernate.annotations.Type(type = "pg-uuid")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    private String phrase;

    private String translate;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "word_id")
    private Word word;

    public Phrase() {
    }

    public Phrase(String phrase, String translate, Word word) {
        this.phrase = phrase;
        this.translate = translate;
        this.word = word;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getPhrase() {
        return phrase;
    }

    public void setPhrase(String phrase) {
        this.phrase = phrase;
    }

    public String getTranslate() {
        return translate;
    }

    public void setTranslate(String translate) {
        this.translate = translate;
    }

    public Word getWord() {
        return word;
    }

    public void setWord(Word word) {
        this.word = word;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Phrase)) return false;
        Phrase phrase1 = (Phrase) o;
        return Objects.equals(getId(), phrase1.getId()) &&
                Objects.equals(getPhrase(), phrase1.getPhrase()) &&
                Objects.equals(getTranslate(), phrase1.getTranslate());
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
