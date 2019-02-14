package com.voronin.english.service;

import com.voronin.english.domain.Role;
import com.voronin.english.domain.User;
import com.voronin.english.repository.UserRepository;
import org.assertj.core.util.Lists;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashSet;
import java.util.Optional;
import java.util.UUID;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

/**
 * UserService test class.
 *
 * @author Alexey Voronin.
 * @since 30.11.2018.
 */
@RunWith(SpringRunner.class)
@WebMvcTest(UserService.class)
@WithMockUser(username = "user", roles = {"USER"})
public class UserServiceTest {

    /**
     * The class object under test.
     */
    @Autowired
    private UserService userService;

    /**
     * Mock BCryptPasswordEncoder.
     */
    @MockBean
    private BCryptPasswordEncoder encoder;

    /**
     * Mock UserRepository.
     */
    @MockBean
    private UserRepository userRepository;

    /**
     * Mock RoleService.
     */
    @MockBean
    private RoleService roleService;

    /**
     * Mock CustomEmailService.
     */
    @MockBean
    private CustomEmailService customEmailService;

    /**
     * Mock JavaMailSender.
     */
    @MockBean
    private JavaMailSender javaMailSender;

    /**
     * Path for activate user.
     */
    @Value("${auth.user.activate.path}")
    private String activatePath;

    /**
     * Class fo test.
     */
    private User user;

    /**
     * UUID id for test.
     */
    private UUID uuid = UUID.randomUUID();

    /**
     * initialization of objects for the tests.
     */
    @Before
    public void init() {
        user = new User("user@user.ru", "password", new HashSet<>(Lists.newArrayList(new Role())));
        user.setId(uuid);
    }

    /**
     * When call getUserById should return user.
     *
     * @throws Exception exception.
     */
    @Test
    public void whenGetUserByIdShouldReturnUser() throws Exception {
        when(userRepository.getOne(uuid)).thenReturn(user);

        assertThat(userService.getUserById(uuid), is(user));
    }

    /**
     * When call getUserByEmail should return user.
     *
     * @throws Exception exception.
     */
    @Test
    public void whenGetUserByEmailShouldReturnUser() throws Exception {
        when(userRepository.getUserByEmail(user.getEmail())).thenReturn(user);

        assertThat(userService.getUserByEmail(user.getEmail()), is(user));
    }

    /**
     * When call regUser with new user should return Optional user.
     *
     * @throws Exception exception.
     */
    @Test
    public void whenRegUserWithNewUserShouldReturnOptionalUser() throws Exception {
        Optional<User> optional = Optional.of(user);
        when(userRepository.save(user)).thenReturn(user);

        assertThat(userService.regUser(user), is(optional));
    }

    /**
     * When call regUser with exist user should return Optional empty.
     *
     * @throws Exception exception.
     */
    @Test
    public void whenRegUserWithExistUsetShouldReturnOptionalUser() throws Exception {
        Optional<User> optional = Optional.empty();
        when(userRepository.save(user)).thenThrow(DataIntegrityViolationException.class);

        assertThat(userService.regUser(user), is(optional));
    }

    /**
     * When call save should return saved entity.
     *
     * @throws Exception exception.
     */
    @Test
    public void whenSaveShouldReturnSavedEntity() throws Exception {
        when(userRepository.save(user)).thenReturn(user);

        assertThat(userService.save(user), is(user));
    }

    /**
     * When user is null should return null.
     *
     * @throws Exception exception.
     */
    @Test
    public void whenActivatedWithUserNullShouldReturnNull() throws Exception {
        when(userRepository.getUserByActivationKey("key")).thenReturn(null);

        assertNull(userService.activateUser("key"));
    }

    /**
     * When the user is not null and the user is not activated, you should activate the user and return it.
     *
     * @throws Exception exception.
     */
    @Test
    public void whenUserIsNotNullButUserIsNotActivatedShouldActivatedUserAndReturnIt() throws Exception {
        when(userRepository.getUserByActivationKey("key")).thenReturn(user);

        assertTrue(userService.activateUser("key").isActive());
    }

    /**
     * When user is not null and user is activated should return that user.
     *
     * @throws Exception exception.
     */
    @Test
    public void whenUserIsNotNulAndUserIsActivatedShouldReturnThatUser() throws Exception {
        user.setActive(true);
        when(userRepository.getUserByActivationKey("key")).thenReturn(user);

        assertThat(userService.activateUser("key").isActive(), is(true));
    }
}
