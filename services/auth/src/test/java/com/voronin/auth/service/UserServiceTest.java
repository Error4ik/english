package com.voronin.auth.service;

import com.voronin.auth.domain.Message;
import com.voronin.auth.domain.Role;
import com.voronin.auth.domain.User;
import com.voronin.auth.exception.ApiRequestException;
import com.voronin.auth.repository.UserRepository;
import org.assertj.core.util.Lists;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.security.Principal;
import java.util.HashSet;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.catchThrowable;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertFalse;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;

/**
 * UserService test class.
 *
 * @author Alexey Voronin.
 * @since 30.11.2018.
 */
public class UserServiceTest {

    /**
     * Mock BCryptPasswordEncoder.
     */
    private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    /**
     * Mock UserRepository.
     */
    private UserRepository userRepository = mock(UserRepository.class);

    /**
     * Mock RoleService.
     */
    private RoleService roleService = mock(RoleService.class);

    /**
     * Mock CustomEmailService.
     */
    private CustomEmailService customEmailService = mock(CustomEmailService.class);

    /**
     * Principal for test.
     */
    private Principal principal = mock(Principal.class);

    /**
     * The class object under test.
     */
    private UserService userService =
            new UserService(
                    userRepository,
                    encoder,
                    roleService,
                    customEmailService);

    /**
     * Path for activate user.
     */
    @Value("${auth.user.activate.path}")
    private String activatePath;

    /**
     * Class for test.
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
        user = new User("user@user.ru", "password", new HashSet<>(Lists.newArrayList(new Role("user"))));
        user.setId(uuid);
        user.setActive(true);
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
        verify(userRepository, times(1)).getOne(uuid);
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
        verify(userRepository, times(1)).getUserByEmail(user.getEmail());
    }

    /**
     * When call regUser with new user should return Optional user.
     *
     * @throws Exception exception.
     */
    @Test
    public void whenRegUserWithNewUserShouldReturnOptionalUser() throws Exception {
        when(userRepository.save(any(User.class))).thenReturn(user);

        assertThat(userService.regUser(user), is(user));

        verify(userRepository, times(1)).save(any(User.class));
        verify(customEmailService, times(1)).send(any(Message.class));
    }

    /**
     * When call regUser with exist user should return Optional empty.
     *
     * @throws Exception exception.
     */
    @Test(expected = ApiRequestException.class)
    public void whenRegUserWithExistUserShouldReturnOptionalUser() throws Exception {
        when(userRepository.save(any(User.class))).thenThrow(DataIntegrityViolationException.class);

        assertThat(userService.regUser(user), is(user));
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
        verify(userRepository, times(1)).save(user);
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
        verify(userRepository, times(1)).getUserByActivationKey("key");
    }

    /**
     * When the user is not null and the user is not activated, you should activate the user and return it.
     *
     * @throws Exception exception.
     */
    @Test
    public void whenUserIsNotNullButUserIsNotActivatedShouldActivatedUserAndReturnIt() throws Exception {
        when(userRepository.getUserByActivationKey("key")).thenReturn(user);
        this.user.setActive(false);

        assertTrue(userService.activateUser("key").isActive());
        verify(userRepository, times(1)).getUserByActivationKey("key");
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
        verify(userRepository, times(1)).getUserByActivationKey("key");
    }

    /**
     * When getUsers should return list of User.
     *
     * @throws Exception exception.
     */
    @Test
    public void whenGetUsersShouldReturnListOfUser() throws Exception {
        List<User> users = Lists.newArrayList(user, user);
        when(userRepository.getAllByOrderByCreateDate()).thenReturn(users);

        assertThat(this.userService.getUsers(), is(users));
        verify(userRepository, times(1)).getAllByOrderByCreateDate();
    }

    /**
     * When changeUserRole and user roles contains role, should remove that role.
     *
     * @throws Exception exception.
     */
    @Test
    public void whenUserRolesContainsRoleShouldRemoveThatRole() throws Exception {
        Role role = new Role("user");
        when(userRepository.getOne(uuid)).thenReturn(user);
        when(roleService.getRoleById(uuid)).thenReturn(role);
        when(userRepository.save(user)).thenReturn(user);

        assertThat(this.userService.changeUserRole(uuid, uuid), is(user));
        verify(userRepository, times(1)).getOne(uuid);
        verify(roleService, times(1)).getRoleById(uuid);
        verify(userRepository, times(1)).save(user);
    }

    /**
     * When changeUserRole and user roles not contains role, should add that role.
     *
     * @throws Exception exception.
     */
    @Test
    public void whenUserRolesNotContainsRoleShouldAddThatRole() throws Exception {
        Role role = new Role("admin");
        when(userRepository.getOne(uuid)).thenReturn(user);
        when(roleService.getRoleById(uuid)).thenReturn(role);
        when(userRepository.save(user)).thenReturn(user);

        assertThat(this.userService.changeUserRole(uuid, uuid), is(user));
        verify(userRepository, times(1)).getOne(uuid);
        verify(roleService, times(1)).getRoleById(uuid);
        verify(userRepository, times(1)).save(user);
    }

    /**
     * When call updateEmail with incorrect password should throw ApiRequestException.
     *
     * @throws Exception ApiRequestException.
     */
    @Test
    public void whenUpdateEmailWithIncorrectPasswordShouldReturnThrowException() throws Exception {
        final String incorrectPassword = "incorrectPassword";
        when(principal.getName()).thenReturn(user.getEmail());
        when(this.userRepository.getUserByEmail(anyString())).thenReturn(user);

        Throwable thrown = catchThrowable(() -> {
            this.userService.updateEmail(principal, user.getEmail(), incorrectPassword);
        });
//        assertThat(thrown).isInstanceOf(ApiRequestException.class);
        assertThat(thrown.getMessage(), is("Не верный пароль."));
    }

    /**
     * When call updateEmail with an old email should throw ApiRequestException.
     *
     * @throws Exception ApiRequestException.
     */
    @Test
    public void whenUpdateEmailWithAnOldEmailShouldThrowException() throws Exception {
        final String password = "password";
        user.setPassword(encoder.encode(user.getPassword()));
        when(principal.getName()).thenReturn(user.getEmail());
        when(this.userRepository.getUserByEmail(user.getEmail())).thenReturn(user);

        Throwable thrown = catchThrowable(() -> {
            userService.updateEmail(principal, user.getEmail(), password);
        });

        assertThat(thrown.getMessage(), is("Вы ввели старую почту."));
    }

    /**
     * When call updateUser with correct email and password should return user.
     *
     * @throws Exception exception.
     */
    @Test
    public void whenUpdateEmailWithCorrectEmailAndPasswordShouldReturnUser() throws Exception {
        final String password = "password";
        final String newEmail = "newEmail";
        user.setPassword(encoder.encode(user.getPassword()));
        when(principal.getName()).thenReturn(user.getEmail());
        when(this.userRepository.getUserByEmail(user.getEmail())).thenReturn(user);

        User actualUser = this.userService.updateEmail(principal, newEmail, password);

        assertThat(actualUser.getActivationKey(), is(user.getActivationKey()));
        assertThat(actualUser.isActive(), is(user.isActive()));
    }

    /**
     * When updateEmail with email is already registered should throw DataIntegrityViolationException.
     *
     * @throws Exception DataIntegrityViolationException.
     */
    @Test
    public void whenUpdateEmailWithEmailThatIsAlreadyRegisteredShouldThrowException() throws Exception {
        final String password = "password";
        final String newEmail = "newEmail";
        user.setPassword(encoder.encode(user.getPassword()));
        when(principal.getName()).thenReturn(user.getEmail());
        when(this.userRepository.getUserByEmail(user.getEmail())).thenReturn(user);

        when(this.userRepository.save(any(User.class))).thenThrow(DataIntegrityViolationException.class);

        Throwable thrown = catchThrowable(() -> {
            userService.updateEmail(principal, newEmail, password);
        });

        assertThat(thrown.getMessage(), is("Пользователь с такой почтой уже зарегистрирован."));
    }

    /**
     * When updatePassword with old password incorrect should
     * throw ApiRequestException.
     *
     * @throws Exception exception.
     */
    @Test
    public void whenUpdatePasswordWithOldPasswordIncorrectShouldReturnFalse() throws Exception {
        user.setPassword(encoder.encode(user.getPassword()));
        when(principal.getName()).thenReturn(user.getEmail());
        when(this.userRepository.getUserByEmail(user.getEmail())).thenReturn(user);

        Throwable thrown = catchThrowable(() -> {
            this.userService.updatePassword(principal, user.getPassword(), "password");
        });

        assertThat(thrown.getMessage(), is("Incorrect old password."));
    }

    /**
     * When updatePassword with correct password should return user.
     *
     * @throws Exception exception.
     */
    @Test
    public void whenUpdatePasswordWithCorrectPasswordShouldReturnTrue() throws Exception {
        user.setPassword(encoder.encode(user.getPassword()));
        when(principal.getName()).thenReturn(user.getEmail());
        when(this.userRepository.getUserByEmail(user.getEmail())).thenReturn(user);

        assertNotNull(this.userService.updatePassword(principal, "password", "12345"));
    }
}
