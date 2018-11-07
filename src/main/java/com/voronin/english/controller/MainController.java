package com.voronin.english.controller;

import com.voronin.english.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

/**
 * TODO: comment.
 *
 * @author Alexey Voronin.
 * @since 21.08.2018.
 */
@RestController
public class MainController {

    @Autowired
    private UserService userService;

    @RequestMapping("/")
    public Principal getPrincipal(Principal principal) {
        return principal == null ? () -> "EMPTY" : principal;
    }

    @RequestMapping("/user/current")
    public Principal getUserById(Principal principal) {
        return principal == null ? () -> "EMPTY" : principal;
    }
}
