package com.voronin.auth.service;

import com.google.common.collect.Lists;
import com.voronin.auth.domain.Message;
import com.voronin.auth.domain.Role;
import com.voronin.auth.domain.User;
import com.voronin.auth.exception.ApiRequestException;
import com.voronin.auth.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.security.Principal;
import java.util.HashSet;
import java.util.List;
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
     * @return Optional User or Optional.empty if registration failed.
     */
    public User regUser(final User user) {
        logger.debug(String.format("Arguments - %s", user));
        User newUser = new User();
        newUser.setPassword(encoder.encode(user.getPassword()));
        newUser.setRoles(new HashSet<>(Lists.newArrayList(this.roleService.findRoleByName("user"))));
        newUser.setActivationKey(UUID.randomUUID().toString());
        newUser.setEmail(user.getEmail().toLowerCase());
        try {
            this.save(newUser);
        } catch (DataIntegrityViolationException e) {
            logger.error(e.getMessage());
            throw new ApiRequestException("Пользователь с такой почтой уже зарегистрирован.");
        }
        sendMessage(newUser);
        logger.debug(String.format("Return - %s", user));
        return user;
    }

    /**
     * Activate user by key.
     *
     * @param activationKey user key.
     * @return User.
     */
    public User activateUser(final String activationKey) {
        logger.debug(String.format("Arguments - %s", activationKey));
        User user = this.userRepository.getUserByActivationKey(activationKey);
        if (user != null && !user.isActive()) {
            user.setActive(true);
            this.userRepository.save(user);
        }
        logger.debug(String.format("Return - %s", user));
        return user;
    }

    /**
     * Return all users.
     *
     * @return list of User.
     */
    public List<User> getUsers() {
        return this.userRepository.getAllByOrderByCreateDate();
    }

    /**
     * Change User role or add new role.
     *
     * @param userId User id.
     * @param roleId Role id.
     * @return the user for whom the role was changed.
     */
    public User changeUserRole(final UUID userId, final UUID roleId) {
        logger.debug(String.format("Arguments - userId - %s, roleId - %s", userId, roleId));
        User user = this.getUserById(userId);
        Role role = roleService.getRoleById(roleId);
        if (user != null && role != null) {
            logger.debug(String.format("User before change role - %s", user));
            if (user.getRoles().contains(role)) {
                user.getRoles().remove(role);
            } else {
                user.getRoles().add(role);
            }
            user = this.save(user);
            logger.debug(String.format("Saved user - %s", user));
        }
        return user;
    }

    /**
     * Change user email.
     *
     * @param principal current principal.
     * @param newEmail  new user email.
     * @param password  user password.
     * @return User or error if the old password is incorrect.
     */
    public User updateEmail(final Principal principal, final String newEmail, final String password) {
        logger.debug(String.format("Arguments - %s %s", principal, newEmail));
        User user = this.userRepository.getUserByEmail(principal.getName());

        if (!encoder.matches(password, user.getPassword())) {
            throw new ApiRequestException("Не верный пароль.");
        }

        if (user.getEmail().equalsIgnoreCase(newEmail)) {
            throw new ApiRequestException("Вы ввели старую почту.");
        }

        user.setEmail(newEmail);
        user.setActivationKey(UUID.randomUUID().toString());
        user.setActive(false);
        try {
            this.save(user);
        } catch (DataIntegrityViolationException e) {
            logger.error(e.getMessage());
            throw new ApiRequestException("Пользователь с такой почтой уже зарегистрирован.");
        }
        sendMessage(user);
        logger.debug(String.format("Return - %s", user));
        return user;
    }

    /**
     * Update user password.
     *
     * @param principal current User.
     * @param oldPass   old user password.
     * @param newPass   new user password.
     * @return User or error if the old password is incorrect.
     */
    public User updatePassword(final Principal principal, final String oldPass, final String newPass) {
        logger.debug(String.format("Arguments - %s", principal));
        User user = this.userRepository.getUserByEmail(principal.getName());

        if (!encoder.matches(oldPass, user.getPassword())) {
            throw new ApiRequestException("Incorrect old password.");
        }
        user.setPassword(encoder.encode(newPass));
        this.save(user);

        logger.debug(String.format("Password isChanged? - %s", user));
        return user;
    }

    /**
     * Send the activation code to the user's email.
     *
     * @param user user.
     */
    private void sendMessage(final User user) {
        try {
            customEmailService.send(
                    new Message(
                            user.getEmail(),
                            "Activated account for ~ english.ru",
                            String.format("%s/activate/%s", activatePath, user.getActivationKey())));
        } catch (UnsupportedEncodingException e) {
            logger.error(e.getMessage());
        }
    }
}
