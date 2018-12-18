package com.voronin.english.domain;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.UUID;

/**
 * TODO: comment.
 *
 * @author Alexey Voronin.
 * @since 12.12.2018.
 */
@Entity(name = "user_exams_stats")
public class UserExamsStats {

    @Id
    @org.hibernate.annotations.Type(type = "pg-uuid")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "exam_id")
    private Exam exam;

    @Column(name = "total_questions")
    private int totalQuestions;

    @Column(name = "correct_answer")
    private int correctAnswer;

    @Column(name = "date_of_the_exam")
    private Timestamp dateOfTheExam;

    public UserExamsStats() {
    }

    public UserExamsStats(User user, Exam exam, int totalQuestions, int correctAnswer) {
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
        return "UserExamsStats{" +
                "id=" + id +
                ", user=" + user +
                ", totalQuestions=" + totalQuestions +
                ", correctAnswer=" + correctAnswer +
                '}';
    }
}
