package com.voronin.nouns.repository;

import com.voronin.nouns.domain.Exam;
import com.voronin.nouns.domain.UserExamsStats;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

/**
 * UserExamsStats repository class.
 *
 * @author Alexey Voronin.
 * @since 30.08.2019.
 */
@Repository
public interface UserExamsStatsRepository
        extends JpaRepository<UserExamsStats, UUID> {

    /**
     * Get exam stats by user and exam.
     *
     * @param userId user id.
     * @param exam exam.
     * @return UserExamsStats.
     */
    UserExamsStats getUserExamsStatsByUserIdAndExam(UUID userId, Exam exam);

    /**
     * Get user exam stats by user.
     *
     * @param userId user id.
     * @return UserExamsStats.
     */
    List<UserExamsStats> getUserExamsStatsByUserId(UUID userId);
}
