package com.voronin.sentence.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.FetchType;
import javax.persistence.Column;
import java.util.UUID;

import java.sql.Timestamp;

/**
 * User exam stats.
 *
 * @author Alexey Voronin.
 * @since 10.10.2019.
 */
@Entity(name = "user_stats")
public class UserExam {

    /**
     * Id.
     */
    @Id
    @org.hibernate.annotations.Type(type = "pg-uuid")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    /**
     * User.
     */
    @JoinColumn(name = "user_id")
    private UUID userId;

    /**
     * Exam.
     */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "exam_id")
    private Exam exam;

    /**
     * Total question in exam.
     */
    @Column(name = "total_questions")
    private int totalQuestions;

    /**
     * Correct answer.
     */
    @Column(name = "correct_answers")
    private int correctAnswers;

    /**
     * Date of exam.
     */
    @Column(name = "date")
    private Timestamp date;

    /**
     * Constructor.
     */
    public UserExam() {
    }

    /**
     * Constructor.
     * @param userId user id.
     * @param exam exam.
     * @param totalQuestions Total question in exam.
     * @param correctAnswers Correct answers.
     */
    public UserExam(final UUID userId, final Exam exam, final int totalQuestions, final int correctAnswers) {
        this.userId = userId;
        this.exam = exam;
        this.totalQuestions = totalQuestions;
        this.correctAnswers = correctAnswers;
        this.date = new Timestamp(System.currentTimeMillis());
    }

    public UUID getId() {
        return id;
    }

    public void setId(final UUID id) {
        this.id = id;
    }

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(final UUID userId) {
        this.userId = userId;
    }

    public Exam getExam() {
        return exam;
    }

    public void setExam(final Exam exam) {
        this.exam = exam;
    }

    public int getTotalQuestions() {
        return totalQuestions;
    }

    public void setTotalQuestions(final int totalQuestions) {
        this.totalQuestions = totalQuestions;
    }

    public int getCorrectAnswers() {
        return correctAnswers;
    }

    public void setCorrectAnswers(final int correctAnswers) {
        this.correctAnswers = correctAnswers;
    }

    public Timestamp getDate() {
        return date;
    }

    public void setDate(final Timestamp date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return String.format("UserExam {id=%s, user=%s}", getId(), getUserId());
    }
}
