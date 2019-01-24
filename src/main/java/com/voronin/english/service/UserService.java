package com.voronin.english.service;

import com.google.common.collect.Lists;
import com.voronin.english.domain.User;
import com.voronin.english.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.UUID;

/**
 * User service class.
 *
 * @author Alexey Voronin.
 * @since 10.10.2018.
 */
@Service
public class UserService {

    /**
     * Logger.
     */
    private final Logger logger = LoggerFactory.getLogger(UserService.class);

    /**
     * User repository.
     */
    private final UserRepository userRepository;

    /**
     * BCrypt password encoder.
     */
    private final BCryptPasswordEncoder encoder;

    /**
     * Role service.
     */
    private final RoleService roleService;

    /**
     * Constructor.
     *
     * @param userRepository user repository.
     * @param encoder        bcrypt password encoder.
     * @param roleService    role service.
     */
    @Autowired
    public UserService(
            final UserRepository userRepository,
            final BCryptPasswordEncoder encoder,
            final RoleService roleService) {
        this.userRepository = userRepository;
        this.encoder = encoder;
        this.roleService = roleService;
    }

    /**
     * Get user by id.
     *
     * @param id id.
     * @return User.
     */
    public User getUserById(final UUID id) {
        return this.userRepository.getOne(id);
    }

    /**
     * Get user by email.
     *
     * @param email email.
     * @return User.
     */
    public User getUserByEmail(final String email) {
        return this.userRepository.getUserByEmail(email);
    }

    /**
     * Registration user.
     *
     * @param user user.
     * @return Optional User or DataIntegrityViolationException if registration failed.
     */
    public Optional<User> regUser(final User user) {
        Optional<User> result = Optional.empty();
        try {
            final String pass = user.getPassword();
            user.setPassword(encoder.encode(user.getPassword()));
            user.setRoles(new HashSet<>(Lists.newArrayList(this.roleService.findRoleByName("user"))));
            result = Optional.of(this.userRepository.save(user));
        } catch (DataIntegrityViolationException e) {
            logger.error(e.getMessage());
        }
        return result;
    }
}
