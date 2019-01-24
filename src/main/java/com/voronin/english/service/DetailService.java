package com.voronin.english.service;

import com.voronin.english.domain.Role;
import com.voronin.english.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

/**
 * Detail service, implementation UserDetailService.
 *
 * @author Alexey Voronin.
 * @since 27.10.2018.
 */
@Component
public class DetailService implements UserDetailsService {

    /**
     * User service.
     */
    private final UserService userService;

    /**
     * Constructor.
     *
     * @param userService user service.
     */
    @Autowired
    public DetailService(final UserService userService) {
        this.userService = userService;
    }

    /**
     * Load user by email from database.
     *
     * @param email user email.
     * @return user or throw DisabledException if email or password incorrect.
     */
    @Override
    public UserDetails loadUserByUsername(final String email) {
        final User user = this.userService.getUserByEmail(email);
        if (user != null) {
            Set<GrantedAuthority> roles = new HashSet<>();
            for (Role role : user.getRoles()) {
                roles.add(new SimpleGrantedAuthority(role.getRole()));
            }
            return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), roles);
        } else {
            throw new DisabledException("Логин или пароль не совпадают.");
        }
    }
}
