package com.voronin.auth.domain;

import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.UUID;

/**
 * @author Alexey Voronin.
 * @since 09.09.2019.
 */
public class Person extends org.springframework.security.core.userdetails.User {

    /**
     * Id.
     */
    private UUID id;

    /**
     * Constructor.
     *
     * @param id          user id.
     * @param username    username.
     * @param password    password.
     * @param authorities authorities.
     */
    public Person(
            final UUID id,
            final String username,
            final String password,
            final Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
        this.id = id;
    }

    public UUID getId() {
        return id;
    }
}
