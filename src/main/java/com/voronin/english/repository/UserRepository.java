package com.voronin.english.repository;

import com.voronin.english.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

/**
 * User repository.
 *
 * @author Alexey Voronin.
 * @since 10.10.2018.
 */
@Repository
public interface UserRepository extends JpaRepository<User, UUID> {

    /**
     * Get user by email.
     *
     * @param email email.
     * @return user.
     */
    User getUserByEmail(String email);

    /**
     * Get user by key.
     *
     * @param key user key.
     * @return User.
     */
    User getUserByActivationKey(String key);

    /**
     * Get all users ordered by create date.
     *
     * @return list of User.
     */
    List<User> getAllByOrderByCreateDate();
}
