package com.voronin.english.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

/**
 * User controller.
 *
 * @author Alexey Voronin.
 * @since 21.08.2018.
 */
@RestController
@RequestMapping("/user")
public class UserController {

    /**
     * Return current user.
     * @param principal user.
     * @return user or null if user is not authorise.
     */
    @RequestMapping("/current")
    public Principal getUserById(final Principal principal) {
        return principal == null ? () -> "Not an authorized user" : principal;
    }
}
