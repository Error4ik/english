package com.voronin.english.controller;

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

    @RequestMapping("/user/current")
    public Principal getUserById(Principal principal) {
        return principal == null ? () -> "EMPTY" : principal;
    }
}
