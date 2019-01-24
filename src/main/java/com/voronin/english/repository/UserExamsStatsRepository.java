package com.voronin.english.repository;

import com.voronin.english.domain.Exam;
import com.voronin.english.domain.User;
import com.voronin.english.domain.UserExamsStats;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

/**
 * User exam stats repository.
 *
 * @author Alexey Voronin.
 * @since 12.12.2018.
 */
@Repository
public interface UserExamsStatsRepository extends JpaRepository<UserExamsStats, UUID> {

    /**
     * Get exam stats by user and exam.
     *
     * @param user user.
     * @param exam exam.
     * @return UserExamsStats.
     */
    UserExamsStats getUserExamsStatsByUserAndExam(User user, Exam exam);

    /**
     * Get user exam stats by user.
     *
     * @param user user.
     * @return UserExamsStats.
     */
    List<UserExamsStats> getUserExamsStatsByUser(User user);
}
