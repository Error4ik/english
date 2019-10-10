package com.voronin.sentence.domain;

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
import java.util.List;
import java.util.UUID;

/**
 * Question for exam.
 *
 * @author Alexey Voronin.
 * @since 04.10.2019.
 */
@Entity(name = "questions")
public class Question {

    /**
     * id.
     */
    @Id
    @org.hibernate.annotations.Type(type = "pg-uuid")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    /**
     * Some question.
     */
    private String question;

    /**
     * Key words for question.
     */
    @OneToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "question_key_words", joinColumns = @JoinColumn(name = "question_id"),
            inverseJoinColumns = @JoinColumn(name = "key_word_id"))
    private List<KeyWord> keyWords;

    /**
     * Exam for question.
     */
    @JsonIgnore
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "exam_id")
    private Exam exam;

    /**
     * Correct answer.
     */
    private String answer;

    /**
     * Constructor.
     */
    public Question() {
    }

    /**
     * Constructor.
     *
     * @param question question.
     * @param answer   answer.
     */
    public Question(final String question, final String answer) {
        this.question = question;
        this.answer = answer;
    }

    public UUID getId() {
        return id;
    }

    public void setId(final UUID id) {
        this.id = id;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(final String question) {
        this.question = question;
    }

    public List<KeyWord> getKeyWords() {
        return keyWords;
    }

    public void setKeyWords(final List<KeyWord> keyWords) {
        this.keyWords = keyWords;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(final String answer) {
        this.answer = answer;
    }

    public Exam getExam() {
        return exam;
    }

    public void setExam(final Exam exam) {
        this.exam = exam;
    }

    @Override
    public String toString() {
        return String.format(
                "Question {id=%s, question=%s, keyWords=%s, answer=%s}",
                getId(),
                getQuestion(),
                getKeyWords(),
                getAnswer());
    }
}
