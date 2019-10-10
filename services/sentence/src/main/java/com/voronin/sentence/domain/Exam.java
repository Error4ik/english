package com.voronin.sentence.domain;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.JoinColumn;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Enumerated;
import javax.persistence.EnumType;
import javax.persistence.Column;
import java.util.List;
import java.util.UUID;

/**
 * Exam by sentence.
 *
 * @author Alexey Voronin.
 * @since 04.10.2019.
 */
@Entity(name = "exams")
public class Exam {

    /**
     * Id.
     */
    @Id
    @org.hibernate.annotations.Type(type = "pg-uuid")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    /**
     * Exam name.
     */
    private String name;

    /**
     * The questions in the exam.
     */
    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "exam_id")
    private List<Question> questions;

    /**
     * Type of exam.
     */
    @Column(name = "exam_type")
    @Enumerated(EnumType.STRING)
    private ExamType examType;

    /**
     * Constructor.
     */
    public Exam() {
    }

    /**
     * Constructor.
     *
     * @param name     exam name.
     * @param examType exam type.
     */
    public Exam(final String name, final ExamType examType) {
        this.name = name;
        this.examType = examType;
    }

    public UUID getId() {
        return id;
    }

    public void setId(final UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(final List<Question> questions) {
        this.questions = questions;
    }

    public ExamType getExamType() {
        return examType;
    }

    public void setExamType(final ExamType examType) {
        this.examType = examType;
    }

    @Override
    public String toString() {
        return String.format("Exam {id=%s, name=%s, examType=%s}", getId(), getName(), getExamType());
    }

    /**
     * Type of exam.
     */
    public enum ExamType {
        /**
         * Future exam question.
         */
        FUTURE,

        /**
         * Present exam question.
         */
        PRESENT,

        /**
         * Past exam question.
         */
        PAST
    }
}
