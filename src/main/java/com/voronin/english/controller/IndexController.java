package com.voronin.english.controller;

import com.google.common.collect.Lists;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * TODO: comment.
 *
 * @author Alexey Voronin.
 * @since 21.08.2018.
 */
@RestController
public class IndexController {

    @RequestMapping("/users")
    public List<String> getIndex() {
        return new ArrayList<>(Lists.newArrayList("User 1", "User 2", "user 3", "USER 5", "UsEr 4"));
    }

    @RequestMapping("/user/{id}")
    public List<String> getUserById(@PathVariable final String id) {
        return new ArrayList<>(Lists.newArrayList("User by id 1"));
    }

    @RequestMapping("/integers")
    public List<Integer> getList() {
        final List<Integer> list = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            list.add(i);
        }
        return list;
    }
}
