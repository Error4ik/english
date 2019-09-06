package com.voronin.words.repository;

import com.voronin.words.domain.Exam;
import com.voronin.words.domain.UserExam;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

/**
 * UserExam repository.
 *
 * @author Alexey Voronin.
 * @since 06.09.2019.
 */
public interface UserExamRepository extends JpaRepository<UserExam, UUID> {

    /**
     * Get UserExam be User id.
     *
     * @param userId User id.
     * @return List of UserExam.
     */
    List<UserExam> getUserExamByUserId(UUID userId);

    /**
     * Get UserExam by User id and Exam.
     *
     * @param userId User id.
     * @param exam   Exam.
     * @return List of UserExam.
     */
    UserExam getUserExamByUserIdAndExam(UUID userId, Exam exam);
}
