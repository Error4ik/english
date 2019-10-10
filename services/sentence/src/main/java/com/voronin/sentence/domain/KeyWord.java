package com.voronin.sentence.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.UUID;

/**
 * Key word for question.
 *
 * @author Alexey Voronin.
 * @since 04.10.2019.
 */
@Entity(name = "key_words")
public class KeyWord {

    /**
     * Id.
     */
    @Id
    @org.hibernate.annotations.Type(type = "pg-uuid")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    /**
     * Word.
     */
    private String word;

    /**
     * Constructor.
     */
    public KeyWord() {
    }

    /**
     * Constructor.
     *
     * @param id   id.
     * @param word word.
     */
    public KeyWord(final UUID id, final String word) {
        this.id = id;
        this.word = word;
    }

    public UUID getId() {
        return id;
    }

    public void setId(final UUID id) {
        this.id = id;
    }

    public String getWord() {
        return word;
    }

    public void setWord(final String word) {
        this.word = word;
    }

    @Override
    public String toString() {
        return String.format("KeyWord {id=%s, word=%s}", getId(), getWord());
    }
}
