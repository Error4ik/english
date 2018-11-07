package com.voronin.english.service;

import com.google.common.collect.Lists;
import com.voronin.english.domain.User;
import com.voronin.english.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

/**
 * TODO: comment.
 *
 * @author Alexey Voronin.
 * @since 10.10.2018.
 */
@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder encoder;

    @Autowired
    private RoleService roleService;

    public User getUserById(final UUID id) {
        return this.userRepository.getOne(id);
    }

    public User getUserByEmail(final String email) {
        return this.userRepository.getUserByEmail(email);
    }


    public Optional<User> regUser(final User user) {
        Optional<User> result = Optional.empty();
        try {
            final String pass = user.getPassword();
            user.setPassword(encoder.encode(user.getPassword()));
            user.setRoles(Lists.newArrayList(this.roleService.findRoleByName("user")));
//            user.setCreateDate(Timestamp.valueOf(LocalDateTime.now()));
//            user.setLastVisit(Timestamp.valueOf(LocalDateTime.now()));
            result = Optional.of(this.userRepository.save(user));
        } catch (DataIntegrityViolationException e) {
            System.out.println(e.getMessage());
        }
        return result;
    }
}
