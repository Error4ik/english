package com.voronin.english.service;

import com.voronin.english.domain.Role;
import com.voronin.english.domain.User;
import org.assertj.core.util.Lists;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashSet;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

/**
 * TODO: comment.
 *
 * @author Alexey Voronin.
 * @since 30.11.2018.
 */
@RunWith(SpringRunner.class)
@WebMvcTest(DetailService.class)
@WithMockUser(username = "user", roles = {"USER"})
public class DetailServiceTest {

    @Autowired
    private DetailService detailService;

    @MockBean
    private UserService userService;


    @Test(expected = DisabledException.class)
    public void whenUserEqualsNullShouldThrowException() {
        when(userService.getUserByEmail("test")).thenReturn(null);

        detailService.loadUserByUsername("test");
    }

    @Test
    public void whenValidUserShouldReturnUser() {
        Role role = new Role();
        role.setRole("user");
        User user = new User("test@test.ru", "password", new HashSet<>(Lists.newArrayList(role)));
        when(userService.getUserByEmail("test@test.ru")).thenReturn(user);

        UserDetails userDetails = detailService.loadUserByUsername("test@test.ru");

        assertThat(userDetails.getUsername(), is(user.getEmail()));
    }
}
