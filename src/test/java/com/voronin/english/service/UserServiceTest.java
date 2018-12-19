package com.voronin.english.service;

import com.voronin.english.domain.Role;
import com.voronin.english.domain.User;
import com.voronin.english.repository.UserRepository;
import org.assertj.core.util.Lists;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashSet;
import java.util.Optional;
import java.util.UUID;

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
@WebMvcTest(UserService.class)
@WithMockUser(username = "user", roles = {"USER"})
public class UserServiceTest {

    @Autowired
    private UserService userService;
    @MockBean
    private BCryptPasswordEncoder encoder;

    @MockBean
    private UserRepository userRepository;
    @MockBean
    private RoleService roleService;

    private User user;
    private UUID uuid = UUID.randomUUID();

    @Before
    public void init() {
        user = new User("user@user.ru", "password", new HashSet<>(Lists.newArrayList(new Role())));
        user.setId(uuid);
    }

    @Test
    public void whenGetUserByIdShouldReturnUser() throws Exception {
        when(userRepository.getOne(uuid)).thenReturn(user);

        assertThat(userService.getUserById(uuid), is(user));
    }

    @Test
    public void whenGetUserByEmailShouldReturnUser() throws Exception {
        when(userRepository.getUserByEmail(user.getEmail())).thenReturn(user);

        assertThat(userService.getUserByEmail(user.getEmail()), is(user));
    }

    @Test
    public void whenRegUserShouldReturnOptionalUser() throws Exception {
        Optional<User> optional = Optional.of(user);
        when(userRepository.save(user)).thenReturn(user);

        assertThat(userService.regUser(user), is(optional));
    }
}
