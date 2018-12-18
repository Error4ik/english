package com.voronin.english.repository;

import com.voronin.english.domain.Exam;
import com.voronin.english.domain.User;
import com.voronin.english.domain.UserExamsStats;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

/**
 * TODO: comment.
 *
 * @author Alexey Voronin.
 * @since 12.12.2018.
 */
@Repository
public interface UserExamsStatsRepository extends JpaRepository<UserExamsStats, UUID> {

    UserExamsStats getUserExamsStatsByUserAndExam(final User user, final Exam exam);

    UserExamsStats getUserExamsStatsByUser(final User user);
}
