package com.voronin.nouns.domain;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.JoinColumn;
import javax.persistence.FetchType;
import javax.persistence.Column;
import javax.persistence.ManyToOne;
import java.sql.Timestamp;
import java.util.UUID;

/**
 * User exam stats.
 *
 * @author Alexey Voronin.
 * @since 12.12.2018.
 */
@Entity(name = "user_exams_stats")
public class UserExamsStats {

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
    @Column(name = "correct_answer")
    private int correctAnswer;

    /**
     * Date of exam.
     */
    @Column(name = "date")
    private Timestamp date;

    /**
     * Empty constructor.
     */
    public UserExamsStats() {
    }

    /**
     * Constructor.
     *
     * @param userId         userId.
     * @param exam           exam.
     * @param totalQuestions total question.
     * @param correctAnswer  correct answer.
     */
    public UserExamsStats(
            final UUID userId,
            final Exam exam,
            final int totalQuestions,
            final int correctAnswer) {
        this.userId = userId;
        this.exam = exam;
        this.totalQuestions = totalQuestions;
        this.correctAnswer = correctAnswer;
        this.date = new Timestamp(System.currentTimeMillis());
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
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

    public int getCorrectAnswer() {
        return correctAnswer;
    }

    public void setCorrectAnswer(final int correctAnswer) {
        this.correctAnswer = correctAnswer;
    }

    public Timestamp getDate() {
        return date;
    }

    public void setDate(final Timestamp date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return String.format(
                "UserExamsStats {id=%s, userId=%s, totalQuestions=%s, correctAnswer=%s}",
                getId(),
                getUserId(),
                getTotalQuestions(),
                getCorrectAnswer());
    }
}
