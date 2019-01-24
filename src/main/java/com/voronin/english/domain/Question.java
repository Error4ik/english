package com.voronin.english.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.ManyToOne;
import javax.persistence.JoinColumn;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.JoinTable;
import java.util.Set;
import java.util.UUID;

/**
 * Question.
 *
 * @author Alexey Voronin.
 * @since 10.12.2018.
 */
@Entity(name = "questions")
public class Question {

    /**
     * Id.
     */
    @Id
    @org.hibernate.annotations.Type(type = "pg-uuid")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    /**
     * Correct answer.
     */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "word_id")
    private Word word;

    /**
     * Exam for question.
     */
    @JsonIgnore
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "exam_id")
    private Exam exam;

    /**
     * Answer choice.
     */
    @OneToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "question_words", joinColumns = @JoinColumn(name = "question_id"),
            inverseJoinColumns = @JoinColumn(name = "word_id"))
    private Set<Word> words;

    /**
     * Empty constructor.
     */
    public Question() {
    }

    /**
     * Constructor.
     * @param word Correct answer.
     * @param exam Exam for question.
     * @param words Answer choice.
     */
    public Question(final Word word, final Exam exam, final Set<Word> words) {
        this.word = word;
        this.exam = exam;
        this.words = words;
    }

    public UUID getId() {
        return id;
    }

    public void setId(final UUID id) {
        this.id = id;
    }

    public Word getWord() {
        return word;
    }

    public void setWord(final Word word) {
        this.word = word;
    }

    public Exam getExam() {
        return exam;
    }

    public void setExam(final Exam exam) {
        this.exam = exam;
    }

    public Set<Word> getWords() {
        return words;
    }

    public void setWords(final Set<Word> words) {
        this.words = words;
    }

    @Override
    public String toString() {
        return String.format("Question{id=%s, word=%s}", getId(), getWord());
    }
}
