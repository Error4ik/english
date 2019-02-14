package com.voronin.english.service;

import com.google.common.collect.Lists;
import com.voronin.english.domain.User;
import com.voronin.english.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
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
     * CustomEmailService.
     */
    private final CustomEmailService customEmailService;

    /**
     * Path for activate user.
     */
    @Value("${auth.user.activate.path}")
    private String activatePath;

    /**
     * Constructor.
     *
     * @param userRepository     user repository.
     * @param encoder            bcrypt password encoder.
     * @param roleService        role service.
     * @param customEmailService CustomEmailService.
     */
    @Autowired
    public UserService(
            final UserRepository userRepository,
            final BCryptPasswordEncoder encoder,
            final RoleService roleService,
            final CustomEmailService customEmailService) {
        this.userRepository = userRepository;
        this.encoder = encoder;
        this.roleService = roleService;
        this.customEmailService = customEmailService;
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
     * Save User to db.
     *
     * @param user User.
     * @return saved entity.
     */
    public User save(final User user) {
        return this.userRepository.save(user);
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
            user.setPassword(encoder.encode(user.getPassword()));
            user.setRoles(new HashSet<>(Lists.newArrayList(this.roleService.findRoleByName("user"))));
            user.setActivationKey(UUID.randomUUID().toString());
            result = Optional.of(this.userRepository.save(user));
            String subject = "Activated account for ~ english.ru";
            customEmailService.send(user.getEmail(), subject, String.format(
                            "%s/activate/%s",
                            activatePath,
                            user.getActivationKey()));
        } catch (DataIntegrityViolationException | UnsupportedEncodingException e) {
            logger.error(e.getMessage());
        }
        return result;
    }

    /**
     * Activate user by key.
     *
     * @param activationKey user key.
     * @return User.
     */
    public User activateUser(final String activationKey) {
        User user = this.userRepository.getUserByActivationKey(activationKey);
        if (user != null && !user.isActive()) {
            user.setActive(true);
            this.userRepository.save(user);
        }
        return user;
    }
}
