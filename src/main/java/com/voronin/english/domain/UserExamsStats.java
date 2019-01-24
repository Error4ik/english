package com.voronin.english.domain;

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
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;

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
    @Column(name = "date_of_the_exam")
    private Timestamp dateOfTheExam;

    /**
     * Empty constructor.
     */
    public UserExamsStats() {
    }

    /**
     * Constructor.
     *
     * @param user           user.
     * @param exam           exam.
     * @param totalQuestions total question.
     * @param correctAnswer  correct answer.
     */
    public UserExamsStats(final User user, final Exam exam, final int totalQuestions, final int correctAnswer) {
        this.user = user;
        this.exam = exam;
        this.totalQuestions = totalQuestions;
        this.correctAnswer = correctAnswer;
        this.dateOfTheExam = new Timestamp(System.currentTimeMillis());
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Exam getExam() {
        return exam;
    }

    public void setExam(Exam exam) {
        this.exam = exam;
    }

    public int getTotalQuestions() {
        return totalQuestions;
    }

    public void setTotalQuestions(int totalQuestions) {
        this.totalQuestions = totalQuestions;
    }

    public int getCorrectAnswer() {
        return correctAnswer;
    }

    public void setCorrectAnswer(int correctAnswer) {
        this.correctAnswer = correctAnswer;
    }

    public Timestamp getDateOfTheExam() {
        return dateOfTheExam;
    }

    public void setDateOfTheExam(Timestamp dateOfTheExam) {
        this.dateOfTheExam = dateOfTheExam;
    }

    @Override
    public String toString() {
        return String.format(
                "UserExamsStats{id=%s, user=%s, totalQuestions=%s, correctAnswer=%s}",
                getId(),
                getUser(),
                getTotalQuestions(),
                getCorrectAnswer());
    }
}
