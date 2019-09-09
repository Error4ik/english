package com.voronin.words.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.JoinTable;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.Set;

/**
 * Question class.
 *
 * @author Alexey Voronin.
 * @since 06.09.2019.
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
     * Answer choice.
     */
    @OneToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "question_words", joinColumns = @JoinColumn(name = "question_id"),
            inverseJoinColumns = @JoinColumn(name = "word_id"))
    private Set<Word> words;

    /**
     * Exam for question.
     */
    @JsonIgnore
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "exam_id")
    private Exam exam;

    /**
     * Empty constructor.
     */
    public Question() {
    }

    /**
     * Constructor.
     *
     * @param word  correct answer.
     * @param words list of variants.
     * @param exam  exam.
     */
    public Question(final Word word, final Set<Word> words, final Exam exam) {
        this.word = word;
        this.words = words;
        this.exam = exam;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Word getWord() {
        return word;
    }

    public void setWord(Word word) {
        this.word = word;
    }

    public Set<Word> getWords() {
        return words;
    }

    public void setWords(Set<Word> words) {
        this.words = words;
    }

    public Exam getExam() {
        return exam;
    }

    public void setExam(Exam exam) {
        this.exam = exam;
    }

    @Override
    public String toString() {
        return String.format("Question {id=%s, word=%s}", getId(), getWord());
    }
}
