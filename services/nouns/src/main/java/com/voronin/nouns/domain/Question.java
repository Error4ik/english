package com.voronin.nouns.domain;

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
 * @since 29.08.2019.
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
    @JoinColumn(name = "noun_id")
    private Noun noun;

    /**
     * Answer choice.
     */
    @OneToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "question_words", joinColumns = @JoinColumn(name = "question_id"),
            inverseJoinColumns = @JoinColumn(name = "noun_id"))
    private Set<Noun> nouns;

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
     * @param noun Correct answer.
     * @param exam Exam for question.
     * @param nouns Answer choice.
     */
    public Question(final Noun noun, final Exam exam, final Set<Noun> nouns) {
        this.noun = noun;
        this.exam = exam;
        this.nouns = nouns;
    }

    public UUID getId() {
        return id;
    }

    public void setId(final UUID id) {
        this.id = id;
    }

    public Noun getNoun() {
        return noun;
    }

    public void setWord(final Noun word) {
        this.noun = noun;
    }

    public Exam getExam() {
        return exam;
    }

    public void setExam(final Exam exam) {
        this.exam = exam;
    }

    public Set<Noun> getNouns() {
        return nouns;
    }

    public void setNouns(final Set<Noun> nouns) {
        this.nouns = nouns;
    }

    @Override
    public String toString() {
        return String.format("Question {id=%s, noun=%s}", getId(), getNoun());
    }
}
