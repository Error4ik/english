package com.voronin.sentence.repository;

import com.voronin.sentence.domain.UserExam;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

/**
 * UserExam Repository.
 *
 * @author Alexey Voronin.
 * @since 10.10.2019.
 */
@Repository
public interface UserExamRepository extends JpaRepository<UserExam, UUID> {

    /**
     * Get list of UserExams by user id.
     *
     * @param id user id.
     * @return List of UserExams.
     */
    List<UserExam> getUserExamByUserId(UUID id);

    /**
     * Get UserExam by user id and exam id.
     *
     * @param userId user id.
     * @param examId exam id.
     * @return UserExam.
     */
    UserExam getUserExamByUserIdAndExamId(UUID userId, UUID examId);
}
